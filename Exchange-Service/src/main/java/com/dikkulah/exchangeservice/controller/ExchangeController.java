package com.dikkulah.exchangeservice.controller;

import com.dikkulah.exchangeservice.model.Exchange;
import com.dikkulah.exchangeservice.model.MultiplyRequest;
import com.dikkulah.exchangeservice.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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
    @Operation(description = "Exchange işlemini gerçekleştiri.",
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Boolean.class), mediaType = MediaType.APPLICATION_JSON_VALUE))},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Exchange", content = @Content(schema = @Schema(implementation = Exchange.class))),
            security = {@SecurityRequirement(name = "SecurityConfig")})
    public ResponseEntity<Boolean> doExchange(@RequestBody Exchange exchange) {
        return exchangeService.createActivityAndSendAccountService(exchange);

    }
    @PostMapping("multiply")
    @Operation(description = " Sabit bir kur değeri döndürür.",
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = Double.class), mediaType = MediaType.APPLICATION_JSON_VALUE))},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Kur", content = @Content(schema = @Schema(implementation = MultiplyRequest.class))),
            security = {@SecurityRequirement(name = "SecurityConfig")})
    public ResponseEntity<Double> getMultiply(@RequestBody MultiplyRequest multiplyRequest) {
        return exchangeService.calculateMultiply(multiplyRequest);

    }

}
