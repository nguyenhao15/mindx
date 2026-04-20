package com.example.demo01.domains.mongo.HRManagment.HumanResource.model;

import com.example.demo01.utils.BaseEntity.Mongo.BaseAuditModel;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "staff_profiles")
public class StaffProfileModels extends BaseAuditModel {

    @Id
    private String id;

    private String staffId;

    private String userId;

    private String departmentId;

    private String positionId;

    private String departmentName;

    private String positionName;

    private int positionLevel;

    private List<String> buAllowedList = new ArrayList<>();

    private Boolean isDefault;

    private Boolean active = true;
}
