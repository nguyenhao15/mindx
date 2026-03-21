package com.example.demo01.core.SpaceCustomer.payload.CustomerInfo;

import com.example.demo01.core.SpaceCustomer.models.CustomerField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRequestDTO {

    
    private String customerCode;

    @NotBlank
    @Size(min = 5, message = "Customer Title must contain at least 5 characters")
    private String customerTitle;

    @NotBlank
    @Size(min = 5, message = "Customer Name must contain at least 5 characters")
    private String customerTaxCode;

    private String representativeId;

    private Integer size;

    private Boolean isCompany;

    private String email;
    private String phone;
    private String city;
    private String ward;
    private String addressLine;
    private Boolean isDeleted = false;

    private List<CustomerField> customerField = new ArrayList<>();

    private List<String> contactIds = new ArrayList<>();
    private List<String> addressIds = new ArrayList<>();

}
