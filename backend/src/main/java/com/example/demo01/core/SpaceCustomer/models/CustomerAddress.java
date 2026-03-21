package com.example.demo01.core.SpaceCustomer.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.UUID;


@Data
@AllArgsConstructor
public class CustomerAddress {

    private String uid;
    private String addressLine;
    private String city;
    private String ward;

    @Transient
    private String updateType;

    public CustomerAddress() {
        this.uid = UUID.randomUUID().toString(); // Tự động sinh ID khi new
    }

    //
    public CustomerAddress(String addressLine, String city, String ward) {
        this.uid = UUID.randomUUID().toString(); // Tự động sinh ID
        this.city = city;
        this.ward = ward;
        this.addressLine = addressLine;
    }

}
