package com.example.demo01.utils.Query.PostgreSQL;

import com.example.demo01.configs.SecureRepoConfig.SecurityRepoUtil;
import com.example.demo01.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StaticSpecs {

    @Autowired
    private AppUtil appUtil;

    @Autowired
    private SecurityRepoUtil securityRepoUtil;

    public <T> Specification<T> validLocation(String locationFieldName) {
        List<String> allowLocations = securityRepoUtil.getCurrentAllowedLocations();
        if (allowLocations == null || allowLocations.isEmpty()) {
            return (root, query, cb) -> cb.disjunction();
        }
        return (root, query, cb) -> root.get(locationFieldName).in(allowLocations);
    }

    public static <T> Specification<T> isNotDeleted() {
        return (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
    }
}
