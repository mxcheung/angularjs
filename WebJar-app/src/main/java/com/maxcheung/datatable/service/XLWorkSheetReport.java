package com.maxcheung.datatable.service;

import java.util.Map;

import com.maxcheung.models.DataTable;

public class XLWorkSheetReport {
	
	private String sheetName;
	private Map<String, DataTable> sections;

	public Map<String, DataTable> getSections() {
		return sections;
	}

	public void setSections(Map<String, DataTable> sections) {
		this.sections = sections;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	





}