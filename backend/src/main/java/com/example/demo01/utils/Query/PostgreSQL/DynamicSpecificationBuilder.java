package com.example.demo01.utils.Query.PostgreSQL;

import com.example.demo01.utils.FilterRequest;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import java.lang.reflect.Array;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DynamicSpecificationBuilder<T> {


    public Specification<T> build(
            List<FilterRequest> filters,
            Map<String, Specification<T>> baseSpecs
    ) {
        Specification<T> finalSpec = Specification.allOf();
        Set<String> baseFields = new HashSet<>();


        if (baseSpecs != null && !baseSpecs.isEmpty()) {
            for (Map.Entry<String, Specification<T>> entry : baseSpecs.entrySet()) {
                finalSpec = finalSpec.and(entry.getValue());
                baseFields.add(entry.getKey()); // Ghi lại các field đã được bảo vệ
            }
        }

        if (filters != null && !filters.isEmpty()) {
            for (FilterRequest filter : filters) {
                if (baseFields.contains(filter.getField())) continue;

                if (filter.getValue() == null) continue; // Bỏ qua filter có giá trị null


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
                        if (list.isEmpty()) return null;
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

    private Comparable castToComparable(Object value) {
        if (value instanceof Comparable) {
            return (Comparable) value;
        }
        try {
            return Instant.parse(value.toString());
        } catch (Exception e) {
            throw new RuntimeException("Invalid comparable value: " + value);
        }
    }

//    private Set<String> extractFields(List<Specification<T>> filters) {
//        return filters.stream()
//                .map(Specification::getField)
//                .collect(Collectors.toSet());
//        }


}