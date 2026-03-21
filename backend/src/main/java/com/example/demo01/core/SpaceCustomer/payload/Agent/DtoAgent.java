package com.example.demo01.core.SpaceCustomer.payload.Agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtoAgent {

    private String _id;

    private String fullName;
    private String idNumber;
    private Boolean gender;

    private Boolean isAgency;

}
