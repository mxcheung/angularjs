package com.maxcheung.models;

import java.util.Map;
//https://poi.apache.org/apidocs/org/apache/poi/ss/usermodel/Name.html
public class DataTable extends AbsDataTable {
	
	
	private String title;

	private Map<String, CellValue> rowTotals;

	private Map<String, CellValue> columnTotals;

	private CellValue grandTotal;

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

	public CellValue getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(CellValue grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
}
