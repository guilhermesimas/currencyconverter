package com.cortex.currencyconverter.clients.bacen.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "moeda")
public class CurrencyTO {

    @JacksonXmlProperty(localName = "sigla")
    private String initials;

    @JacksonXmlProperty(localName = "codigo")
    private Integer code;
}
