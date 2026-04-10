package com.example.demo01.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.f4b6a3.ulid.UlidCreator;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.Nullable;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AppUtil {

    public BigDecimal calculateGrowthBetweenTwoValue(BigDecimal current, BigDecimal last) {
        if (last.compareTo(BigDecimal.ZERO) == 0) {
            return current.compareTo(BigDecimal.ZERO) > 0 ? BigDecimal.valueOf(100) : BigDecimal.ZERO;
        }

        // (Current - Last) / Last * 100
        return current.subtract(last)
                .divide(last, 4, RoundingMode.HALF_UP) // Giữ 4 số lẻ để nhân 100 chính xác
                .multiply(BigDecimal.valueOf(100));
    }

    public String handleSubString(String input, int length, Boolean dimension) {
        if (input == null) {
            return "";
        }
        if (input.length() <= length) {
            return input;
        }
        return dimension
                ? input.substring(0, length)
                : input.substring(input.length() - length);
    }


    public String handleGetFileNameWithoutExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return fileName;
        }
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    public String handleGetFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }

    public String extractDuplicateField(String errorMessage) {
        if (errorMessage == null) return "dữ liệu";

        // Pattern tìm tên field trong ngoặc nhọn sau chữ 'index:'
        // Ví dụ: index: buShortName_1 dup key... -> lấy 'buShortName'
        Pattern pattern = Pattern.compile("index:\\s*(\\w+)_?\\d*\\s*dup key");
        Matcher matcher = pattern.matcher(errorMessage);

        if (matcher.find()) {return matcher.group(1);
        }
        return "Trường dữ liệu";
    }

    public String generateFileKey() {
        return UlidCreator.getUlid().toString();
    }


}
