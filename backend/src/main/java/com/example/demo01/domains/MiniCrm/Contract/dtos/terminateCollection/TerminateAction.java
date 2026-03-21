package com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TerminateAction {

    private String actionType;
    private String updateValue;
    private String itemId;
}
