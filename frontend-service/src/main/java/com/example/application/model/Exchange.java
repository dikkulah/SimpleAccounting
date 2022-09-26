
package com.example.application.model;

import com.example.application.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "currencyFrom",
        "currencyTo",
        "conversionMultiple",
        "quantity",
        "totalAmount", "token", "accountFrom", "accountTo"
})
@Generated("jsonschema2pojo")
public class Exchange {

    @JsonProperty("id")
    private UUID id;
    @JsonProperty("currencyFrom")
    private Currency currencyFrom;
    @JsonProperty("currencyTo")
    private Currency currencyTo;
    @JsonProperty("conversionMultiple")
    private BigDecimal conversionMultiple;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;
    @JsonProperty("token")
    private String token;
    @JsonProperty("accountFrom")
    private UUID accountFrom;
    @JsonProperty("accountTo")
    private UUID accountTo;

    @JsonProperty("token")

    public String getToken() {
        return token;
    }

    @JsonProperty("token")

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("accountFrom")

    public UUID getAccountFrom() {
        return accountFrom;
    }

    @JsonProperty("accountFrom")

    public void setAccountFrom(UUID accountFrom) {
        this.accountFrom = accountFrom;
    }

    @JsonProperty("accountTo")

    public UUID getAccountTo() {
        return accountTo;
    }

    @JsonProperty("accountTo")

    public void setAccountTo(UUID accountTo) {
        this.accountTo = accountTo;
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonProperty("currencyFrom")
    public Currency getCurrencyFrom() {
        return currencyFrom;
    }

    @JsonProperty("currencyFrom")
    public void setCurrencyFrom(Currency currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    @JsonProperty("currencyTo")
    public Currency getCurrencyTo() {
        return currencyTo;
    }

    @JsonProperty("currencyTo")
    public void setCurrencyTo(Currency currencyTo) {
        this.currencyTo = currencyTo;
    }

    @JsonProperty("conversionMultiple")
    public BigDecimal getConversionMultiple() {
        return conversionMultiple;
    }

    @JsonProperty("conversionMultiple")
    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    @JsonProperty("quantity")
    public BigDecimal getQuantity() {
        return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @JsonProperty("totalAmount")
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    @JsonProperty("totalAmount")
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }


}
