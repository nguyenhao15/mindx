package com.example.demo01.core.Auth.repositories;

import com.example.demo01.core.Auth.models.AppRole;
import com.example.demo01.core.Auth.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByRoleName(AppRole appRole);
}
