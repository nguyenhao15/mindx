package com.example.demo01.domains.mongo.HRManagment.Department.service;

import com.example.demo01.domains.mongo.HRManagment.Department.dto.Position.PositionDto;
import com.example.demo01.domains.mongo.HRManagment.Department.dto.Position.PositionRequest;
import com.example.demo01.domains.mongo.HRManagment.Department.model.PositionModel;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PositionModelService {

    PositionModel createPosition(PositionRequest request);

    BasePageResponse<PositionDto> getAllPositions(FilterWithPagination filter);

    BasePageResponse<PositionDto> buildPageResponse(Page<PositionModel> positionModels);

    List<PositionModel> getPositionByActive(Boolean isActive);

    List<PositionModel> getPositionsByDepartmentId(String departmentId);

    List<PositionModel> getCurrentWorkingPosition();

    PositionModel getPositionById(String id);

    PositionModel updatePosition(String id, PositionRequest request);

    void deletePosition(String id);

}
