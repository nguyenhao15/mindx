package com.example.demo01.domains.mongo.MiniCrm.Process.dtos;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessingInfoDto {

    private String _id;

    private String processCode;

    private String customerId;

    private String description;

    private Boolean active;

    private String status;

    private String buId;

    private String buName;
    private String customerName;

    private FileResponseDTO file;

    private String createdBy;
    private String lastModifiedBy;
    private String createdDate;
    private String lastModifiedDate;
    private Long version;

}
