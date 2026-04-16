package com.example.demo01.domains.jpa.Core.Audit.entity;

import com.example.demo01.core.AuditUpdate.model.ChangeType;
import com.example.demo01.utils.BaseEntity.Jpa.BaseAuditJpaModel;
import com.example.demo01.utils.ModuleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "audits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuditUpdateEntity extends BaseAuditJpaModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String identifier;
    
    private String description;
    
    private String updateValue;
    
    @Enumerated(EnumType.STRING)
    private ChangeType changeType;
    
    private ModuleEnum module;
    
}
