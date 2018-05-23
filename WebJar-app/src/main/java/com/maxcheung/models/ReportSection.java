package com.maxcheung.models;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Table;

public class ReportSection  {

	private Table<String, String, ReportValue> table;

	private  Map<String, ReportValue> rowTotals;
	
	private Map<String, ReportValue> columnTotals;
	
	private ReportValue grandTotal;

	public Table<String, String, ReportValue> getTable() {
		return table;
	}

	public void setTable(Table<String, String, ReportValue> table) {
		this.table = table;
	}

	public Map<String, ReportValue> getRowTotals() {
		return rowTotals;
	}

	public void setRowTotals(Map<String, ReportValue> rowTotals) {
		this.rowTotals = rowTotals;
	}

	public Map<String, ReportValue> getColumnTotals() {
		return columnTotals;
	}

	public void setColumnTotals(Map<String, ReportValue> columnTotals) {
		this.columnTotals = columnTotals;
	}
	
	
	public Set<String> getRowHeaders() {		
		return table.rowKeySet();
	}

	public Set<String> getColumnHeaders() {		
		return table.columnKeySet();
	}

	public ReportValue getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(ReportValue grandTotal) {
		this.grandTotal = grandTotal;
	}
	
}
