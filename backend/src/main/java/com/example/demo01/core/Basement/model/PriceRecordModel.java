package com.example.demo01.core.Basement.model;

import com.example.demo01.utils.BaseModels.BusinessUnitEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "priceRecords")
public class PriceRecordModel extends BusinessUnitEntity {

    @Id
    private String _id;

    @Indexed
    private String buShortName;
    private Double hotDeskPrice;
    private Double valuePerSize;

}
