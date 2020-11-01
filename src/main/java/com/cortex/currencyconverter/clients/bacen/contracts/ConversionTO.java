package com.cortex.currencyconverter.clients.bacen.contracts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

@Data
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(namespace = "valor-convertido")
public class ConversionTO {

    @JacksonXmlText
    private Double convertedValue;
}
