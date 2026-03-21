package com.example.demo01.core.SpaceCustomer.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "CustomerInfo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerInfo {

    @Id
    @Indexed
    private String _id;

    //    This is for identification customer with their info like: Space - {companyTaxNumber for company and phone for individual}
    @Indexed(unique = true)
    private String customerCode;

    @Indexed
    private String customerTitle;

    @Indexed(unique = true)
    private String customerTaxCode;

    private String representativeId;

    private Integer size;

    private Boolean isCompany;

    private List<CustomerField> customerField = new ArrayList<>();
    private List<CustomerContact> contactInfo = new ArrayList<>();
    private List<CustomerAddress> addressLine = new ArrayList<>();

    private Boolean isDeleted;

    private LocalDate deletedAt;

    @CreatedDate
    private Instant createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private Instant updatedAt;

    @LastModifiedBy
    private String updatedBy;

}
