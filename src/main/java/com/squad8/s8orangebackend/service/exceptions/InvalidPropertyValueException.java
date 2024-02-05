package com.squad8.s8orangebackend.service.exceptions;

public class InvalidPropertyValueException extends RuntimeException{


    public InvalidPropertyValueException(String message) {
        super("Value is null or empty");
    }
}
