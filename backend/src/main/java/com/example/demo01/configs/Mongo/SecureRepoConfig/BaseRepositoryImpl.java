package com.example.demo01.configs.Mongo.SecureRepoConfig;

import com.example.demo01.core.Auth.dtos.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class BaseRepositoryImpl<T, ID> extends SimpleMongoRepository<T, ID> implements BaseRepository<T, ID> {

    private final MongoTemplate mongoTemplate;
    private final MongoEntityInformation<T, ID> entityInformation;

    public BaseRepositoryImpl(MongoEntityInformation<T, ID> metadata, MongoTemplate mongoTemplate) {
        super(metadata, mongoTemplate);
        this.entityInformation = metadata;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<T> findAllSecure(Query query) {
        applySecurity(query);
        return mongoTemplate.find(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
    }

    @Override
    public Page<T> findAllSecure(Query query, Pageable pageable) {
        applySecurity(query);

        long total = mongoTemplate.count(query, entityInformation.getJavaType(), entityInformation.getCollectionName());

        query.with(pageable);

        List<T> content = mongoTemplate.find(query, entityInformation.getJavaType(), entityInformation.getCollectionName());

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public long countSecure(Query query) {
        applySecurity(query);
        return mongoTemplate.count(query, entityInformation.getJavaType(), entityInformation.getCollectionName());
    }

    private void applySecurity(Query query) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails user) {
            if (!user.isGlobalAdmin()) {
                List<String> allowedLocs = user.getAllowedLocations();
                if (allowedLocs == null || allowedLocs.isEmpty()) {
                    query.addCriteria(Criteria.where("_id").is("NO_ACCESS"));
                } else {
                    query.addCriteria(Criteria.where("buId").in(allowedLocs));
                }
            }
        }
    }
}
