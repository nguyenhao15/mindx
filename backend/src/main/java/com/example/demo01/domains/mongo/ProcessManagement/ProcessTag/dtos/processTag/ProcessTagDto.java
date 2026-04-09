package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessTagDto {
//    For save to nested processTag in process
    private String id;

    private String tagName;

    private String description;

    private String fullTagName;

    private Boolean active;

    private String status;



}
