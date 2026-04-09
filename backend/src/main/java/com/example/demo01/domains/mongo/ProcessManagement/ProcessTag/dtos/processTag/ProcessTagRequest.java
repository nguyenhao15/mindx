package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessTagRequest {

    @NotEmpty
    private String tagName;

    @NotEmpty
    private String fullTagName;

    private Boolean active = false;

    private String status;

    @NotBlank
    private String description;

}
