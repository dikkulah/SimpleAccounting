package com.dikkulah.exchangeservice.controller;

import com.dikkulah.exchangeservice.model.Exchange;
import com.dikkulah.exchangeservice.model.MultiplyRequest;
import com.dikkulah.exchangeservice.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("exchange")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ExchangeController {

    private final ExchangeService exchangeService;
    @PostMapping
    public ResponseEntity<Boolean> doExchange(@RequestBody Exchange exchange) {
        return exchangeService.createActivityAndSendAccountService(exchange);

    }
    @PostMapping("multiply")
    public ResponseEntity<Double> getMultiply(@RequestBody MultiplyRequest multiplyRequest) {
        return exchangeService.calculateMultiply(multiplyRequest);

    }

}
