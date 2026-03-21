package com.example.demo01.core.SpaceCustomer.models;


import lombok.Data;

import java.util.UUID;

@Data
public class CustomerField {

    private String uid;
    private String fieldValue;

    public CustomerField() {
        this.uid = UUID.randomUUID().toString(); // Tự động sinh ID khi new
    }

    public CustomerField(String type) {
        this.uid = UUID.randomUUID().toString(); // Tự động sinh ID
        this.fieldValue = type;
    }
}
