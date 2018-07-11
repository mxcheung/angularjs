package com.maxcheung.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.ReportSmartRCVXTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TableRestControllerTest {

	private static final Logger LOG = LoggerFactory.getLogger(TableRestControllerTest.class);

	@LocalServerPort
	private int port;

	private static final String BASE_ENDPOINT = "http://localhost:";
	private static final String TABLE_TABLE_CSV = "/table/tableCSV";

	protected TestRestTemplate template;
	private RestTemplate restTemplate;
	private ParameterizedTypeReference<ReportSmartRCVXTemplate> reportSmartRCVXTemplateTypeReference = new ParameterizedTypeReference<ReportSmartRCVXTemplate>() {
	};
	private ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<Void>() {
	};

	@Before
	public void setUp() {
		// ObjectMapper mapper = getMapper();

		// Create a list for the message converters

		// Create a Rest template
		// restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		// Add the Jackson Message converter
		// messageConverters.add(new MappingJackson2HttpMessageConverter(mapper));

		// Add the message converters to the restTemplate
		// restTemplate.setMessageConverters(messageConverters);

		// template = new TestRestTemplate(restTemplate, "user", "test");
		template = new TestRestTemplate("user", "test");
		System.out.println("Invoked before each test method");
	}

	private ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		mapper.registerModule(new GuavaModule());
		return mapper;
	}

	@Test
	public void shouldGetTableCSV() throws Exception {
		String TABLE_ENDPOINT = BASE_ENDPOINT + port + TABLE_TABLE_CSV;
		// ResponseEntity<Void> response = template.exchange(TABLE_ENDPOINT,
		// HttpMethod.GET, null,
		// voidTypeReference);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(
				new MediaType("text", "csv")
				 ));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		// ResponseEntity<Void> response
		// = template.getForEntity(TABLE_ENDPOINT, Void.class);

		LOG.info("template exchange ...");

		ResponseEntity<Void> response = template.exchange(TABLE_ENDPOINT, HttpMethod.GET, entity, Void.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void shouldSerialise() throws Exception {
		DataTable dataTable = new DataTable();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(dataTable);
	}

}
