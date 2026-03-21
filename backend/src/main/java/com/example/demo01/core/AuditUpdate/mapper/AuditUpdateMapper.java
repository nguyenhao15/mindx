package com.example.demo01.core.AuditUpdate.mapper;

import com.example.demo01.core.AuditUpdate.Dto.AuditUpdateDto;
import com.example.demo01.core.AuditUpdate.model.AuditItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditUpdateMapper {
    AuditItem toEntity(AuditUpdateDto dto);
}
