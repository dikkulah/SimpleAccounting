package com.dikkulah.accountservice.exception;

public class WrongPasswordException extends RuntimeException {

    private static final String WRONG_PASSWORD = "Yanlış şifre girdiniz, lütfen tekrar deneyiniz";
    public WrongPasswordException() {
        super(WRONG_PASSWORD);
    }
}
