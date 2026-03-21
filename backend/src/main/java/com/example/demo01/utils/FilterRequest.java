package com.example.demo01.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterRequest {
  private String field;
  private Object value;
  private FilterOperator operator;
}
