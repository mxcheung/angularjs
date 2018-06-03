package com.maxcheung.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.ReportSmartRCVXTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReportControllerTest {

	@LocalServerPort
	private int port;

	protected TestRestTemplate template;
	private RestTemplate restTemplate;
	private ParameterizedTypeReference<ReportSmartRCVXTemplate> reportSmartRCVXTemplateTypeReference = new ParameterizedTypeReference<ReportSmartRCVXTemplate>() {
	};

	@Before
	public void setUp() {
	//	ObjectMapper mapper = getMapper();

		// Create a list for the message converters

		// Create a Rest template
	//	restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		// Add the Jackson Message converter
	//	messageConverters.add(new MappingJackson2HttpMessageConverter(mapper));

		// Add the message converters to the restTemplate
//		restTemplate.setMessageConverters(messageConverters);
		
		
//		template = new TestRestTemplate(restTemplate, "user", "test");
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
	public void shouldLogValidationErrors() throws Exception {
		String RCVX_ENDPOINT = "http://localhost:" + port + "/report/SmartTableRCVX";
		ResponseEntity<ReportSmartRCVXTemplate> response = template.exchange(RCVX_ENDPOINT, HttpMethod.GET, null,
				reportSmartRCVXTemplateTypeReference);
		ReportSmartRCVXTemplate reportSmartRCVXTemplate = response.getBody();
		reportSmartRCVXTemplate = response.getBody();
		DataTable dataTable = reportSmartRCVXTemplate.getDataTable();
		CellValue cellValue = dataTable.getTable().get("id1", "Name");
		assertEquals("James", cellValue.getStringCellValue());
	}

	@Test
	public void shouldSerialise() throws Exception {
		DataTable dataTable = new DataTable();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(dataTable);
	}

}
