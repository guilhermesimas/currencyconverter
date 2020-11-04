package com.cortex.currencyconverter.integration;

import com.cortex.currencyconverter.contracts.ConversionResultTO;
import com.cortex.currencyconverter.contracts.HttpErrorTO;
import com.cortex.currencyconverter.entities.CacheableConversion;
import com.cortex.currencyconverter.exceptions.InvalidCurrency;
import com.cortex.currencyconverter.facades.ConverterFacade;
import com.netflix.ribbon.proxy.annotation.Http;
import feign.FeignException;
import feign.Request;
import io.micrometer.core.ipc.http.HttpSender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.format.DateTimeParseException;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ConverterControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    ConverterFacade converterFacade;

    @Test
    void test_whenFacadeReturnsCurrencies_ok(){
        final HashMap<String, Integer> fixtureList = new HashMap<>();
        fixtureList.put("ABC", 1);
        when(converterFacade.listCurrencies()).thenReturn(fixtureList);

        ResponseEntity<HashMap> response = testRestTemplate.getForEntity("/currencies", HashMap.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().size(),1);
    }

    @Test
    void test_whenFacadeReturnsConversion_ok(){
        final Double fixtureAmount = 1d;
        when(converterFacade.convert(anyDouble(), anyString(), anyString(), anyString()))
                .thenReturn(CacheableConversion.of(fixtureAmount));

        ResponseEntity<ConversionResultTO> response = testRestTemplate.getForEntity("/convert/1/from/ABC/to/ABC/on" +
                "/when", ConversionResultTO.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(response.getBody().getAmount(),fixtureAmount);
    }

    @Test
    void test_whenFacadeThrowsDateParseException_badRequest(){
        when(converterFacade.convert(anyDouble(), anyString(), anyString(), anyString()))
                .thenThrow(new DateTimeParseException("", "message", 1));

        ResponseEntity<String> response = testRestTemplate.getForEntity("/convert/1/from/ABC/to/ABC/on" +
                "/when", String.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_whenFacadeThrowsInvalidCurrency_notFound(){
        when(converterFacade.convert(anyDouble(), anyString(), anyString(), anyString()))
                .thenThrow(new InvalidCurrency(""));

        ResponseEntity<String> response = testRestTemplate.getForEntity("/convert/1/from/ABC/to/ABC/on" +
                "/when", String.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void test_whenFacadeThrowsFeignException_gatewayTimeout(){
        FeignException ex = new FeignException.InternalServerError("", Request.create(Request.HttpMethod.GET, "",
                new HashMap<>(), (byte[]) null, null),null);
        when(converterFacade.convert(anyDouble(), anyString(), anyString(), anyString()))
                .thenThrow(ex);

        ResponseEntity<String> response = testRestTemplate.getForEntity("/convert/1/from/ABC/to/ABC/on" +
                "/when", String.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.GATEWAY_TIMEOUT);
    }
}
