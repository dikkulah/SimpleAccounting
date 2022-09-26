package com.dikkulah.exchangeservice.service;

import com.dikkulah.exchangeservice.model.Activity;
import com.dikkulah.exchangeservice.model.Exchange;
import com.dikkulah.exchangeservice.model.enums.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {
    WebClient accountClient = WebClient.builder()
            .baseUrl("http://localhost:8080/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();


    public ResponseEntity<Boolean> createActivityAndSendAccountService(Exchange exchange) {

        Activity activity1 = new Activity();
        activity1.setActivityType(ActivityType.BUY);
        activity1.setAmount(exchange.getTotalAmount());
        activity1.setTime(LocalDateTime.now());
        activity1.setDescription("ALIŞ [%s] @ [%s]".formatted(exchange.getQuantity(), exchange.getConversionMultiple()));

        Activity activity2 = new Activity();

        activity2.setActivityType(ActivityType.SELL);
        activity2.setAmount(exchange.getTotalAmount());
        activity2.setTime(LocalDateTime.now());
        activity2.setDescription("SATIŞ [%s] @ [%s]".formatted(BigDecimal.ONE.divide(exchange.getQuantity(), MathContext.DECIMAL32), BigDecimal.ONE.divide(exchange.getConversionMultiple(), MathContext.DECIMAL32)));


        var response1 = accountClient.post().uri("accounts/" + exchange.getAccountFrom() + "/activity")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + exchange.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(activity1))
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorMap(e -> new RuntimeException(e.getMessage())).block();

        var response2 = accountClient.post().uri("accounts/" + exchange.getAccountTo() + "/activity")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + exchange.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(activity2))
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorMap(e -> new RuntimeException(e.getMessage())).block();

        if (Boolean.TRUE.equals(response2) && Boolean.TRUE.equals(response1)) return ResponseEntity.ok(Boolean.TRUE);
        else return ResponseEntity.badRequest().body(Boolean.FALSE);
    }
}
