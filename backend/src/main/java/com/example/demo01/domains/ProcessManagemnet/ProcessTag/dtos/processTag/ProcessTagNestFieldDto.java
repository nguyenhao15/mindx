package com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processTag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessTagNestFieldDto {

    private String id;
    private String tagName;
    private String fullTagName;

}
