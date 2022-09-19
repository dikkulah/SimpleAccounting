package com.dikkulah.accountservice.exception;

public class AccountNotFoundException extends RuntimeException {
    private static final String ACCOUNT_NOT_FOUND = "Böyle bir hesap mevcut değil.";
    public AccountNotFoundException() {
        super(ACCOUNT_NOT_FOUND);
    }
}
