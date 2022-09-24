
package com.dikkulah.exchangeservice.model;

import com.dikkulah.exchangeservice.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.ToString;

import javax.annotation.Generated;
import java.math.BigDecimal;

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
@ToString
public class Exchange {

    @JsonProperty("id")
    private String id;
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
    private Account accountFrom;
    @JsonProperty("accountTo")
    private Account accountTo;

    @JsonProperty("token")

    public String getToken() {
        return token;
    }

    @JsonProperty("token")

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("accountFrom")

    public Account getAccountFrom() {
        return accountFrom;
    }

    @JsonProperty("accountFrom")

    public void setAccountFrom(Account accountFrom) {
        this.accountFrom = accountFrom;
    }

    @JsonProperty("accountTo")

    public Account getAccountTo() {
        return accountTo;
    }

    @JsonProperty("accountTo")

    public void setAccountTo(Account accountTo) {
        this.accountTo = accountTo;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
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
