package com.example.demo01.domains.HRManagment.Department.model;

import com.example.demo01.domains.HRManagment.Department.dto.Department.DepartmentInfoDto;
import com.example.demo01.utils.BaseAuditModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "positions")
public class PositionModel extends BaseAuditModel {

    @Id
    private String id;

    @Indexed
    private String positionName;

    @Indexed(unique = true)
    private String positionCode;

    private String description;

    @Indexed
    private String departmentCode;

    @Indexed
    private int positionLevel;

    private Boolean active;

}
