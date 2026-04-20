package com.example.demo01.core.Auth.models;

import com.example.demo01.core.Auth.dtos.WorkProfile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String _id;

    @NotBlank
    private String fullName;

    @NotBlank
    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String staffId;

    @NotBlank
    @Size(max = 50)
    @Email
    @Indexed(unique = true)
    private String email;

    private String systemRole;

    @Size(max = 120)
    @JsonIgnore
    private String password;

    private boolean accountNonLocked = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;

    private String twoFactorSecret;
    private boolean isTwoFactorEnabled = false;
    private String signUpMethod;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant updatedDate;

    public User(String userName, String email, String password) {
        this.username = User.this.username;
        this.email = email;
        this.password = password;
    }

    public User(String username, String email) {
        this.username = User.this.username;
        this.email = email;
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof User)) return false;
//        return userId != null && userId.equals(((User) o).getUserId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
}


