package com.cortex.currencyconverter.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel("Erro no processamento da requisição.")
@Data
@JsonSerialize
@JsonDeserialize
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
