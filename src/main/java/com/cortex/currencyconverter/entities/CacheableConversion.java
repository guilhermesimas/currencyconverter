package com.cortex.currencyconverter.entities;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
public class CacheableConversion implements Serializable {
    private Double amount;

    @Builder.Default
    private Boolean fromCache = false;

    public static CacheableConversion of(Double amount){
        return builder().amount(amount).build();
    }
}
