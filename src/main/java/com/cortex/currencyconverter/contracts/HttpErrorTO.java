package com.cortex.currencyconverter.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Erro no processamento da requisição.")
public class HttpErrorTO {

    @JsonProperty
    @ApiModelProperty("Mensagem descritiva de erro.")
    private String error;

    public HttpErrorTO(Throwable t) {
        this(t.getMessage());
    }

    public HttpErrorTO(String errorMessage) {
        this.error = errorMessage;
    }
}
