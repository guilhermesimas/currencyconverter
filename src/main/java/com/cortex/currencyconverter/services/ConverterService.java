package com.cortex.currencyconverter.services;

import com.cortex.currencyconverter.clients.bacen.BacenClient;
import com.cortex.currencyconverter.clients.bacen.contracts.ConversionTO;
import com.cortex.currencyconverter.clients.bacen.contracts.CurrencyTO;
import com.cortex.currencyconverter.clients.bacen.contracts.ListCurrencyTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConverterService {

    private final BacenClient bacenClient;

    public Double convert(Double amount, String from, String to, LocalDate when){
        Map<String, Integer> currencies = listCurrencies();
        Integer fromCurrencyCode = currencies.get(from);
        Integer toCurrencyCode = currencies.get(to);
        ConversionTO conversionTO = bacenClient.convert(amount, fromCurrencyCode, toCurrencyCode, when.toString()).getBody();
        return conversionTO.getConvertedValue();
    }

    public Map<String, Integer> listCurrencies() {
        ListCurrencyTO listCurrency = bacenClient.listCurrencies().getBody();
        return listCurrency.getCurrencies().stream().collect(Collectors.toMap(CurrencyTO::getInitials,
                CurrencyTO::getCode));

    }
}
