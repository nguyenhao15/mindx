package com.example.demo01.domains.mongo.MiniCrm.Dimmesion.controller;

import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.dtos.Currency.CurrencyPatchRequest;
import com.example.demo01.domains.mongo.MiniCrm.Dimmesion.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/currency")
@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    public ResponseEntity<?> getCurrencies() {
        return ResponseEntity.ok(currencyService.getAllCurrency());
    }

    @GetMapping("/active")
    public ResponseEntity<?> getActiveCurrencies() {
        return ResponseEntity.ok(currencyService.getActiveCurrency(true));
    }

    @PostMapping
    public ResponseEntity<?> createCurrency(CurrencyPatchRequest currencyPatchRequest) {
        return ResponseEntity.ok(currencyService.createNewCurrency(currencyPatchRequest));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCurrency(@PathVariable String id, @RequestBody CurrencyPatchRequest currencyPatchRequest) {
        return ResponseEntity.ok(currencyService.updateCurrency(id, currencyPatchRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurrencyById(@PathVariable String id) {
        return ResponseEntity.ok(currencyService.getCurrency(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable String id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }

}
