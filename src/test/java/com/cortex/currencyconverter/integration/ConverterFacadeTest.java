package com.cortex.currencyconverter.integration;

import com.cortex.currencyconverter.entities.CacheableConversion;
import com.cortex.currencyconverter.facades.ConverterFacade;
import com.cortex.currencyconverter.services.ConverterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sun.security.util.Cache;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ConverterFacadeTest {

    @Autowired
    ConverterFacade converterFacade;

    @MockBean
    ConverterService converterService;

    @Test
    void test_whenInvalidDateFormat_throwsParseException(){
        final Double fixtureAmount = 123.45;
        final String fixtureFrom = "ABC";
        final String fixtureTo = "DFE";
        final String fixtureInvalidDate = "1234-13-01";

        Assertions.assertThrows(DateTimeParseException.class,
                () -> converterFacade.convert(fixtureAmount,
                fixtureFrom, fixtureTo, fixtureInvalidDate));
    }

    @Test
    void test_whenServiceReturnsList_returnsList(){
        final HashMap<String, Integer> fixtureList = new HashMap<>();
        fixtureList.put("ABC", 1);
        when(converterService.listCurrencies()).thenReturn(fixtureList);

        HashMap<String, Integer> currencies = converterFacade.listCurrencies();

        Assertions.assertEquals(currencies.size(), 1);
        Assertions.assertEquals(currencies.get("ABC"), 1);
        verify(converterService, times(1)).listCurrencies();
    }

    @Test
    void test_whenServiceReturnsConversion_setAsCached(){
        final Double fixtureAmount = 123.45;
        final String fixtureFrom = "ABC";
        final String fixtureTo = "DFE";
        final String fixtureWhen = "1995-06-29";

        final CacheableConversion cacheableConversion = CacheableConversion.of(fixtureAmount);
        when(converterService.convert(anyDouble(), anyString(), anyString(), any(LocalDate.class)))
               .thenReturn(cacheableConversion);
        when(converterService.setResultAsCached(anyDouble(), anyString(), anyString(), any(LocalDate.class),
               any(CacheableConversion.class))).thenReturn(cacheableConversion);

        final CacheableConversion returned = converterFacade.convert(fixtureAmount, fixtureFrom, fixtureTo, fixtureWhen);

        Assertions.assertSame(cacheableConversion, returned);
        verify(converterService, times(1)).convert(anyDouble(), anyString(), anyString(), any(LocalDate.class));
        verify(converterService, times(1)).setResultAsCached(anyDouble(), anyString(), anyString(),
                any(LocalDate.class), any(CacheableConversion.class));
    }
}
