package com.example.demo01.core.Auth.dtos;

import com.example.demo01.core.Auth.models.User;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.example.demo01.utils.ModuleEnum;
import com.example.demo01.utils.ScopeView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String _id;
    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;
    private String fullName;

    private String email;
    private String staffId;
    private String systemRole;

    private Map<String, StaffProfileInfoDto> allProfiles = new HashMap<>();

    private Map<ModuleEnum, ScopeView> scopeExceptions = new HashMap<>();

    private List<String> allowLocationIds;

    private transient StaffProfileInfoDto activeProfile;
    public CustomUserDetails(User user,@Nullable List<StaffProfileInfoDto> staffProfileInfoDto, Map<ModuleEnum, ScopeView>  scopeExceptions) {
        this._id = user.get_id();

        this.username = user.getUsername();

        this.fullName = user.getFullName();

        this.password = user.getPassword();

        this.email = user.getEmail();

        this.staffId = user.getStaffId();

        this.systemRole = user.getSystemRole();

        if (staffProfileInfoDto != null) {
            this.allProfiles = staffProfileInfoDto.stream()
                    .collect(Collectors.toMap(StaffProfileInfoDto::id, profile -> profile));
        }
        this.scopeExceptions = scopeExceptions != null ? scopeExceptions : new HashMap<>();

        List<SimpleGrantedAuthority> auths = new ArrayList<>();

        auths.add(new SimpleGrantedAuthority("ROLE_" + user.getSystemRole()));
        this.authorities = auths;
    }

    public CustomUserDetails withActiveProfile(StaffProfileInfoDto profile) {
        this.activeProfile = profile;
        return this;
    }

    public ScopeView getFinalScope(ModuleEnum moduleCode) {
        if (scopeExceptions.containsKey(moduleCode)) return scopeExceptions.get(moduleCode);
        return ScopeView.SELF;
    }

    public StaffProfileInfoDto getDefaultProfile() {
        return allProfiles.values().stream()
                .filter(StaffProfileInfoDto::isDefault)
                .findFirst()
                .orElse(allProfiles.isEmpty() ? null : allProfiles.values().iterator().next());
    }

    public boolean isGlobalAdmin() {
        return systemRole.equals("ADMIN") || this.activeProfile.positionLevel() > 2;
    }

    public List<String> getAllowedLocations() {
        if (isGlobalAdmin()) {
            return null;
        }
        return this.allowLocationIds;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
