package com.cortex.currencyconverter.exceptions;

public class InvalidCurrency extends RuntimeException {

    public InvalidCurrency(String initials) {
        super("CacheableConversion " + initials + " is invalid.");
    }
}
