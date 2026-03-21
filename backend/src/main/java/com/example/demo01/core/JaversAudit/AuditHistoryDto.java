package com.example.demo01.core.JaversAudit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * DTO đại diện cho một bản ghi lịch sử thay đổi.
 * Thiết kế theo hướng "Read-Optimized" cho Frontend ReactJS.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditHistoryDto {
    private String commitId;
    private long version;
    private LocalDateTime changeDate;
    private String commitType;  // Add this field
    private String author;
    private Map<String, String> metadata;
    private List<String> changedFields;
    private Map<String, Object> state;
}