package com.cortex.currencyconverter.contracts;

import com.cortex.currencyconverter.entities.CacheableConversion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("Resultado de conversão.")
public class ConversionResultTO {

    @ApiModelProperty("Valor monetário resultado da conversão.")
    private Double amount;

    @ApiModelProperty("Se o dado é originado da cache.")
    private Boolean fromCache;

    public static ConversionResultTO of(CacheableConversion cacheableConversion){
        return builder()
                .amount(cacheableConversion.getAmount())
                .fromCache(cacheableConversion.getFromCache())
                .build();
    }
}
