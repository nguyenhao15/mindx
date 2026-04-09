package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processValue;

import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessTagValueDto {
    private String id;

    private String tagTitle;

    private String tagValueCode;

    private List<ProcessTagNestFieldDto> tagItems;

    private String description;

    private Boolean active;


    private String createdBy;
}
