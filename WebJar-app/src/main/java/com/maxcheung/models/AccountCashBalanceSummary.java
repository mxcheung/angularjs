package com.maxcheung.models;

import java.util.Map;


public class AccountCashBalanceSummary {

	private Map<String, ReportTable> reports;
	

	public Map<String, ReportTable> getReports() {
		return reports;
	}

	public void setReports(Map<String, ReportTable> reports) {
		this.reports = reports;
	}


}