package com.dikkulah.exchangeservice.service;

import com.dikkulah.exchangeservice.model.Activity;
import com.dikkulah.exchangeservice.model.Exchange;
import com.dikkulah.exchangeservice.model.MultiplyRequest;
import com.dikkulah.exchangeservice.model.enums.ActivityType;
import com.dikkulah.exchangeservice.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeService {
    @Autowired
    ExchangeRepository exchangeRepository;
    WebClient accountClient = WebClient.builder()
            .baseUrl("http://localhost:8080/")
            .build();

    private Activity getActivitySell(Exchange exchange) {
        Activity activity2 = new Activity();
        activity2.setActivityType(ActivityType.SELL);
        activity2.setAmount(exchange.getTotalAmount());
        activity2.setTime(LocalDateTime.now());
        activity2.setDescription("SATIŞ [%s] @ [%s]".formatted(exchange.getTotalAmount(), BigDecimal.ONE.divide(exchange.getConversionMultiple(), MathContext.DECIMAL32) + " " + exchange.getCurrencyTo() + "-" + exchange.getCurrencyFrom()));
        return activity2;
    }

    private Activity getActivityBuy(Exchange exchange) {
        Activity activity1 = new Activity();
        activity1.setActivityType(ActivityType.BUY);
        activity1.setAmount(exchange.getQuantity());
        activity1.setTime(LocalDateTime.now());
        activity1.setDescription("ALIŞ [%s] @ [%s]".formatted(exchange.getQuantity(), exchange.getConversionMultiple() + " " + exchange.getCurrencyFrom() + "-" + exchange.getCurrencyTo()));
        return activity1;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public ResponseEntity<Boolean> createActivityAndSendAccountService(Exchange exchange) {
        Activity activity1 = getActivityBuy(exchange);
        Activity activity2 = getActivitySell(exchange);


        try {
            log.info(exchange.getToken());
            var checkAccountBalanceResponse = accountClient.get().uri("accounts/balance/" + exchange.getAccountTo() + "/" + activity2.getAmount()).header(HttpHeaders.AUTHORIZATION, "Bearer " + exchange.getToken()).retrieve().bodyToMono(Boolean.class).block();
            if (Boolean.TRUE.equals(checkAccountBalanceResponse)) {
                var buyResponse = accountClient.post().uri("accounts/" + exchange.getAccountFrom() + "/activity/").header(HttpHeaders.AUTHORIZATION, "Bearer " + exchange.getToken()).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(activity1)).retrieve().bodyToMono(Boolean.class).onErrorMap(e -> new RuntimeException(e.getMessage())).block();
                var sellResponse = accountClient.post().uri("accounts/" + exchange.getAccountTo() + "/activity/").header(HttpHeaders.AUTHORIZATION, "Bearer " + exchange.getToken()).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(activity2)).retrieve().bodyToMono(Boolean.class).onErrorMap(e -> new RuntimeException(e.getMessage())).block();

                if (Boolean.TRUE.equals(sellResponse) && Boolean.TRUE.equals(buyResponse)) {
                    exchangeRepository.save(exchange);
                    return ResponseEntity.ok(Boolean.TRUE);
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
        return ResponseEntity.badRequest().body(Boolean.FALSE);

    }

    public ResponseEntity<Double> calculateMultiply(MultiplyRequest multiplyRequest) {
        Random random = new Random();
         var multiply = switch (multiplyRequest.getCurrencyFrom()) {
            case DOLLAR -> switch (multiplyRequest.getCurrencyTo()) {
                case TL -> random.nextDouble() * (19.0 - 17.0) + 17.0;
                case DOLLAR -> 1D;
                case EURO -> random.nextDouble() * (1.1 - 1.0) + 1.0;
                case GOLD -> random.nextDouble() * (0.02 - 0.015) + 0.015;
            };
            case TL -> switch (multiplyRequest.getCurrencyTo()) {
                case TL -> 1D;
                case DOLLAR, EURO -> random.nextDouble() * (0.06 - 0.05) + 0.05;
                case GOLD -> random.nextDouble() * (0.0012 - 0.0010) + 0.0010;
            };
            case GOLD -> switch (multiplyRequest.getCurrencyTo()) {
                case TL -> random.nextDouble() * (950. - 900.) + 900.;
                case DOLLAR, EURO -> random.nextDouble() * (53. - 50.) + 50.;
                case GOLD -> 1D;
            };
            case EURO -> switch (multiplyRequest.getCurrencyTo()) {
                case TL -> random.nextDouble() * (18.2 - 17.) + 17.;
                case DOLLAR -> random.nextDouble() * (0.99 - 0.93) + 0.93;
                case EURO -> 1D;
                case GOLD -> random.nextDouble() * (0.019D - 0.014D) + 0.014D;
            };
        };
         if (multiplyRequest.getActivityType() == ActivityType.BUY){
             multiply *=1.05;
         }else {
             multiply*=0.95;
         }
        return ResponseEntity.ok().body(multiply);
    }


}
