package com.cortex.currencyconverter.clients.bacen;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("bacen")
public interface BacenClient {
    @GetMapping(value="converter/{amount}/1/{from}/{to}/{when}")
    ResponseEntity<String> convert(@PathVariable("amount") Float amount,
                                   @PathVariable("from") Integer from,
                                   @PathVariable( "to") Integer to,
                                   @PathVariable("when") String when);
}
