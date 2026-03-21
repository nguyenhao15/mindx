package com.example.demo01.core.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String field;
    String fieldName;
    String fieldId;

    public ResourceNotFoundException(String customerInfo, String id, Long aLong) {}

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

}
