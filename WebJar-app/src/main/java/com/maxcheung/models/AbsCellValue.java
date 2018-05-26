package com.maxcheung.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbsCellValue implements CellValue {

	@JsonIgnore
	public String rowKey;
	@JsonIgnore
	public String columnKey;

	@JsonIgnore
	public BigDecimal value = BigDecimal.ZERO;

	@Override
	public String getRowKey() {
		return rowKey;
	}

	@Override
	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	@Override
	public String getColumnKey() {
		return columnKey;
	}

	@Override
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	@Override
	public BigDecimal getValue() {
		return value;
	}

	@Override
	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	@JsonIgnore
	public abstract CellType getCellType();

}