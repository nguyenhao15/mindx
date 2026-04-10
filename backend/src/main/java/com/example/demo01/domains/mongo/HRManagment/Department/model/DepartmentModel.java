package com.example.demo01.domains.mongo.HRManagment.Department.model;

import com.example.demo01.domains.mongo.HRManagment.Department.dto.WorkingField.WorkingFieldDto;
import com.example.demo01.utils.BaseEntity.Mongo.BaseAuditModel;
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
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "departments")
public class DepartmentModel extends BaseAuditModel {

    @Id
    private String id;

    @Indexed
    private String departmentName;

    @Indexed(unique = true)
    private String departmentCode;

    private String description;

    private String iconSvg;

    private Boolean isSecurity;

    private Boolean active;

    private List<WorkingFieldDto> workingFields;

}
