package com.example.demo01.utils.Query.PostgreSQL;

import com.example.demo01.utils.PageInput;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostgreSQLPageUtil {

    private final DynamicSpecificationBuilder<?> specBuilder;

    public <T, R extends JpaSpecificationExecutor<T>> Page<T> buildPageResponse(
            Specification<T> baseSpecs,
            PageInput pageInput,
            R repository
    ) {
        Pageable pageable = pageInput.toPageable();

        return repository.findAll(baseSpecs, pageable);
    }
}
