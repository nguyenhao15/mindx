package com.example.demo01.repository.mongo.CoreRepo.AuditRepository;

import com.example.demo01.core.AuditUpdate.model.AuditItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditItemRepository extends MongoRepository<AuditItem, String> {
    AuditItem getByEntityId(String entityId);
}
