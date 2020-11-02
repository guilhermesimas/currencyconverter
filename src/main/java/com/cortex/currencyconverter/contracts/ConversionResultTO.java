package com.cortex.currencyconverter.contracts;

import com.cortex.currencyconverter.entities.CacheableConversion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.cache.annotation.Cacheable;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversionResultTO {
    private Double amount;
    private Boolean fromCache;

    public static ConversionResultTO of(CacheableConversion cacheableConversion){
        return builder()
                .amount(cacheableConversion.getAmount())
                .fromCache(cacheableConversion.getFromCache())
                .build();
    }
}
