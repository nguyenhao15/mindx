package com.example.demo01.domains.MiniCrm.Contract.models;

import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateAction;
import com.example.demo01.utils.BusinessUnitEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "terminateCollections")
public class TerminateModel extends BusinessUnitEntity {

    @Id
    private String _id;

    @Indexed(unique = true)
    private String appendixCode;

    @Indexed
    private String customerId;

    private LocalDate updateDate;

    private List<TerminateAction> actions;

    private String terminateReason;

    private String updateType;

}
