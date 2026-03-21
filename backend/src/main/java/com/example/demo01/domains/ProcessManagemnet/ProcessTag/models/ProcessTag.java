package com.example.demo01.domains.ProcessManagemnet.ProcessTag.models;

import com.example.demo01.utils.BaseAuditModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "processTag")
public class ProcessTag extends BaseAuditModel {

    @Id
    private String id;

    @Indexed(unique = true)
    private String tagName;

    @Indexed
    private String fullTagName;

    @NotBlank
    private String description;

    private Boolean active;

    private String status;
}
