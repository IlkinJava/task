package com.example.parking.exception;

public class UnsuccessfulTransactionException extends RuntimeException {
    public UnsuccessfulTransactionException(String message) {
        super(message);
    }
}
