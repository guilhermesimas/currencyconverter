package com.cortex.currencyconverter.controllers;

import com.cortex.currencyconverter.services.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final ConverterService converterService;

    @GetMapping("convert")
    public ResponseEntity<String> convertCurrency(){
        return ResponseEntity.ok(converterService.convert());
    }
}
