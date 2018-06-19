package com.maxcheung.datatable.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Table;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.DataTable;

public class DataTableWriterImpl implements DataTableWriter {

	/*
	 * String fileLocation = path.substring(0, path.length() - 1) +
	 * "templateReport.xlsx"; FileOutputStream outputStream = new
	 * FileOutputStream(fileLocation);
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.maxcheung.datatable.service.DataTableWriter#writeToFile(java.util.List,
	 * java.io.InputStream, java.io.OutputStream)
	 */
	@Override
	public void writeToFile(List<XLWorkSheetReport> worksheets, InputStream excelTemplateFileToRead,
			OutputStream outputStream) throws IOException, OpenXML4JException {

		Workbook workbook = new XSSFWorkbook(excelTemplateFileToRead);
		for (XLWorkSheetReport worksheet : worksheets) {
			String sheetname = worksheet.getSheetName();
			Map<String, DataTable> sections = worksheet.getSections();
			Sheet sheet = workbook.getSheet(sheetname);
			if (sheet == null) {
				sheet = workbook.createSheet(sheetname);
			}
			int rowCount = 1;
			writeSections(sections, sheet, rowCount);

		}

		workbook.write(outputStream);
		workbook.close();
	}

	private void writeSections(Map<String, DataTable> sections, Sheet sheet, int rowCount) {
		for (Map.Entry<String, DataTable> entry : sections.entrySet()) {
			DataTable v = entry.getValue();
			Table<String, String, CellValue> table = v.getTable();
			rowCount = writeSection(sheet, rowCount, table);
			rowCount++;
		}
	}
	
	
	


	private int writeSection(Sheet sheet, int rowCount,Table<String, String, CellValue> table) {
		int colCount;
		Set<String> colheaders = table.columnKeySet();
		Row row = sheet.createRow(rowCount++);
		colCount = 0;
		// Write header
		for (String colheader : colheaders) {
			row.createCell(colCount++).setCellValue(colheader);
		}
		for (String rowKey : table.rowKeySet()) {
			row = sheet.createRow(rowCount++);
			colCount = 0;
			for (String colheader : colheaders) {
				CellValue cellValue = table.get(rowKey, colheader);
				if (cellValue != null) {
					if (cellValue.getCellType() == com.maxcheung.models.CellType.CELLTYPE_BIGDECIMAL) {
						row.createCell(colCount).setCellValue(Double.valueOf(cellValue.getStringCellValue()));
						row.getCell(colCount).setCellType(CellType.NUMERIC);
					} else {
						row.createCell(colCount).setCellValue(cellValue.getStringCellValue());
					}

				}
				colCount++;
			}
		}
		return rowCount;
	}

}