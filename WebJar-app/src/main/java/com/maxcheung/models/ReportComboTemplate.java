package com.maxcheung.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

public class ReportComboTemplate {

	
	private List<CellValue> highchartpie;
	
	private List<List<Object>> highchartbar;

	private Set<String> smartchartlabel = Sets.newHashSet("a", "b", "c");

	private List<BigDecimal> smartchartdata = new ArrayList<>();

	public ReportComboTemplate() {
		super();
		smartchartdata.add(new BigDecimal(1));
		smartchartdata.add(new BigDecimal(2));
		smartchartdata.add(new BigDecimal(3));
	}

	public List<CellValue> getHighchartpie() {
		return highchartpie;
	}

	public void setHighchartpie(List<CellValue> highchartpie) {
		this.highchartpie = highchartpie;
	}

	public Set<String> getSmartchartlabel() {
		return smartchartlabel;
	}

	public void setSmartchartlabel(Set<String> smartchartlabel) {
		this.smartchartlabel = smartchartlabel;
	}

	public List<BigDecimal> getSmartchartdata() {
		return smartchartdata;
	}

	public void setSmartchartdata(List<BigDecimal> smartchartdata) {
		this.smartchartdata = smartchartdata;
	}

	public List<List<Object>> getHighchartbar() {
		return highchartbar;
	}

	public void setHighchartbar(List<List<Object>> highchartbar) {
		this.highchartbar = highchartbar;
	}



	

	
	
}