package com.cortex.currencyconverter.integration;

import com.cortex.currencyconverter.entities.CacheableConversion;
import com.cortex.currencyconverter.exceptions.InvalidCurrency;
import com.cortex.currencyconverter.services.ConverterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashMap;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@DirtiesContext
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class ConverterServiceTest {

    @Autowired
    ConverterService converterService;

    @Test
    void test_whenListCurrencies_receivesBRLandUSD(){
        HashMap<String, Integer> currencies = converterService.listCurrencies();

        Assertions.assertNotNull(currencies);
        Assertions.assertEquals(currencies.size(), 2);

        Assertions.assertTrue(currencies.containsKey("BRL"));
        Assertions.assertTrue(currencies.containsKey("USD"));
    }

    @Test
    void test_whenCurrencyNotInList_throwsInvalidCurrencyException() {
        Assertions.assertThrows(InvalidCurrency.class, () -> converterService.convert(1d, "ABC", "USD",
                LocalDate.of(1995, 6, 29)));
        Assertions.assertThrows(InvalidCurrency.class, () -> converterService.convert(1d, "USD", "ABC",
                LocalDate.of(1995, 6, 29)));
    }

    @Test
    void test_whenConvertingBRLtoUSDonValidDate_receivesAmount(){
        CacheableConversion cacheableConversion = converterService.convert(100d, "BRL", "USD", LocalDate.of(2020, 10
                , 20));

        Assertions.assertNotNull(cacheableConversion);
        Assertions.assertNotNull(cacheableConversion.getAmount());
        Assertions.assertFalse(cacheableConversion.getFromCache());
    }

    @Test
    void test_whenSettingConversionAsCachedThenConverting_convertedIsCached(){
        final Double fixtureAmount = 123.45;
        final String fixtureFrom = "ABC";
        final String fixtureTo = "DFE";
        final LocalDate fixtureWhen = LocalDate.of(1995,6,29);

        final CacheableConversion cacheableConversion = CacheableConversion.builder()
                .amount(fixtureAmount)
                .fromCache(false)
                .build();

        converterService.setResultAsCached(fixtureAmount, fixtureFrom, fixtureTo,
                fixtureWhen, cacheableConversion);
        final CacheableConversion cached = converterService.convert(fixtureAmount, fixtureFrom, fixtureTo, fixtureWhen);

        Assertions.assertTrue(cached.getFromCache());
        Assertions.assertEquals(cached.getAmount(), fixtureAmount);
    }
}
