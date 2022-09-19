package com.example.application.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "username",
        "hashedPassword",
        "accounts",
        "exchanges",
        "roles"
})
@Generated("jsonschema2pojo")
public class User {


    @JsonProperty("id")
    private String id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("hashedPassword")
    private String hashedPassword;
    @JsonProperty("accounts")
    private List<Account> accounts = new ArrayList<>();
    @JsonProperty("exchanges")
    private List<Exchange> exchanges = new ArrayList<>();
    @JsonProperty("roles")
    private Set<String> roles = new HashSet<>();


    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    @JsonProperty("username")
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty("hashedPassword")
    public String getHashedPassword() {
        return hashedPassword;
    }

    @JsonProperty("hashedPassword")
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @JsonProperty("accounts")
    public List<Account> getAccounts() {
        return accounts;
    }

    @JsonProperty("accounts")
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @JsonProperty("exchanges")
    public List<Exchange> getExchanges() {
        return exchanges;
    }

    @JsonProperty("exchanges")
    public void setExchanges(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    @JsonProperty("roles")
    public Set<String> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }


}
