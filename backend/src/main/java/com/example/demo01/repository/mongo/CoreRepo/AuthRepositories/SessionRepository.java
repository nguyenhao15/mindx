package com.example.demo01.repository.mongo.CoreRepo.AuthRepositories;

import com.example.demo01.core.Auth.models.Session;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends MongoRepository<Session, String> {

    Session findByStaffId(String staffId);

    Session findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);
}
