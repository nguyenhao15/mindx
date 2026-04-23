package com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.controller;

import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleInfoDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleRequestDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.service.ExceptionPolicyRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/policy")
public class ExceptionPolicyRuleController {

    @Autowired
    private ExceptionPolicyRuleService exceptionPolicyRuleService;

    @PostMapping
    public ResponseEntity<?> createNewPolicyRuleInfo(@RequestBody ExceptionPolicyRuleRequestDto requestDto) {
        ExceptionPolicyRuleInfoDto createdPolicyRuleInfo = exceptionPolicyRuleService.createNewPolicyRule(requestDto);
        return ResponseEntity.ok().body(createdPolicyRuleInfo);
    };

}
