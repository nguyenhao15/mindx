package com.example.demo01.core.Basement.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoomPatchRequest {
    private String roomShortName;
    private String buShortName;

    private String roomName;
    private String roomTag;

    private Boolean commercial;


    private Double roomSize;

    private Boolean isActive;
}
