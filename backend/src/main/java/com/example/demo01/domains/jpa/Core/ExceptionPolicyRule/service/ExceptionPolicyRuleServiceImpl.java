package com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.service;

import com.example.demo01.core.Auth.services.UserService;
import com.example.demo01.core.Exceptions.ResourceNotFoundException;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleInfoDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.dtos.ExceptionPolicyRuleRequestDto;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.entity.ExceptionPolicyRuleEntity;
import com.example.demo01.domains.jpa.Core.ExceptionPolicyRule.mappers.ExceptionPolicyRuleMapper;
import com.example.demo01.repository.mongo.CoreRepo.AuthRepositories.UserRepository;
import com.example.demo01.repository.postgreSQL.Core.PolicyRuleRepository.PolicyRuleRepository;
import com.example.demo01.utils.BasePageResponse;
import com.example.demo01.utils.FilterWithPagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExceptionPolicyRuleServiceImpl implements ExceptionPolicyRuleService {

    @Autowired
    private PolicyRuleRepository policyRuleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExceptionPolicyRuleMapper exceptionPolicyRuleMapper;

    @Override
    public ExceptionPolicyRuleInfoDto createNewPolicyRule(ExceptionPolicyRuleRequestDto requestDto) {
        String userId = requestDto.getStaffId();
        boolean isValid = userRepository.existsByStaffId(userId);
        if (!isValid) {
            throw new ResourceNotFoundException("User", "staffId", userId);
        }
        ExceptionPolicyRuleEntity exceptionPolicyRuleEntity = exceptionPolicyRuleMapper.fromRequestToEntity(requestDto);

        ExceptionPolicyRuleEntity exceptionPolicyRuleEntitySaved = policyRuleRepository.save(exceptionPolicyRuleEntity);
        return exceptionPolicyRuleMapper.fromEntityToDto(exceptionPolicyRuleEntitySaved);
    }

    @Override
    public ExceptionPolicyRuleInfoDto updatePolicyRule(Long id, ExceptionPolicyRuleRequestDto policyRuleInfoDto) {
        ExceptionPolicyRuleEntity exceptionPolicyRuleEntity = getPolicyRule(id);
        exceptionPolicyRuleMapper.updateEntityFromDto(policyRuleInfoDto, exceptionPolicyRuleEntity);
        ExceptionPolicyRuleEntity exceptionPolicyRuleEntitySaved = policyRuleRepository.save(exceptionPolicyRuleEntity);
        return exceptionPolicyRuleMapper.fromEntityToDto(exceptionPolicyRuleEntitySaved);
    }

    @Override
    public List<ExceptionPolicyRuleInfoDto> getPolicyRuleInfoByUserId(String userId) {
        List<ExceptionPolicyRuleEntity> policyRuleEntities = policyRuleRepository.findByStaffId(userId);
        return exceptionPolicyRuleMapper.fromEntitiesToDtos(policyRuleEntities);
    }

    @Override
    public ExceptionPolicyRuleEntity getPolicyRule(Long id) {
        return policyRuleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PolicyRule", "id", id));
    }

    @Override
    public ExceptionPolicyRuleInfoDto getPolicyRuleInfo(Long id) {
        ExceptionPolicyRuleEntity exceptionPolicyRuleEntity = getPolicyRule(id);
        return exceptionPolicyRuleMapper.fromEntityToDto(exceptionPolicyRuleEntity);
    }

    @Override
    public BasePageResponse<ExceptionPolicyRuleInfoDto> getAllPolicyRules(FilterWithPagination filterWithPagination) {
        return null;
    }

    @Override
    public BasePageResponse<ExceptionPolicyRuleInfoDto> buildPageResponse(Page<ExceptionPolicyRuleEntity> page) {
        return null;
    }

    @Override
    public void deletePolicyRule(Long id) {
        	ExceptionPolicyRuleEntity exceptionPolicyRuleEntity = getPolicyRule(id);
            policyRuleRepository.delete(exceptionPolicyRuleEntity);
    }
}
