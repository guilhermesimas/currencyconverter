package com.cortex.currencyconverter.controllers;

import com.cortex.currencyconverter.contracts.ConversionResultTO;
import com.cortex.currencyconverter.services.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final ConverterService converterService;

    @GetMapping("convert/{amount}/from/{from}/to/{to}/on/{when}")
    public ResponseEntity<ConversionResultTO> convertCurrency(@PathVariable("amount") Double amount,
                                                              @PathVariable("from") String from,
                                                              @PathVariable("to") String to,
                                                              @PathVariable("when") String when){
        return ResponseEntity.ok(new ConversionResultTO(converterService.convert(amount, from, to ,
                LocalDate.parse(when))));
    }

    @GetMapping("currencies")
    public ResponseEntity<Map<String, Integer>> listCurrencies(){
        return ResponseEntity.ok(converterService.listCurrencies());
    }
}
