package com.cortex.currencyconverter.controllers.advice;

import com.cortex.currencyconverter.contracts.HttpErrorTO;
import com.cortex.currencyconverter.controllers.CurrencyController;
import com.cortex.currencyconverter.exceptions.InvalidCurrency;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.format.DateTimeParseException;

@ControllerAdvice(basePackageClasses = {CurrencyController.class})
public class ExceptionHandlingAdvice {

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    private HttpErrorTO persistenceExceptionHandler(DateTimeParseException ex) {
        return new HttpErrorTO("Invalid datetime format.");
    }

    @ExceptionHandler(InvalidCurrency.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    private HttpErrorTO invalidCurrencyHandler(InvalidCurrency ex) {
        return new HttpErrorTO(ex.getMessage());
    }


    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    @ResponseBody
    private HttpErrorTO clientExceptionHandler(FeignException ex) {
       return new HttpErrorTO("Error in service request. Please try again later.");
    }
}
