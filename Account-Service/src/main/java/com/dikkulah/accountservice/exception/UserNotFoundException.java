package com.dikkulah.accountservice.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String USER_NOT_FOUND = "Bu kullanıcı mevcut değil.";
    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }
}
