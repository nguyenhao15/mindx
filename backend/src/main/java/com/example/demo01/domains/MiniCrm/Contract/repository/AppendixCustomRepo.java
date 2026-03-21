package com.example.demo01.domains.MiniCrm.Contract.repository;

import com.example.demo01.domains.MiniCrm.Contract.dtos.appendix.AppendixInfoDto;
import com.example.demo01.domains.MiniCrm.Contract.models.Appendix;

import java.util.List;

public interface AppendixCustomRepo {
    List<AppendixInfoDto> getActiveAppendixByCustomerIdAndBuId(String customerId, String buId);
    List<Appendix> getActiveAppendixByBuShortNameAndServiceIDAndCustomerId(String buShortName, String serviceId, String customerId);


}
