package com.squad8.s8orangebackend.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(Integer id) {
        super("ResourceNotFound. Id: " + id);
    }
}
