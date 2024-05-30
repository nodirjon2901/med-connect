package org.example.stackservice.exception;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String message) {
        super(message);
    }
}
