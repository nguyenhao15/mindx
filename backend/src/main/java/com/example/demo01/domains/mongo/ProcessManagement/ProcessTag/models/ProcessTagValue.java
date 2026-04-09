package com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.models;

import com.example.demo01.domains.mongo.ProcessManagement.ProcessTag.dtos.processTag.ProcessTagNestFieldDto;
import com.example.demo01.utils.BaseModels.BaseAuditModel;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tag_values")
@Data
@EqualsAndHashCode(callSuper = true)
public class ProcessTagValue extends BaseAuditModel {

    @Id
    private String id;

    @Indexed
    private String tagTitle;

    @Indexed(unique = true)
    private String tagValueCode;

    @Indexed
    private List<ProcessTagNestFieldDto> tagItems;

    private Boolean active;

    @NotBlank
    private String description;

}
