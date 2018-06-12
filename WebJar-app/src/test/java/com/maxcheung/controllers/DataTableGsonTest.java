package com.maxcheung.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.google.gson.Gson;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.DataTable;

public class DataTableGsonTest {

	
	@Before
	public void setUp() {
	//	ObjectMapper mapper = getMapper();
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
	public void shouldSerialise() throws Exception {
		Gson gson = new Gson();
		DataTable dataTable = new DataTable();
		Table<String, String, CellValue> table = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		CellValue cellValue = new CellValueDefault();
		cellValue.setRowKey("rowKey");
		cellValue.setColumnKey("columnKey");
		cellValue.setCellValue(BigDecimal.TEN);
		
	//	HashBasedTable table = HashBasedTable.create();
		
		table.put(cellValue.getRowKey(), cellValue.getColumnKey(), cellValue);
		dataTable.setTable(table);
	//	dataTable.setRowTotals(table.rowMap().get("rowKey"));
	//	dataTable.setColumnTotals(table.rowMap().get("rowKey"));
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());
		String json = mapper.writeValueAsString(dataTable);
		json = mapper.writeValueAsString(dataTable);
//		DataTable dataTable2 = mapper.readValue(json, DataTable.class);
//		DataTable dataTable2 = gson.fromJson(json, DataTable.class);
	//	dataTable2 = mapper.readValue(json, DataTable.class);
 
	}

	@Test
	public void guava_table_example () throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());
	    Random r = new Random(3000);

	    Table<Integer, String, Workout> table = HashBasedTable.create();
	    table.put(1, "Filthy 50", new Workout(r.nextLong()));
	    table.put(1, "Fran", new Workout(r.nextLong()));
	    table.put(1, "The Seven", new Workout(r.nextLong()));
	    table.put(1, "Murph", new Workout(r.nextLong()));
	    table.put(1, "The Ryan", new Workout(r.nextLong()));
	    table.put(1, "King Kong", new Workout(r.nextLong()));

	    table.put(2, "Filthy 50", new Workout(r.nextLong()));
	    table.put(2, "Fran", new Workout(r.nextLong()));
	    table.put(2, "The Seven", new Workout(r.nextLong()));
	    table.put(2, "Murph", new Workout(r.nextLong()));
	    table.put(2, "The Ryan", new Workout(r.nextLong()));
	    table.put(2, "King Kong", new Workout(r.nextLong()));

	    String json = mapper.writeValueAsString(table);
	    json = mapper.writeValueAsString(table);
	    Map<String, Map<Integer, Workout>> x = table.columnMap();
        Map<Integer, Map<String, Workout>> y = table.rowMap();
        json = mapper.writeValueAsString(y);
        
        TypeReference<HashMap<Integer, HashMap<String, Workout>>> typeRef 
        = new TypeReference<HashMap<Integer, HashMap<String, Workout>>>() {};
        
 //       Map<String, Map<Integer, Workout>> map = mapper.readValue(json, typeRef);
        
	//    ImmutableTable dataTable2 = mapper.readValue(json, ImmutableTable.class);
	//    json = mapper.writeValueAsString(table);
	    
	    // for each row key
	    for (Integer key : table.rowKeySet()) {

//	        logger.info("Person: " + key);

	        for (Entry<String, Workout> row : table.row(key).entrySet()) {
	//            logger.info("Workout name: " + row.getKey() + " for elapsed time of " + row.getValue().getElapsedTime());
	        }
	    }
	}
	

	@Test
	public void mapguava_table_example () throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());
		String jsonInput = "{\"key\": \"value\"}";
		TypeReference<HashMap<String, String>> typeRef 
		  = new TypeReference<HashMap<String, String>>() {};
		Map<String, String> map = mapper.readValue(jsonInput, typeRef);
		
	}
}
