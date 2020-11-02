package com.cortex.currencyconverter.services;

import com.cortex.currencyconverter.clients.bacen.BacenClient;
import com.cortex.currencyconverter.clients.bacen.contracts.ConversionTO;
import com.cortex.currencyconverter.clients.bacen.contracts.CurrencyTO;
import com.cortex.currencyconverter.clients.bacen.contracts.ListCurrencyTO;
import com.cortex.currencyconverter.entities.CacheableConversion;
import com.cortex.currencyconverter.exceptions.InvalidCurrency;
import com.cortex.currencyconverter.services.keygenerators.ConversionKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConverterService {

    private final BacenClient bacenClient;

    @Cacheable(value = "conversion", keyGenerator = "conversionKeyGenerator")
    public CacheableConversion convert(Double amount, String from, String to, LocalDate when){
        CacheableConversion result = CacheableConversion.of(convertExternal(amount, from, to, when));

        return result;
    }

    @CachePut(value = "conversion", keyGenerator = "conversionKeyGenerator")
    public CacheableConversion setResultAsCached(Double amount, String from, String to, LocalDate when,
                                                 CacheableConversion cacheableConversion) {
        return cacheableConversion.toBuilder().fromCache(true).build();
    }

    private Double convertExternal(Double amount, String from, String to, LocalDate when){
        Map<String, Integer> currencies = listCurrencies();
        Integer fromCurrencyCode = currencies.get(from);
        if(Objects.isNull(fromCurrencyCode)){
            throw new InvalidCurrency(from);
        }
        Integer toCurrencyCode = currencies.get(to);
        if(Objects.isNull(toCurrencyCode)){
            throw new InvalidCurrency(to);
        }
        ConversionTO conversionTO = bacenClient.convert(amount, fromCurrencyCode, toCurrencyCode, when.toString()).getBody();
        return conversionTO.getConvertedValue();
    }

    @Cacheable("currencies")
    public HashMap<String, Integer> listCurrencies() {
        ListCurrencyTO listCurrency = bacenClient.listCurrencies().getBody();
        return  listCurrency.getCurrencies().stream().collect(Collectors.toMap(
                CurrencyTO::getInitials,
                CurrencyTO::getCode,
                (o1, o2) -> o1,
                HashMap::new));

    }
}
