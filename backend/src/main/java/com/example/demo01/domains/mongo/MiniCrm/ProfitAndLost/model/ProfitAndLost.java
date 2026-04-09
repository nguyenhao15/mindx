package com.example.demo01.domains.mongo.MiniCrm.ProfitAndLost.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "pnl")
public class ProfitAndLost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String _id;

    private Double totalAllocationAmount;

    private Double localAmount;
    private Double allocatedAmount;
    private Double remainAmount;

    private LocalDate allocationDate;
    private Integer usingDays;

    private String buId;
    private String serviceId;

    @Indexed
    private String trackId;

    @Indexed
    private String contractId;

}
