package com.dikkulah.accountservice.dto;

import com.dikkulah.accountservice.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeDto implements Serializable {
    private UUID id;
    private Currency currencyFrom;
    private Currency currencyTo;
    private BigDecimal conversionMultiple;
    private BigDecimal quantity;
    private BigDecimal totalAmount;
}
