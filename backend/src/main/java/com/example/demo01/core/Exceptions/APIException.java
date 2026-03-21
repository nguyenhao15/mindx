package com.example.demo01.core.Exceptions;

public class APIException extends RuntimeException{
    public APIException(String message) {
        super(message);
    }
}
