package com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processValue;

import com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

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
