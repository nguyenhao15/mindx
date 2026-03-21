package com.example.demo01.domains.MiniCrm.Contract.service.impl;

import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateAction;
import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateCollectionInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateRequest;
import com.example.demo01.domains.MiniCrm.Contract.mappers.TerminateUpdateCollectionMapper;
import com.example.demo01.domains.MiniCrm.Contract.models.TerminateModel;
import com.example.demo01.domains.MiniCrm.Contract.repository.TerminateModelRepository;
import com.example.demo01.domains.MiniCrm.Contract.service.AppendixService;
import com.example.demo01.domains.MiniCrm.Contract.service.TerminateCollectionService;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminateCollectionServiceImpl implements TerminateCollectionService {

    private final TerminateModelRepository terminateModelRepository;

    private final AppendixService appendixService;

    private final TerminateUpdateCollectionMapper terminateUpdateCollectionMapper;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public TerminateCollectionInfoDto createTerminateRequest(TerminateRequest terminateRequest) {
        String appendixCode = terminateRequest.getAppendixCode();
        AppendixInfoDto appendix = appendixService.getAppendixByAppendixCode(appendixCode);
        List<TerminateAction> terminateActions = appendixService.terminateAppendix(appendix.getAppendixCode(), terminateRequest.getUpdateDate());
        terminateRequest.setActions(terminateActions);
        TerminateModel createdItem = terminateModelRepository.save(terminateUpdateCollectionMapper.toEntity(terminateRequest));
        return terminateUpdateCollectionMapper.toDTO(createdItem);
    }

    @Override
    public TerminateCollectionInfoDto getTerminateRequestById(String id) {
        TerminateModel terminateModel = terminateModelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TerminateUpdateCollection", "id", id));
        return terminateUpdateCollectionMapper.toDTO(terminateModel);
    }

    @Override
    public TerminateCollectionInfoDto getTerminateByAppendixCode(String appendixCode) {
        return null;
    }

    @Override
    public TerminateCollectionInfoDto updateTerminateRequest(String id, TerminateRequest terminateRequest) {
        return null;
    }

    @Override
    public void deleteTerminateRequest(String id) {

    }
}
