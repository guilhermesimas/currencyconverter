package com.cortex.currencyconverter.clients.bacen;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@RibbonClient(value = "bacen", configuration = {BacenMockClient.RibbonConfig.class})
@RequiredArgsConstructor
public class BacenMockClient {

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
    ResponseEntity<String> convert(@PathVariable("amount") Float amount,
                                         @PathVariable("from") Integer from,
                                         @PathVariable( "to") Integer to,
                                         @PathVariable("when") String when){
        return ResponseEntity.ok("" +
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<valor-convertido>154.1405110</valor-convertido>" +
                "");
    }

    @GetMapping(value="/bc_moeda/rest/moeda/data")
    ResponseEntity<String> listCurrencies() {
       return ResponseEntity.ok("" +
               "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
               "<moedas>\n" +
               "<moeda>\n" +
               "        <codigo>220</codigo>\n" +
               "        <codigoIata>USD</codigoIata>\n" +
               "        <codigoPais>0</codigoPais>\n" +
               "        <codigoSistema>1</codigoSistema>\n" +
               "        <codigoSwift>USD</codigoSwift>\n" +
               "        <codigosSistema>1</codigosSistema>\n" +
               "        <codigosSistema>2</codigosSistema>\n" +
               "        <codigosSistema>3</codigosSistema>\n" +
               "        <codigosSistema>4</codigosSistema>\n" +
               "        <dataHoraInclusao>1983-12-26T00:00:00.000-0300</dataHoraInclusao>\n" +
               "        <dataHoraRegistro>2016-11-21T15:45:56.251-02:00</dataHoraRegistro>\n" +
               "        <genero>M</genero>\n" +
               "        <moedaConvertidaEuro>false</moedaConvertidaEuro>\n" +
               "        <nome>DOLAR DOS EUA</nome>\n" +
               "        <nomeFormatado>Dólar dos Estados Unidos</nomeFormatado>\n" +
               "        <nomePlural>DOLARES DOS ESTADOS UNIDOS</nomePlural>\n" +
               "        <nomeSingular>DOLAR DOS ESTADOS UNIDOS</nomeSingular>\n" +
               "        <paises>\n" +
               "            <codigo>6114</codigo>\n" +
               "        </paises>\n" +
               "        <paises>\n" +
               "            <codigo>2496</codigo>\n" +
               "        </paises>\n" +
               "        <paises>\n" +
               "            <codigo>8664</codigo>\n" +
               "        </paises>\n" +
               "        <paises>\n" +
               "            <codigo>4766</codigo>\n" +
               "        </paises>\n" +
               "        <paises>\n" +
               "            <codigo>8630</codigo>\n" +
               "        </paises>\n" +
               "        <sigla>USD</sigla>\n" +
               "        <simbolo>USD</simbolo>\n" +
               "        <sistemas>SISTEMA_FUTUROS</sistemas>\n" +
               "        <sistemas>SISTEMA_CONTRATOS</sistemas>\n" +
               "        <sistemas>SISTEMA_RESERVAS</sistemas>\n" +
               "        <sistemas>SISTEMA_GPT</sistemas>\n" +
               "        <tipo>A</tipo>\n" +
               "    </moeda>" +
               "    <moeda>\n" +
               "        <codigo>790</codigo>\n" +
               "        <codigoIata></codigoIata>\n" +
               "        <codigoPais>1058</codigoPais>\n" +
               "        <codigoSistema>1</codigoSistema>\n" +
               "        <codigoSwift>BRL</codigoSwift>\n" +
               "        <codigosSistema>1</codigosSistema>\n" +
               "        <codigosSistema>2</codigosSistema>\n" +
               "        <codigosSistema>4</codigosSistema>\n" +
               "        <dataHoraInclusao>1994-07-01T00:00:00.000-0300</dataHoraInclusao>\n" +
               "        <dataHoraRegistro>2011-09-09T15:12:39.316-03:00</dataHoraRegistro>\n" +
               "        <genero>M</genero>\n" +
               "        <moedaConvertidaEuro>false</moedaConvertidaEuro>\n" +
               "        <nome>REAL BRASIL</nome>\n" +
               "        <nomeFormatado>Real</nomeFormatado>\n" +
               "        <nomePlural></nomePlural>\n" +
               "        <nomeSingular>REAL</nomeSingular>\n" +
               "        <paises>\n" +
               "            <codigo>1058</codigo>\n" +
               "        </paises>\n" +
               "        <sigla>BRL</sigla>\n" +
               "        <simbolo>BRL</simbolo>\n" +
               "        <sistemas>SISTEMA_FUTUROS</sistemas>\n" +
               "        <sistemas>SISTEMA_CONTRATOS</sistemas>\n" +
               "        <sistemas>SISTEMA_GPT</sistemas>\n" +
               "        <tipo>A</tipo>\n" +
               "    </moeda>\n" +
               "</moedas>");
    }
}
