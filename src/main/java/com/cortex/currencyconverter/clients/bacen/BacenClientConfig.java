package com.cortex.currencyconverter.clients.bacen;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BacenClientConfig {

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new JacksonDecoder(xmlMapper()));
    }

    public static XmlMapper xmlMapper(){
        return new XmlMapper();
    }
}
