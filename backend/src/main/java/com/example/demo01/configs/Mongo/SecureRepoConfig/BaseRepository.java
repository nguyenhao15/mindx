package com.example.demo01.configs.Mongo.SecureRepoConfig;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends MongoRepository<T, ID> {

    List<T> findAllSecure(Query query);

    Page<T> findAllSecure(Query query, Pageable pageable);

    long countSecure(Query query);

}
