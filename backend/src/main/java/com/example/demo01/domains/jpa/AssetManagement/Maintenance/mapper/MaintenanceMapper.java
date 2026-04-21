package com.example.demo01.domains.jpa.AssetManagement.Maintenance.mapper;

import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceCategoryMapper;
import com.example.demo01.domains.jpa.AssetManagement.Dimmensions.mappers.MaintenanceItemMapper;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceDetailsInfoDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceRequestDto;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.dtos.Maintenance.MaintenanceSummaryDTO;
import com.example.demo01.domains.jpa.AssetManagement.Maintenance.entities.MaintenanceEntity;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring",
uses = {
        MaintenanceCategoryMapper .class,
        MaintenanceItemMapper.class,
        MaintenancesProposalMapper.class,
})
public interface MaintenanceMapper {

    MaintenanceEntity fromRequestToEntityMaintenance(MaintenanceRequestDto requestDto);

    @Mapping(target = "totalProposals", expression = "java(entity.getMaintenancesProposals().size())")
    @Mapping(target = "locationName", expression = "java(String.valueOf(lookupMap.getOrDefault(entity.getLocationId(), \"N/A\"))) ")
    MaintenanceSummaryDTO fromEntityToMaintenanceInfoDto(MaintenanceEntity entity,
                                                         @Context Map<String, Object> lookupMap);

    List<MaintenanceSummaryDTO> fromEntitiesToSummaryInfo(List<MaintenanceEntity> entities,
                                                          @Context Map<String, Object> lookupMap);

    default List<MaintenanceSummaryDTO> toSummaryList(List<MaintenanceEntity> entities,
                                                      @Context Map<String, Object> lookupMap) {
        if (entities == null) return null;
        return fromEntitiesToSummaryInfo(entities, lookupMap);
    }

    @Mapping(target = "totalProposals", expression = "java(entity.getMaintenancesProposals().size())")
    @Mapping(target = "locationName", expression = "java(String.valueOf(lookupMap.getOrDefault(entity.getLocationId(), \"N/A\"))) ")
    MaintenanceDetailsInfoDto fromEntityToMaintenanceDetailsInfoDto(MaintenanceEntity entity,
                                                                    @Context Map<String, Object> lookupMap);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(MaintenanceRequestDto requestDto, @MappingTarget MaintenanceEntity entity);

}
