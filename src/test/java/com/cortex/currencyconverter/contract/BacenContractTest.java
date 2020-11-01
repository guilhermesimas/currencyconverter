package com.cortex.currencyconverter.contract;

import com.cortex.currencyconverter.clients.bacen.BacenClientConfig;
import com.cortex.currencyconverter.clients.bacen.contracts.ConversionTO;
import com.cortex.currencyconverter.clients.bacen.contracts.CurrencyTO;
import com.cortex.currencyconverter.clients.bacen.contracts.ListCurrencyTO;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BacenContractTest {

    XmlMapper objectMapper = BacenClientConfig.xmlMapper();

    @Test
    @SneakyThrows
    void test_desserialize_conversion_ok() {
        ConversionTO conversionTO = objectMapper.readValue("" +
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                        "<valor-convertido>154.1405110</valor-convertido>",
                ConversionTO.class);
        Assertions.assertThat(conversionTO.getConvertedValue()).isEqualTo(154.1405110);
    }

    @Test
    @SneakyThrows
    void test_desserialize_currency_ok() {
        CurrencyTO currencyTO = objectMapper.readValue("" +
                        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
                        "",
                CurrencyTO.class);
        Assertions.assertThat(currencyTO).isNotNull();
        Assertions.assertThat(currencyTO.getCode()).isEqualTo(5);
        Assertions.assertThat(currencyTO.getInitials()).isEqualTo("AFN");
    }
    @Test
    @SneakyThrows
    void test_desserialize_currencies_ok() {
        ListCurrencyTO listCurrencyTO = objectMapper.readValue("" +
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
                        "    <moeda>\n" +
                        "        <codigo>406</codigo>\n" +
                        "        <codigoIata></codigoIata>\n" +
                        "        <codigoPais>4502</codigoPais>\n" +
                        "        <codigoSistema>1</codigoSistema>\n" +
                        "        <codigoSwift>MGA</codigoSwift>\n" +
                        "        <codigosSistema>1</codigosSistema>\n" +
                        "        <dataHoraInclusao>2010-06-14T00:00:00.000-0300</dataHoraInclusao>\n" +
                        "        <dataHoraRegistro>2011-09-08T11:33:39.262-03:00</dataHoraRegistro>\n" +
                        "        <genero>M</genero>\n" +
                        "        <moedaConvertidaEuro>false</moedaConvertidaEuro>\n" +
                        "        <nome>ARIARY MADAGASCAR</nome>\n" +
                        "        <nomeFormatado>Ariary</nomeFormatado>\n" +
                        "        <nomePlural></nomePlural>\n" +
                        "        <nomeSingular>ARIARY MADAGASCAR</nomeSingular>\n" +
                        "        <paises>\n" +
                        "            <codigo>4502</codigo>\n" +
                        "        </paises>\n" +
                        "        <sigla>MGA</sigla>\n" +
                        "        <simbolo>MGA</simbolo>\n" +
                        "        <sistemas>SISTEMA_FUTUROS</sistemas>\n" +
                        "        <tipo>A</tipo>\n" +
                        "    </moeda>\n" +
                        "    <moeda>\n" +
                        "        <codigo>20</codigo>\n" +
                        "        <codigoIata>BAL</codigoIata>\n" +
                        "        <codigoPais>5800</codigoPais>\n" +
                        "        <codigoSistema>1</codigoSistema>\n" +
                        "        <codigoSwift>PAB</codigoSwift>\n" +
                        "        <codigosSistema>1</codigosSistema>\n" +
                        "        <dataHoraInclusao>1983-12-26T00:00:00.000-0300</dataHoraInclusao>\n" +
                        "        <dataHoraRegistro>1998-07-30T15:25:00-03:00</dataHoraRegistro>\n" +
                        "        <genero>M</genero>\n" +
                        "        <moedaConvertidaEuro>false</moedaConvertidaEuro>\n" +
                        "        <nome>BALBOA/PANAMA</nome>\n" +
                        "        <nomeFormatado>Balboa</nomeFormatado>\n" +
                        "        <nomeSingular>BALBOA</nomeSingular>\n" +
                        "        <paises>\n" +
                        "            <codigo>5800</codigo>\n" +
                        "        </paises>\n" +
                        "        <sigla>PAB</sigla>\n" +
                        "        <simbolo>PAB</simbolo>\n" +
                        "        <sistemas>SISTEMA_FUTUROS</sistemas>\n" +
                        "        <tipo>A</tipo>\n" +
                        "    </moeda>" +
                        "</moedas>",
                ListCurrencyTO.class);
        Assertions.assertThat(listCurrencyTO).isNotNull();
        Assertions.assertThat(listCurrencyTO.getCurrencies().size()).isEqualTo(3);
        for (CurrencyTO currency : listCurrencyTO.getCurrencies()) {
            Assertions.assertThat(currency).isNotNull();
            Assertions.assertThat(currency.getInitials()).isNotBlank();
            Assertions.assertThat(currency.getCode()).isNotNull();
        }
    }

}
