package com.maxcheung.models;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Table;

public class ReportSection  {

	private Table<String, String, CellValue> table;

	private  Map<String, CellValue> rowTotals;
	
	private Map<String, CellValue> columnTotals;
	
	private CellValue grandTotal;

	public Table<String, String, CellValue> getTable() {
		return table;
	}

	public void setTable(Table<String, String, CellValue> table) {
		this.table = table;
	}

	public Map<String, CellValue> getRowTotals() {
		return rowTotals;
	}

	public void setRowTotals(Map<String, CellValue> rowTotals) {
		this.rowTotals = rowTotals;
	}

	public Map<String, CellValue> getColumnTotals() {
		return columnTotals;
	}

	public void setColumnTotals(Map<String, CellValue> columnTotals) {
		this.columnTotals = columnTotals;
	}
	
	
	public Set<String> getRowHeaders() {		
		return table.rowKeySet();
	}

	public Set<String> getColumnHeaders() {		
		return table.columnKeySet();
	}

	public CellValue getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(CellValue grandTotal) {
		this.grandTotal = grandTotal;
	}
	
}
