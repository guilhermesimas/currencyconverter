package com.cortex.currencyconverter;

import com.cortex.currencyconverter.clients.bacen.contracts.ConversionTO;
import com.cortex.currencyconverter.clients.bacen.contracts.CurrencyTO;
import com.cortex.currencyconverter.clients.bacen.contracts.ListCurrencyTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class CurrencyconverterApplicationTests {

	@Test
	void contextLoads() {
	}
}
