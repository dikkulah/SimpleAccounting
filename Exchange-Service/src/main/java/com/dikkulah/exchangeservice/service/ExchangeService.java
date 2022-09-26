package com.dikkulah.exchangeservice.service;

import com.dikkulah.exchangeservice.model.Exchange;
import com.dikkulah.exchangeservice.repository.ExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;

    public Exchange saveExchange(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }
}
