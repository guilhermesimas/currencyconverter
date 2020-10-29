package com.cortex.currencyconverter.services;

import com.cortex.currencyconverter.clients.bacen.BacenClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConverterService {
    private final BacenClient bacenClient;
    public String convert(){
        return bacenClient.convert(100.0f, 20, 220, "10-20-2020").getBody();
    }
}
