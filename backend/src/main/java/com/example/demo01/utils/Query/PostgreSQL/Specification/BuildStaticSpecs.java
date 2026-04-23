package com.example.demo01.utils.Query.PostgreSQL.Specification;

import com.example.demo01.utils.ModuleEnum;
import org.springframework.data.jpa.domain.Specification;

public interface BuildStaticSpecs {

    <T> Specification<T> validLocation(String locationFieldName);

    <T> Specification<T> isNotDeleted(String localIsDeletedFieldName);

    <T> Specification<T> buildDynamicSpecification(ModuleEnum moduleEnum, SpecFieldName specFieldName);
}
