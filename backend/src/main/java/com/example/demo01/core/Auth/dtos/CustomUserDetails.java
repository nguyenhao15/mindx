package com.example.demo01.core.Auth.dtos;

import com.example.demo01.core.Auth.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
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
    private List<WorkProfile> workProfiles;


    public CustomUserDetails(User user) {
        this._id = user.get_id();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.staffId = user.getStaffId();
        this.systemRole = user.getSystemRole();
        this.workProfiles = user.getWorkProfileList();
        this.authorities = workProfiles.stream()
                .map(profile -> {
                    String roleName = profile.getPositionCode();
                    return new SimpleGrantedAuthority("ROLE_"+ roleName);
                }).toList();
    }

    public boolean checkPermission(String deptId, int minLevel) {
        if (workProfiles == null) return false;
        return workProfiles.stream()
                .anyMatch(p -> p.getDepartmentId().equals(deptId) && p.getPositionLevel() >= minLevel);
    }

    public boolean isGlobalAdmin() {
        return systemRole.equals("ADMIN") || workProfiles.stream()
                .anyMatch(p -> p.getPositionLevel() > 3 );
    }

    public List<String> getAllowedLocations() {
        if (isGlobalAdmin()) {
            return null;
        }
        return workProfiles.stream()
                .flatMap(w -> w.getBuAllowedList().stream())
                .distinct()
                .collect(Collectors.toList());
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
