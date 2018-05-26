package com.maxcheung.models;

import java.math.BigDecimal;

public interface CellValue {

	String getRowKey();

	void setRowKey(String rowKey);

	String getColumnKey();

	void setColumnKey(String columnKey);

	BigDecimal getValue();

	void setValue(BigDecimal value);

	CellType getCellType();

}