package com.example.demo01.core.Basement.mapper;

import com.example.demo01.core.Basement.dto.room.RoomInfoDto;
import com.example.demo01.core.Basement.dto.room.RoomPatchRequest;
import com.example.demo01.core.Basement.dto.room.RoomRequestDto;
import com.example.demo01.core.Basement.model.RoomModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomModel toEntity(RoomRequestDto requestDto);
    RoomInfoDto toDto(RoomModel roomModel);

    List<RoomInfoDto> toDtoList(List<RoomModel> roomModels);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateRoomFromDto(RoomPatchRequest patchRequest, @MappingTarget RoomModel roomModel);

}
