package com.maxcheung.models;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.Table;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "headers", "table", "sales"})
public class ReportSmartRCVTemplate {

	
	private Table<String, String, String> table;
	private Table<String, String, CellValue> sales;
	private Set<String> headers;
	
	public Table<String, String, String> getTable() {
		return table;
	}

	public void setTable(Table<String, String, String> table) {
		headers = table.columnKeySet();
		this.table = table;
	}

	public Set<String> getHeaders() {
		return headers;
	}

	public void setHeaders(Set<String> headers) {
		this.headers = headers;
	}

	public Table<String, String, CellValue> getSales() {
		return sales;
	}

	public void setSales(Table<String, String, CellValue> sales) {
		this.sales = sales;
	}



	

	
	
}