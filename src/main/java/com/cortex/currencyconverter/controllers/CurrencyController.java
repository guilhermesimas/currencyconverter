package com.cortex.currencyconverter.controllers;

import com.cortex.currencyconverter.contracts.ConversionResultTO;
import com.cortex.currencyconverter.facades.ConverterFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final ConverterFacade converterFacade;

    @ApiOperation(value = "Consultar conversão de moeda em data específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna valor da conversão e origem."),
            @ApiResponse(responseCode = "400", description = "Dados incorretos de requisição."),
            @ApiResponse(responseCode = "404", description = "Moeda requisitada não se encontra disponível."),
            @ApiResponse(responseCode = "504", description = "Dependência externa indisponível."),
            @ApiResponse(responseCode = "500", description = "Erro desconhecido na aplicação.")
    })
    @GetMapping("convert/{amount}/from/{from}/to/{to}/on/{when}")
    public ResponseEntity<ConversionResultTO> convertCurrency(
            @ApiParam("Valor a ser convertido.") @PathVariable("amount") Double amount,
            @ApiParam("Moeda de origem.") @PathVariable("from") String from,
            @ApiParam("Moeda de destino.") @PathVariable("to") String to,
            @ApiParam("Data de referência para conversão. Deve ser no formato YYYY-MM-DD")
            @PathVariable("when") String when){
        return ResponseEntity.ok(ConversionResultTO.of(converterFacade.convert(amount, from, to, when)));
    }

    @ApiOperation(value = "Consultar moedas disponíveis para conversão.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retorna lista de moedas disponíveis."),
            @ApiResponse(responseCode = "504", description = "Dependência externa indisponível."),
            @ApiResponse(responseCode = "500", description = "Erro desconhecido na aplicação.")
    })
    @GetMapping("currencies")
    public ResponseEntity<HashMap<String, Integer>> listCurrencies(){
        return ResponseEntity.ok(converterFacade.listCurrencies());
    }
}
