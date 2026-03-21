// UserInfoResponse.java
package com.example.demo01.core.Auth.response;

import com.example.demo01.core.Auth.dtos.WorkProfile;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class UserInfoResponse {

    private String id;
    private String username;
    private String fullName;
    private String email;
    private List<WorkProfile> workProfiles;

    private boolean accountNonLocked;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private LocalDate credentialsExpiryDate;
    private LocalDate accountExpiryDate;
    private boolean isTwoFactorEnabled;

    public UserInfoResponse(String id, String username, String fullName, String email, boolean accountNonLocked, boolean accountNonExpired,
                            boolean credentialsNonExpired, boolean enabled, LocalDate credentialsExpiryDate,
                            LocalDate accountExpiryDate, boolean isTwoFactorEnabled, List<String> roles, List<WorkProfile> workProfiles) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.accountNonLocked = accountNonLocked;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.credentialsExpiryDate = credentialsExpiryDate;
        this.accountExpiryDate = accountExpiryDate;
        this.isTwoFactorEnabled = isTwoFactorEnabled;
        this.workProfiles = workProfiles;
    }
}