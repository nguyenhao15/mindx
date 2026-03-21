package com.example.demo01.core.SpaceCustomer.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CustomerContact {

    private String uid;
    private String contactType;
    private String contactValue;

    @Transient
    private String updateType;

    public CustomerContact() {
        this.uid = UUID.randomUUID().toString();
    }

    public CustomerContact(String type, String value) {
        this.uid = UUID.randomUUID().toString(); // Tự động sinh ID
        this.contactType = type;
        this.contactValue = value;
    }
}
