package com.example.demo01.domains.mongo.MiniCrm.Contract.models;

import com.example.demo01.core.Aws3.dtos.FileResponseDTO;
import com.example.demo01.utils.BaseEntity.Mongo.BusinessUnitEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "contracts")
public class Contract extends BusinessUnitEntity {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String contractCode;

    private String serviceId;

    private FileResponseDTO contractPath;

    @Indexed
    private String customerId;

    private LocalDate startDate;

    @Indexed
    private LocalDate endDate;

    private String contractStatus;

}
