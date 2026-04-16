package com.example.demo01.domains.jpa.Core.Audit.mapper;

import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateDto;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateRequest;
import com.example.demo01.domains.jpa.Core.Audit.entity.AuditUpdateEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuditUpdateJpaMapper {

    AuditUpdateEntity toEntity(AuditUpdateRequest request);

    AuditUpdateDto fromEntityToDto(AuditUpdateEntity entity);

    List<AuditUpdateDto> fromEntityToDto(List<AuditUpdateEntity> entity);

}
