package com.example.application.service;


import com.example.application.model.Account;
import com.example.application.model.Activity;
import com.example.application.model.Exchange;
import com.example.application.model.Token;
import com.example.application.model.enums.Currency;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class BackendService {
    WebClient accountClient = WebClient.builder()
            .baseUrl("http://localhost:8080/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    WebClient exchangeClient = WebClient.builder()
            .baseUrl("http://localhost:8081/")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    @Value("${auth-token.header}")
    private String apiKey;
    @Value("${auth-token.value}")
    private String value;

    public ResponseEntity<Token> login(String username, String password) {
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("username", username);
        bodyMap.put("password", password);
        var responseToken = accountClient.post()
                .uri("user/login")
                .body(BodyInserters.fromValue(bodyMap))
                .retrieve()
                .bodyToFlux(Token.class)
                .onErrorMap(e -> new RuntimeException(e.getMessage()))
                .blockLast();
        return new ResponseEntity<>(responseToken, HttpStatus.OK);


    }

    public ResponseEntity<List<Account>> getAccounts(String token)  {

        var response = accountClient.get()
                .uri("accounts")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Account[].class)
                .onErrorMap(e -> new RuntimeException(e.getMessage())).block();

        assert response != null;
        List<Account> accounts = List.of(response);
        return new ResponseEntity<>(accounts, HttpStatus.OK);


    }

    //page yapısında veri almaya çalış
    public ResponseEntity<List<Activity>> getAccountActivities(String token, UUID uuid, Integer count) {

        var response = accountClient.get()
                .uri("accounts/" + uuid + "/" + count)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Activity[].class)
                .onErrorMap(e -> new RuntimeException(e.getMessage())).block();
        assert response != null;
        List<Activity> activities = List.of(response);
        log.info(activities.toString());
        return new ResponseEntity<>(activities, HttpStatus.OK);


    }

    public ResponseEntity<Account> createAccount(Currency currencyValue, String token) {
        var response = accountClient.post()
                .uri("accounts/" + "/" + currencyValue)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(Account.class)
                .onErrorMap(e -> new RuntimeException(e.getMessage())).block();
        return ResponseEntity.ok().body(response);
    }


    public ResponseEntity<Exchange> doExchange(Exchange exchangeRequest) {
        var response = exchangeClient.post().uri("/exchange")
                .header(apiKey, value)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(exchangeRequest))
                .retrieve()
                .bodyToMono(Exchange.class)
                .onErrorMap(e -> new RuntimeException(e.getMessage())).block();
        return ResponseEntity.ok(response);
    }
}
