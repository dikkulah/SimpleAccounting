package com.dikkulah.exchangeservice.model;

import com.dikkulah.exchangeservice.model.enums.ActivityType;
import com.dikkulah.exchangeservice.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "currencyFrom",
        "currencyTo",
        "activityType"
})
@Getter
@Setter
@AllArgsConstructor
public class MultiplyRequest {
    @JsonProperty("currencyFrom")
    @Enumerated(EnumType.STRING)
    private final Currency currencyFrom;
    @JsonProperty("currencyTo")
    @Enumerated(EnumType.STRING)
    private final Currency currencyTo;
    @JsonProperty("activityType")
    private final ActivityType activityType;
}

