package com.example.demo01.domains.mongo.HRManagment.Department.mapper;

import com.example.demo01.domains.mongo.HRManagment.Department.dto.Position.PositionDto;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Position.PositionRequest;
import com.example.demo01.domains.mongo.HRManagment.Department.model.PositionModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionModel toPositionModel(PositionRequest request);

    PositionDto toPositionDto(PositionModel model);

    List<PositionDto> toDtoPositionList(List<PositionModel> positionModels);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePositionModelFromRequest(PositionRequest request,@MappingTarget PositionModel positionModel);

}
