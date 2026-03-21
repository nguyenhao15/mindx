package com.example.demo01.core.Basement.dto.basement;

import com.example.demo01.core.Basement.dto.room.RoomInfoDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BUInfoDto {

    private String id;

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
