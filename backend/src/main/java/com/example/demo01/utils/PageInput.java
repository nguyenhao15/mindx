package com.example.demo01.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageInput {
    private int page;
    private int size;
    private List<SortOrder> sorts;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SortOrder {
        private String property;
        private String direction; // "ASC" hoặc "DESC"
    }

    public Pageable toPageable() {
        Sort sort = Sort.unsorted();
        if (sorts != null && !sorts.isEmpty()) {
            for (SortOrder sortOrder : sorts) {
                Sort.Order order = new Sort.Order(Sort.Direction.fromString(sortOrder.getDirection()), sortOrder.getProperty());
                sort = sort.and(Sort.by(order));
            }
        }
        return org.springframework.data.domain.PageRequest.of(page, size, sort);
    }
}
