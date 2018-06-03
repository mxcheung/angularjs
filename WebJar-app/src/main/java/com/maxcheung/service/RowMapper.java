package com.maxcheung.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class RowMapper {

	public RowMapper() {
		super();
	}


	public Object getCellValue(Cell cell) {
		if (cell != null) {
			return getCellValue(cell,cell.getCellTypeEnum());
		}
		return null;
    }

	public Object getCellValue(Cell cell, CellType cellType) {
		Object val = null;
		if (cell != null) {
			switch (cellType) {
			case BOOLEAN:
				val = cell.getBooleanCellValue();
				break;
			case NUMERIC:
				val = cell.getNumericCellValue();
				break;
			case STRING:
				val = cell.getStringCellValue();
				break;
			case BLANK:
				break;
			case ERROR:
				val = cell.getErrorCellValue();
				break;
			// CELL_TYPE_FORMULA will never occur
			case FORMULA:
				break;
			default:
				break;
			}
		}

		return val;
	}

	public boolean isRowEmpty(Row row) {
		for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
			Cell cell = row.getCell(c);
			if (cell != null && cell.getCellTypeEnum() != CellType.BLANK) {
				return false;
			}
		}
		return true;
	}

}