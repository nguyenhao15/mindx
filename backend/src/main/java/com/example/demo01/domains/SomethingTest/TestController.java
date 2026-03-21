package com.example.demo01.domains.SomethingTest;

import com.example.demo01.core.Aws3.service.S3ServiceImpl;
import com.example.demo01.core.Basement.dto.basement.BURequestDto;
import com.example.demo01.core.Basement.service.BasementService;
import com.example.demo01.domains.MiniCrm.Contract.repository.AppendixRepository;
import com.example.demo01.domains.MiniCrm.Contract.service.AppendixService;
import com.example.demo01.domains.MiniCrm.Dimmesion.repository.ProductRepository;
import com.example.demo01.utils.AppUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/space/test")
public class TestController {

    private final BasementService  basementService;

    @PostMapping("/create-batch")
    public void createBatch(@Valid @RequestBody List<BURequestDto> buRequestDtos) {
        System.out.println("Something createBatch");
        basementService.createBus(buRequestDtos);
    }

}
