
package com.dikkulah.exchangeservice.model;

import com.dikkulah.exchangeservice.model.enums.ActivityType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.ToString;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "time",
    "amount",
    "activityType",
    "description"
})
@Generated("jsonschema2pojo")
@ToString
public class Activity {

    @JsonProperty("id")
    private String id;
    @JsonProperty("time")
    private LocalDateTime time;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("activityType")
    private ActivityType activityType;
    @JsonProperty("description")
    private String description;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("time")
    public LocalDateTime getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @JsonProperty("amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @JsonProperty("activityType")
    public ActivityType getActivityType() {
        return activityType;
    }

    @JsonProperty("activityType")
    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }


}
