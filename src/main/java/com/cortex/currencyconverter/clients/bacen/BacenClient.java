package com.cortex.currencyconverter.clients.bacen;

import com.cortex.currencyconverter.clients.bacen.contracts.ConversionTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bacen")
public interface BacenClient {

    @GetMapping(value="/bc_moeda/rest/moeda/data")
    ResponseEntity<String> getCurrency();

    @GetMapping(value="/bc_moeda/rest/converter/{amount}/1/{from}/{to}/{when}")
    ResponseEntity<ConversionTO> convert(@PathVariable("amount") Float amount,
                                         @PathVariable("from") Integer from,
                                         @PathVariable( "to") Integer to,
                                         @PathVariable("when") String when);
}
