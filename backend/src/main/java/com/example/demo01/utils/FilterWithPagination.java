package com.example.demo01.utils;

import lombok.Data;

import java.util.List;

@Data
public class FilterWithPagination {
    private List<FilterRequest> filters;
    private PageInput pagination;
}
