package com.example.demo01.utils;

import com.example.demo01.configs.SecureRepoConfig.SecurityRepoUtilImpl;
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

    private final MongoTemplate mongoTemplate;

    private final SecurityRepoUtilImpl securityRepoUtil;

    private final ObjectMapper objectMapper;

    private final CacheManager cacheManager;

    public Query applyFilter(@Nullable List<FilterRequest> filters,@Nullable List<Criteria> baseCriteriaList) {
        Query query = new Query();
        List<Criteria> finalCriteriaList = new ArrayList<>();
        Set<String> filterKeyFromBaseCriteria = new HashSet<>();

        if (baseCriteriaList != null && !baseCriteriaList.isEmpty()) {
            finalCriteriaList.addAll(baseCriteriaList);
            filterKeyFromBaseCriteria = baseCriteriaList.stream()
                    .filter(Objects::nonNull)
                    .flatMap(criteria -> criteria.getCriteriaObject().keySet().stream())
                    .collect(Collectors.toSet());
        }

        if (filters != null && !filters.isEmpty()) {
            for (FilterRequest filter : filters) {
                if (filterKeyFromBaseCriteria.contains(filter.getField())) {
                    continue;
                }
                Criteria c = buildCriteriaFromFilter(filter);
                if (c != null) {
                finalCriteriaList.add(c);
                };
            }
        };

        if (!finalCriteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(finalCriteriaList.toArray(new Criteria[0])));
        }
//        TODO: XỬ LÝ PHẦN RETURN CŨ
//        return securityRepoUtil.createSecureQuery(query);
        return query;
    }

    private Criteria buildCriteriaFromFilter(FilterRequest filter) {
        String field = filter.getField();
        Object value = filter.getValue();
        FilterOperator operator = filter.getOperator();
        switch (operator) {
            case EQUALS:
                if (!value.toString().isBlank()) {
                    return Criteria.where(field).is(value);
                }
                break;
            case LIKE:
                if (!value.toString().isBlank()) {
                    return Criteria.where(field).regex(".*" + value + ".*", "i");
                }
                break;
            case IN:
                if (value instanceof List) {
                    return Criteria.where(field).in((List<?>) value);
                }
                break;
            case BETWEEN:
                if (value instanceof List && ((List<?>) value).size() == 2) {
                    List<?> date = (List<?>) value;
                    try {
                        Object startDate = Instant.parse(date.get(0).toString());
                        Object endDate = Instant.parse(date.get(1).toString());
                        return Criteria.where(field).gte(startDate).lte(endDate);
                    } catch (Exception e) {
                        return null;
                    }
                }
                break;
            case GTE:
                return Criteria.where(field).gte(value);
            case LTE:
                return Criteria.where(field).lte(value);
        }
        return null;
    }

    public Map<String, Object> mapIdsToField(String collectionName,String lookupTarget, List<String> lookupValues, String targetField) {
        if (lookupValues == null || lookupValues.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, Object> finalResults = new HashMap<>();
        List<String> missingValues = new ArrayList<>();

        Cache cache = cacheManager.getCache(collectionName +"_Cache");
        String CACHE_ALL_KEY = "all_"+collectionName+"_list";

        if (cache != null && cache.get(CACHE_ALL_KEY) != null) {
            List<?> cachedData = cache.get(CACHE_ALL_KEY, List.class);

            for (String value : lookupValues) {
                Optional<?> match = cachedData.stream()
                        .filter(item -> {
                            Map<String, Object> map = objectMapper.convertValue(item, Map.class);
                            return value.equals(String.valueOf(map.get(lookupTarget)));
                        })
                        .findFirst();

                if (match.isPresent()) {
                    Map<String, Object> map = objectMapper.convertValue(match.get(), Map.class);
                    finalResults.put(value, map.getOrDefault(targetField, "N/A"));
                } else {
                    missingValues.add(value);
                }
            }
        } else  {
            missingValues.addAll(lookupValues);
        }

        if (missingValues.isEmpty()) {
            return finalResults;
        }

        List<Object> queryValues = missingValues.stream()
                .map(val -> Objects.equals(lookupTarget, "_id") ? new ObjectId(val) : val)
                .collect(Collectors.toList());

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where(lookupTarget).in(lookupValues)),
                Aggregation.project(lookupTarget, targetField)
        );

        AggregationResults<Document> results = mongoTemplate.aggregate(
                aggregation,
                collectionName,
                Document.class
        );

        results.getMappedResults().forEach(doc -> {
            String key = doc.get(lookupTarget).toString();
            finalResults.put(key, doc.getOrDefault(targetField, "N/A"));
        });

        return finalResults;
    }

    public <T> Page<T> buildPageResponse(List<FilterRequest> filterRequests,
                                         List<Criteria> criteriaList,
                                         PageInput pageInput,
                                         Class<T> entityClass
    ) {
        Pageable pageable = pageInput.toPageable();
        Query query = applyFilter(filterRequests, criteriaList).with(pageable);

        List<T> list = mongoTemplate.find(query, entityClass);
        long total = mongoTemplate.count(query.skip(-1).limit(-1), entityClass);

        return new PageImpl<T>(list, pageable, total);
    }

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
