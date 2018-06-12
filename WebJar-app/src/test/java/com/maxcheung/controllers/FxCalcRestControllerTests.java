package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.maxcheung.config.TestConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfig.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = { DependencyInjectionTestExecutionListener.class,
		TransactionDbUnitTestExecutionListener.class, })
@DbUnitConfiguration(databaseConnection = "dbUnitWebJarConnection")
@DatabaseSetup(connection = "dbUnitWebJarConnection", value = "/dbunit/fxcalc/fxcalc-simple.xml", type = DatabaseOperation.CLEAN_INSERT)
@DatabaseTearDown(connection = "dbUnitWebJarConnection", value = "/dbunit/fxcalc/fxcalc-simple.xml", type = DatabaseOperation.DELETE_ALL)
public class FxCalcRestControllerTests {

	private static final String FXCALC_BASE_ENDPOINT = "http://localhost:{port}/fxcalc";
	private static final String FXCALC_GET_SELL_AMOUNT = FXCALC_BASE_ENDPOINT
			+ "/calc-sell-amount?buyAmount={buyAmount}&fxrate={fxrate}";

	@Value("${local.server.port}")
	private int port;

	private TestRestTemplate template = new TestRestTemplate("user", "test");

	private ParameterizedTypeReference<BigDecimal> sellAmountReference = new ParameterizedTypeReference<BigDecimal>() {

	};

	@Before
	public void setUp() {

	}

	@Test
	public void testGetSellAmount() {
		ResponseEntity<BigDecimal> sellAmount = template.exchange(FXCALC_GET_SELL_AMOUNT, HttpMethod.GET, null,
				sellAmountReference, port, new BigDecimal(100), new BigDecimal(0.075));
		Assertions.assertThat(sellAmount.getBody()).isEqualTo(new BigDecimal(7.5).setScale(2, RoundingMode.HALF_EVEN));
	}
}
