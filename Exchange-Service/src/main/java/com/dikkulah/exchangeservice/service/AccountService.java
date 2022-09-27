package com.dikkulah.exchangeservice.service;

import com.dikkulah.exchangeservice.model.Activity;
import com.dikkulah.exchangeservice.model.Exchange;
import com.dikkulah.exchangeservice.model.enums.ActivityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    WebClient accountClient = WebClient.builder()
            .baseUrl("http://localhost:8080/")
            .build();

    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Boolean> createActivityAndSendAccountService(Exchange exchange) {
        Activity activity1 = new Activity();
        activity1.setActivityType(ActivityType.BUY);
        activity1.setAmount(exchange.getQuantity());
        activity1.setTime(LocalDateTime.now());
        activity1.setDescription("ALIŞ [%s] @ [%s]".formatted(exchange.getQuantity(), exchange.getConversionMultiple() + " " + exchange.getCurrencyFrom() + "-" + exchange.getCurrencyTo()));
        log.info(activity1.toString());
        Activity activity2 = new Activity();

        activity2.setActivityType(ActivityType.SELL);
        activity2.setAmount(exchange.getTotalAmount());
        activity2.setTime(LocalDateTime.now());
        activity2.setDescription("SATIŞ [%s] @ [%s]".formatted(exchange.getTotalAmount(), BigDecimal.ONE.divide(exchange.getConversionMultiple(), MathContext.DECIMAL32) + " " + exchange.getCurrencyTo() + "-" + exchange.getCurrencyFrom()));
        log.info(activity2.toString());
        try {
            log.info(exchange.getToken());
            var checkAccountBalanceResponse = accountClient.get().uri("accounts/balance/" + exchange.getAccountTo() + "/" + activity2.getAmount()).header(HttpHeaders.AUTHORIZATION, "Bearer " + exchange.getToken()).retrieve().bodyToMono(Boolean.class).block();
            if (Boolean.TRUE.equals(checkAccountBalanceResponse)) {
                var buyResponse = accountClient.post().uri("accounts/" + exchange.getAccountFrom() + "/activity/").header(HttpHeaders.AUTHORIZATION, "Bearer " + exchange.getToken()).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(activity1)).retrieve().bodyToMono(Boolean.class).onErrorMap(e -> new RuntimeException(e.getMessage())).block();
                var sellResponse = accountClient.post().uri("accounts/" + exchange.getAccountTo() + "/activity/").header(HttpHeaders.AUTHORIZATION, "Bearer " + exchange.getToken()).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(activity2)).retrieve().bodyToMono(Boolean.class).onErrorMap(e -> new RuntimeException(e.getMessage())).block();

                if (Boolean.TRUE.equals(sellResponse) && Boolean.TRUE.equals(buyResponse))
                    return ResponseEntity.ok(Boolean.TRUE);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.badRequest().body(Boolean.FALSE);

    }
}
