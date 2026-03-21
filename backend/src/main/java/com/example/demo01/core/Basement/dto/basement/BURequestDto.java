package com.example.demo01.core.Basement.dto.basement;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BURequestDto {

    @NotBlank(message = "buFullName is required")
    private String buFullName;

    @NotBlank(message = "buId is required")
    private String buId;

    @NotBlank(message = "Accountant Code is required")
    private String accountantCode;

    @NotBlank(message = "buType is required")
    private String buType;

    @NonNull
    private Boolean active;

    private Double size;

    private String address;

    @NotBlank(message = "city is required")
    private String city;


    private String areaFullName;
}
