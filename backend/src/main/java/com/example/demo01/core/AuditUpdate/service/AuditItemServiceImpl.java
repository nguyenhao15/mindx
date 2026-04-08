package com.example.demo01.core.AuditUpdate.service;

import com.example.demo01.configs.Constants.CacheConstants;
import com.example.demo01.configs.SecureRepoConfig.SecurityRepoUtilImpl;
import com.example.demo01.core.AuditUpdate.Dto.AuditUpdateDto;
import com.example.demo01.core.AuditUpdate.model.AuditItem;
import com.example.demo01.repository.mongo.CoreRepo.AuditRepository.AuditItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditItemServiceImpl implements AuditItemService {

    private final AuditItemRepository auditItemRepository;

    private final SecurityRepoUtilImpl securityRepoUtil;

    private final MongoTemplate mongoTemplate;

    @Override
    @CacheEvict(value = CacheConstants.AUDIT_CACHE, key = "#auditItem.entityId")
    public AuditItem createAuditItem(AuditUpdateDto auditItem) {
        AuditItem existingItem = auditItemRepository.getByEntityId(auditItem.getEntityId());
        String currentUser = securityRepoUtil.getCurrentUserId();
        auditItem.setAuthor(currentUser);
        auditItem.setCreatedDate(Instant.now());

        if (existingItem != null) {
            List<AuditUpdateDto> existingUpdates = existingItem.getUpdates();
            existingUpdates.add(auditItem);
            existingItem.setUpdates(existingUpdates);
            return auditItemRepository.save(existingItem);
        }
        AuditItem newItem = new AuditItem();
        newItem.setEntityId(auditItem.getEntityId());
        newItem.setUpdates(List.of(auditItem));
        return auditItemRepository.save(newItem);
    }

    @Override
    @Cacheable(value = CacheConstants.AUDIT_CACHE, key = "#entityId")
    public AuditItem getAuditItemsByEntityId(String entityId) {
        return auditItemRepository.getByEntityId(entityId);
    }
}
