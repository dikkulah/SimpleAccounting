package com.dikkulah.exchangeservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Ufuk Forex - Exchange Service API"
        ,version = "0.1", description = "Ufuk Forex - Exchange Service"),
        servers = {@Server(url = "http://localhost:8081/")})
@SecurityScheme(name = "SecurityConfig",scheme = "ApiKeyAuth", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER,paramName = "x-api-key")
public class ExchangeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeServiceApplication.class, args);
    }

}
