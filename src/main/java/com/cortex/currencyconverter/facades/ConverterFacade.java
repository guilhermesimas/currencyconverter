package com.cortex.currencyconverter.facades;

import com.cortex.currencyconverter.contracts.ConversionResultTO;
import com.cortex.currencyconverter.entities.CacheableConversion;
import com.cortex.currencyconverter.services.ConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class ConverterFacade {

    private final ConverterService converterService;

    public CacheableConversion convert(Double amount, String from, String to, String when) {
        final LocalDate dateStr = LocalDate.parse(when);
        CacheableConversion cacheableConversion = converterService.convert(amount, from, to, dateStr);
        if (cacheableConversion.getFromCache() == false) {
            converterService.setResultAsCached(amount, from, to, dateStr, cacheableConversion);
        }
        return cacheableConversion;
    }

    public HashMap<String, Integer> listCurrencies() {
        return converterService.listCurrencies();
    }
}
