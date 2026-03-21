package com.example.demo01.domains.MiniCrm.Contract.service;

import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateCollectionInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateRequest;

public interface TerminateCollectionService {
    TerminateCollectionInfoDto createTerminateRequest(TerminateRequest terminateRequest);

    TerminateCollectionInfoDto getTerminateRequestById(String id);

    TerminateCollectionInfoDto getTerminateByAppendixCode(String appendixCode);

    TerminateCollectionInfoDto updateTerminateRequest(String id, TerminateRequest terminateRequest);

    void deleteTerminateRequest(String id);
}
