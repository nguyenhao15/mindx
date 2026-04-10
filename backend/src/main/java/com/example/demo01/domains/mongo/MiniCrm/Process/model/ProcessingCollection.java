package com.example.demo01.domains.mongo.MiniCrm.Process.model;

import com.example.demo01.utils.BaseEntity.Mongo.BusinessUnitEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@Document(collection = "processing_collection")
@AllArgsConstructor
@NoArgsConstructor
public class ProcessingCollection extends BusinessUnitEntity {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String processCode;

    private String customerId;

    private String updateDescription;

    private String status;

    private Boolean active;
}
