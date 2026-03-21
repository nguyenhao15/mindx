package com.example.demo01.core.SpaceCustomer.payload.CustomerInfo;


import com.example.demo01.core.SpaceCustomer.models.CustomerAddress;
import com.example.demo01.core.SpaceCustomer.models.CustomerContact;
import com.example.demo01.core.SpaceCustomer.models.CustomerField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerInfoDTO {

    private String _id;
    private String customerCode;
    private String customerTitle;
    private String customerTaxCode;
    private String representativeId;
    private Integer size;
    private Boolean isCompany;
    private List<CustomerField> customerField = new ArrayList<>();
    private List<CustomerContact> contactInfo = new ArrayList<>();
    private List<CustomerAddress> addressLine = new ArrayList<>();
}
