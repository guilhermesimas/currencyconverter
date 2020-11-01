package com.cortex.currencyconverter.clients.bacen;

import com.cortex.currencyconverter.clients.bacen.contracts.ConversionTO;
import com.cortex.currencyconverter.clients.bacen.contracts.ListCurrencyTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bacen", configuration = BacenClientConfig.class)
public interface BacenClient {

    @GetMapping(value="/bc_moeda/rest/moeda/data")
    ResponseEntity<ListCurrencyTO> listCurrencies();

    @GetMapping(value="/bc_moeda/rest/converter/{amount}/1/{from}/{to}/{when}")
    ResponseEntity<ConversionTO> convert(@PathVariable("amount") Double amount,
                                         @PathVariable("from") Integer from,
                                         @PathVariable( "to") Integer to,
                                         @PathVariable("when") String when);
}
