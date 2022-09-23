package com.example.application.model;

import com.example.application.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "currency",
        "amount",
        "activities"
})
@Generated("jsonschema2pojo")

public class Account {

    @JsonProperty("id")
    private String id;
    @JsonProperty("currency")
    private Currency currency;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("activities")
    private List<Activity> activities = new ArrayList<>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("currency")
    public Currency getCurrency() {
        return currency;
    }

    @JsonProperty("currency")
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @JsonProperty("activities")
    public List<Activity> getActivities() {
        return activities;
    }

    @JsonProperty("activities")
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }


    @Override
    public String toString() {
        return
                "Birim: " + currency +
                        "  Miktar: " + amount +
                        "  HesapNo: " + id;
    }
}
