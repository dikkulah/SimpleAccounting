package com.dikkulah.accountservice.dto;

import com.dikkulah.accountservice.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {
    private UUID id;
    private Currency currency;
    private BigDecimal amount;
    private List<ActivityDto> activities = new ArrayList<>();
}
