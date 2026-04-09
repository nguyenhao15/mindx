package com.example.demo01.domains.mongo.MiniCrm.Renting.model;


import com.example.demo01.utils.BaseModels.BusinessUnitEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Renting")
public class RentingRecord extends BusinessUnitEntity {

    @Id
    @Indexed
    private String _id;

    @Indexed
    private String contractId;

    @Indexed
    private String roomShortName;

    @Indexed
    private String serviceShortName;

    @Indexed
    private String customerId;

    private LocalDate assigned_from;

    private LocalDate assigned_to;

    private LocalDate actualEndDate;

    @Indexed
    private RentingStatus status;

    @Indexed
    private Boolean active;

}
