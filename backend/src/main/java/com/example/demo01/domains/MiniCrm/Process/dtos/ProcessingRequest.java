package com.example.demo01.domains.MiniCrm.Process.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProcessingRequest {

    private String processCode;

    private String buId;

    private String customerId;

    private String updateDescription;

    private String status;

    private Boolean active;
}
