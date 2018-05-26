package com.maxcheung.models;

import java.util.Map;


public class AccountCashBalanceSummary {

	private Map<String, ReportSummary> reports;
	

	public Map<String, ReportSummary> getReports() {
		return reports;
	}

	public void setReports(Map<String, ReportSummary> reports) {
		this.reports = reports;
	}


}