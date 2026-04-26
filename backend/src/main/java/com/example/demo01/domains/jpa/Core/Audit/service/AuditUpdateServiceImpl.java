package com.example.demo01.domains.jpa.Core.Audit.service;

import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateDto;
import com.example.demo01.domains.jpa.Core.Audit.dto.AuditUpdateRequest;
import com.example.demo01.domains.jpa.Core.Audit.entity.AuditUpdateEntity;
import com.example.demo01.domains.jpa.Core.Audit.mapper.AuditUpdateJpaMapper;
import com.example.demo01.repository.postgreSQL.Core.AuditRepository.AuditUpdateRepository;
import com.example.demo01.utils.ModuleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditUpdateServiceImpl implements AuditUpdateService  {

    @Autowired
    private AuditUpdateRepository auditUpdateRepository;

    @Autowired
    private AuditUpdateJpaMapper auditUpdateJpaMapper;

    @Override
    public AuditUpdateDto createAuditUpdate(AuditUpdateRequest request) {
        ModuleEnum module = request.getModule();
        String identifier = module + "-" + request.getItemId();
        request.setIdentifier(identifier);
        AuditUpdateEntity auditUpdateEntity = auditUpdateJpaMapper.toEntity(request);
        AuditUpdateEntity savedEntity = auditUpdateRepository.save(auditUpdateEntity);
        return auditUpdateJpaMapper.fromEntityToDto(savedEntity);
    }

    @Override
    public List<AuditUpdateDto> getAuditUpdatesByEntityName(ModuleEnum moduleEnum, Long id) {
        String identifier = moduleEnum + "-" + id;

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        List<AuditUpdateEntity> auditUpdateEntities = auditUpdateRepository.findByIdentifier(identifier, sort);
        return auditUpdateJpaMapper.fromEntityToDto(auditUpdateEntities);
    }

    @Override
    public void deleteAuditUpdatesByEntityName(String entityName) {
        List<AuditUpdateEntity> auditUpdateEntities = auditUpdateRepository.findByIdentifier(entityName);
        auditUpdateRepository.deleteAll(auditUpdateEntities);
    }
}
