package com.example.demo01.domains.ProcessManagement.ProcessTag.dtos.processValue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessTagValueNestFieldDto {

    private String id;
    private String tagTitle;
    private String tagValueCode;
}
