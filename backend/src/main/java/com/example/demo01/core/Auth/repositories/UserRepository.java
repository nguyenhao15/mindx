package com.example.demo01.core.Auth.repositories;

import com.example.demo01.core.Auth.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByEmail(String email);

    boolean existsByStaffId(String staffId);

    User findByStaffId(String staffId);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}

