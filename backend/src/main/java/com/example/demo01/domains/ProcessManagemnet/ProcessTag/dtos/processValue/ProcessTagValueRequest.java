package com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processValue;

import com.example.demo01.domains.ProcessManagemnet.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessTagValueRequest {

    private String tagTitle;

    private Boolean active;

    private String tagValueCode;

    @NotBlank
    private String description;

    @NotEmpty
    private List<ProcessTagNestFieldDto> tagItems;

}
