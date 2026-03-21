package com.example.demo01.domains.MiniCrm.Contract.controller;

import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateCollectionInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.dtos.terminateCollection.TerminateRequest;
import com.example.demo01.domains.MiniCrm.Contract.service.TerminateCollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/space/v1/terminate-contracts")
@RequiredArgsConstructor
public class TerminateContractController {

    private final TerminateCollectionService terminateCollectionService;

    @PostMapping
    public ResponseEntity<?> createTerminateRequest(@RequestBody @Valid TerminateRequest terminateRequest) {
        TerminateCollectionInfoDto collectionInfoDto = terminateCollectionService.createTerminateRequest(terminateRequest);
        return ResponseEntity.ok(collectionInfoDto);
    }

}
