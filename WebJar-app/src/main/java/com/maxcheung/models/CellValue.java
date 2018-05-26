package com.maxcheung.models;

import java.math.BigDecimal;

/*
 *    see https://google.github.io/guava/releases/23.0/api/docs/com/google/common/collect/Table.Cell.html
 *    Row key / column key / value triplet corresponding to a mapping in a table.
 */
public interface CellValue {

	String getRowKey();

	void setRowKey(String rowKey);

	String getColumnKey();

	void setColumnKey(String columnKey);

	BigDecimal getValue();

	void setValue(BigDecimal value);

	CellType getCellType();

}