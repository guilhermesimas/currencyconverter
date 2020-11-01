package com.cortex.currencyconverter.clients.bacen;

import com.cortex.currencyconverter.clients.bacen.contracts.ConversionTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Profile({"!production"})
@RestController
@RibbonClient(value = "bacen", configuration = BacenMockClient.RibbonConfig.class)
@RequiredArgsConstructor
public class BacenMockClient {

    private final ObjectMapper objectMapper;

    @Profile({"!production"})
    @Configuration
    protected static class RibbonConfig {

        @Value("${server.port}")
        int port;

        @Bean
        public ServerList<Server> serverList() {
            log.info("Initializing static server list");
            return new StaticServerList<>(new Server("localhost", port));
        }

    }

    @GetMapping(value="/bc_moeda/rest/converter/{amount}/1/{from}/{to}/{when}")
    @SneakyThrows
    ResponseEntity<ConversionTO> convert(@PathVariable("amount") Float amount,
                                         @PathVariable("from") Integer from,
                                         @PathVariable( "to") Integer to,
                                         @PathVariable("when") String when){
        return ResponseEntity.ok(objectMapper.readValue("" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ConversionTO>" +
                "<valor-convertido>154.1405110</valor-convertido>" +
                "</ConversionTO>", ConversionTO.class));
    }

    @GetMapping(value="/bc_moeda/rest/moeda/data")
    ResponseEntity<String> getCurrency() {
       return ResponseEntity.ok("" +
               "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
               "<moedas>\n" +
               "    <moeda>\n" +
               "        <codigo>5</codigo>\n" +
               "        <codigoIata>AFG</codigoIata>\n" +
               "        <codigoPais>132</codigoPais>\n" +
               "        <codigoSistema>1</codigoSistema>\n" +
               "        <codigoSwift>AFN</codigoSwift>\n" +
               "        <codigosSistema>1</codigosSistema>\n" +
               "        <dataHoraInclusao>1984-12-07T00:00:00.000-0300</dataHoraInclusao>\n" +
               "        <dataHoraRegistro>2011-09-09T14:55:47.746-03:00</dataHoraRegistro>\n" +
               "        <genero>M</genero>\n" +
               "        <moedaConvertidaEuro>false</moedaConvertidaEuro>\n" +
               "        <nome>AFEGANE AFEGANIST</nome>\n" +
               "        <nomeFormatado>Afegane</nomeFormatado>\n" +
               "        <nomePlural></nomePlural>\n" +
               "        <nomeSingular>AFEGANE</nomeSingular>\n" +
               "        <paises>\n" +
               "            <codigo>132</codigo>\n" +
               "        </paises>\n" +
               "        <sigla>AFN</sigla>\n" +
               "        <simbolo>AFN</simbolo>\n" +
               "        <sistemas>SISTEMA_FUTUROS</sistemas>\n" +
               "        <tipo>A</tipo>\n" +
               "    </moeda>\n" +
               "</moedas>");
    }
}
