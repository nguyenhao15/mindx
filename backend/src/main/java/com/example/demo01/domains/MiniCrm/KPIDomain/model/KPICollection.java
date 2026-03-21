package com.example.demo01.domains.MiniCrm.KPIDomain.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collation = "KPI")
public class KPICollection {

    @Id
    private String _id;

    private BigDecimal target;

    private LocalDate month;
    private String buShortName;

}
