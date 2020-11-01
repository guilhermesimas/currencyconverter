package com.cortex.currencyconverter.services;

import com.cortex.currencyconverter.clients.bacen.BacenClient;
import com.cortex.currencyconverter.clients.bacen.contracts.ConversionTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConverterService {
    private final BacenClient bacenClient;
    public Double convert(){
        ConversionTO conversionTO = bacenClient.convert(100.0f, 978, 220, "2020-10-20").getBody();
        return conversionTO.getConvertedValue();
    }
}
