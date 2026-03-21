package com.example.demo01.core.Attachment.repository;

import com.example.demo01.core.Attachment.model.AttachmentItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends MongoRepository<AttachmentItem , String> {

    List<AttachmentItem> findByOwnerIdAndIsDeleted(String ownerId, Boolean isDeleted);

    List<AttachmentItem> findByFileUrlIn(List<String> fileUrls);

    List<AttachmentItem> findByIsDeleted(Boolean isDeleted);

}
