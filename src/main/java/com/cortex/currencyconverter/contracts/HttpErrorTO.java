package com.cortex.currencyconverter.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HttpErrorTO {

    @JsonProperty
    private String error;

    public HttpErrorTO(Throwable t) {
        this(t.getMessage());
    }

    public HttpErrorTO(String errorMessage) {
        this.error = errorMessage;
    }
}
