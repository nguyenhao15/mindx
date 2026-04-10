package com.example.demo01.utils.Query.PostgreSQL;

import com.example.demo01.utils.FilterRequest;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class DynamicSpecificationBuilder<T> {

    public Specification<T> build(
            List<FilterRequest> filters,
            List<Specification<T>> baseSpecs
    ) {
        Specification<T> finalSpec = null;

        // 🔒 1. Add base conditions (security, tenant, ...)
        Set<String> baseFields = new HashSet<>();

        if (baseSpecs != null && !baseSpecs.isEmpty()) {
            for (Specification<T> spec : baseSpecs) {
                finalSpec = finalSpec.and(spec);
            }
        }

        // 👉 Optional: nếu fen muốn block override field từ base
        if (filters != null && baseSpecs != null) {
            baseFields = extractFields(filters); // simplified
        }

        // 🔍 2. Add dynamic filters
        if (filters != null && !filters.isEmpty()) {
            for (FilterRequest filter : filters) {

                if (baseFields.contains(filter.getField())) continue;

                Specification<T> spec = buildSingleFilter(filter);

                if (spec != null) {
                    finalSpec = finalSpec.and(spec);
                }
            }
        }

        return finalSpec;
    }

    private Specification<T> buildSingleFilter(FilterRequest filter) {
        return (root, query, cb) -> {

            Path<?> path = getPath(root, filter.getField());
            Object value = filter.getValue();

            if (value == null) return null;

            switch (filter.getOperator()) {

                case EQUALS:
                    return cb.equal(path, value);

                case LIKE:
                    return cb.like(
                            cb.lower(path.as(String.class)),
                            "%" + value.toString().toLowerCase() + "%"
                    );

                case IN:
                    if (value instanceof List<?> list) {
                        CriteriaBuilder.In<Object> inClause = cb.in(path);
                        for (Object v : list) {
                            inClause.value(v);
                        }
                        return inClause;
                    }
                    break;

                case BETWEEN:
                    if (value instanceof List<?> list && list.size() == 2) {
                        Comparable start = castToComparable(list.get(0));
                        Comparable end = castToComparable(list.get(1));

                        return cb.between((Path<Comparable>) path, start, end);
                    }
                    break;

                case GTE:
                    return cb.greaterThanOrEqualTo(
                            (Path<Comparable>) path,
                            castToComparable(value)
                    );

                case LTE:
                    return cb.lessThanOrEqualTo(
                            (Path<Comparable>) path,
                            castToComparable(value)
                    );
            }

            return null;
        };
    }

    /**
     * SUPPORT: handle nested field (user.name)
     */
    private Path<?> getPath(From<?, ?> root, String field) {
        if (field.contains(".")) {
            String[] parts = field.split("\\.");
            From<?, ?> join = root;

            for (int i = 0; i < parts.length - 1; i++) {
                join = join.join(parts[i], JoinType.LEFT);
            }

            return join.get(parts[parts.length - 1]);
        }

        return root.get(field);
    }

    /**
     * SUPPORT: cast value safely
     */
    private Comparable castToComparable(Object value) {
        if (value instanceof Comparable) {
            return (Comparable) value;
        }

        // xử lý string date → Instant
        try {
            return Instant.parse(value.toString());
        } catch (Exception e) {
            throw new RuntimeException("Invalid comparable value: " + value);
        }
    }

    /**
     * OPTIONAL: extract fields
     */
    private Set<String> extractFields(List<FilterRequest> filters) {
        return filters.stream()
                .map(FilterRequest::getField)
                .collect(Collectors.toSet());
    }
}