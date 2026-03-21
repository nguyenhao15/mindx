package com.example.demo01.core.SpaceCustomer.payload.Agent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestAgent {

    @NotBlank(message = "Full name must not be blank")
    @Size(min = 5, message = "Full name must contains more 5 characters")
    private String fullName;

    @NotBlank(message = "idNumber must not be blank")
    @Size(min = 10, message = "Id number must contains more 10 characters")
    private String idNumber;

    private Boolean gender;

    private Boolean isAgency;

}
