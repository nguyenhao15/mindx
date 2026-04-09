package com.example.demo01.core.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    String fieldId;

    public ResourceNotFoundException(String message, String resourceName, String field, String fieldName) {
        super(String.format("%s not found with %s: %s",resourceName, field, fieldName));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldName = fieldName;
    }

    public ResourceNotFoundException(String resourceName, String field, String fieldId){
        super(String.format("%s not found with %s: %s",resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String resourceName, String field, Long fieldId){
        super(String.format("%s not found with %s: %s", resourceName, field, fieldId));
        this.resourceName = resourceName;
        this.field = field;
        this.fieldId = String.valueOf(fieldId);
    }

}
