package com.dikkulah.exchangeservice.controller;

import com.dikkulah.exchangeservice.model.Exchange;
import com.dikkulah.exchangeservice.service.AccountService;
import com.dikkulah.exchangeservice.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("exchange")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ExchangeController {
    private final ExchangeService exchangeService;
    private final AccountService accountService;
    @PostMapping
    public String doExchange(@RequestBody Exchange exchange) {
        accountService.sendExchangeToAccountService();

        return "";
    }

}
