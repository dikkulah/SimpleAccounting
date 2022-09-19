package com.dikkulah.accountservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime localDateTime;


    public ErrorResponse(String message, HttpStatus httpStatus, LocalDateTime localDateTime) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "message='" + message + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}


