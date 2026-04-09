package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.controller;

import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.model.provinceModel;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/provinces")
public class ProvinceController {

    private final ProvinceService provinceService;

    @GetMapping
    public ResponseEntity<?> createProduct() {
        List<provinceModel> provinceModels = provinceService.getAllProvinces();
        return ResponseEntity.ok(provinceModels);
    }

}
