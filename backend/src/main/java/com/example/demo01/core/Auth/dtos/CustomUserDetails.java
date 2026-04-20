package com.example.demo01.core.Auth.dtos;

import com.example.demo01.core.Auth.models.User;
import com.example.demo01.domains.mongo.HRManagment.HumanResource.dto.StaffProfileInfoDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    private String profileId;
    private String position;
    private String positionTitle;
    private String departmentId;
    private String departmentTitle;
    private int PositionLevel;
    private List<String> allowLocationIds;

    public CustomUserDetails(User user,@Nullable StaffProfileInfoDto staffProfileInfoDto) {
        this._id = user.get_id();

        this.username = user.getUsername();

        this.fullName = user.getFullName();

        this.password = user.getPassword();

        this.email = user.getEmail();

        this.staffId = user.getStaffId();

        this.systemRole = user.getSystemRole();

        if (staffProfileInfoDto != null) {
            this.profileId = staffProfileInfoDto.id();
            this.positionTitle = staffProfileInfoDto.positionName();
            this.position = staffProfileInfoDto.positionId();
            this.departmentId = staffProfileInfoDto.departmentId();
            this.departmentTitle = staffProfileInfoDto.departmentName();
            this.allowLocationIds = staffProfileInfoDto.buAllowedList();
            this.PositionLevel = staffProfileInfoDto.positionLevel();
        }

        List<SimpleGrantedAuthority> auths = new ArrayList<>();

        auths.add(new SimpleGrantedAuthority("ROLE_" + user.getSystemRole()));
        this.authorities = auths;
    }


    public boolean isGlobalAdmin() {
        return systemRole.equals("ADMIN") || this.getPositionLevel() > 2;
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
