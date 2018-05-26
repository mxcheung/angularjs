package com.maxcheung.models;

import java.util.Map;
import java.util.Set;

public class ReportSummary {
	
	private Set<String> headers;
	
	private Map<String, DataTable> sections;


	public Set<String> getHeaders() {
		return headers;
	}


	public void setHeaders(Set<String> headers) {
		this.headers = headers;
	}


	public Map<String, DataTable> getSections() {
		return sections;
	}


	public void setSections(Map<String, DataTable> sections) {
		this.sections = sections;
	}



}