package com.example.demo01.domains.jpa.Core.Approval.controller;

import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyInfoDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.Approval.ApprovalPolicyRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.dto.WorkFlowTransition.WorkFlowTransitionRequestDto;
import com.example.demo01.domains.jpa.Core.Approval.service.ApprovalPolicyService;
import com.example.demo01.utils.FilterWithPagination;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/approval-policy")
public class ApprovalPolicyController {

    @Autowired
    private ApprovalPolicyService approvalPolicyService;


    @PostMapping
    public ResponseEntity<?> createApprovalPolicy(@RequestBody @Valid ApprovalPolicyRequestDto requestDto) {
        ApprovalPolicyInfoDto approvalPolicyInfoDto = approvalPolicyService.createApprovalPolicy(requestDto);
        return ResponseEntity.ok().body(approvalPolicyInfoDto);
    }

    @PostMapping("/get/page")
    public ResponseEntity<?> getApprovalPolicies(@RequestBody FilterWithPagination filter) {
        return ResponseEntity.ok(approvalPolicyService.getAllApprovalPolicies(filter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApprovalPolicy(@PathVariable Long id ,ApprovalPolicyRequestDto requestDto) {
        ApprovalPolicyInfoDto approvalPolicyInfoDto = approvalPolicyService.updateApprovalPolicy(id,requestDto);
        return ResponseEntity.ok().body(approvalPolicyInfoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteApprovalPolicy(@PathVariable Long id) {
        approvalPolicyService.deleteApprovalPolicy(id);
        return ResponseEntity.ok().build();
    }

}
