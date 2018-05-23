package com.maxcheung.models;

import java.util.Map;
import java.util.Set;

public class ReportTable {
	
	private Set<String> headers;
	
	private Map<String, ReportSection> sections;


	public Set<String> getHeaders() {
		return headers;
	}


	public void setHeaders(Set<String> headers) {
		this.headers = headers;
	}


	public Map<String, ReportSection> getSections() {
		return sections;
	}


	public void setSections(Map<String, ReportSection> sections) {
		this.sections = sections;
	}



}