package com.example.demo01.core.Basement.dto.basement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BUPatchRequestDto {

    private String buFullName;

    private String buId;

    private String accountantCode; // Dùng camelCase cho đúng chuẩn Java

    private String buType;
    private Boolean active;

    private Double size;

    private String address;

    private String city;
    private String areaFullName;

}
