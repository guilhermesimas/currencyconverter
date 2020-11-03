package com.cortex.currencyconverter.config;

import com.cortex.currencyconverter.services.keygenerators.ConversionKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    ConversionKeyGenerator conversionKeyGenerator(){
        return new ConversionKeyGenerator();
    }
}
