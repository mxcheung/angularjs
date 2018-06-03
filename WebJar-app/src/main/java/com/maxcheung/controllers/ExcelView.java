package com.maxcheung.controllers;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.google.common.collect.Table;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.ReportSmartRCVXTemplate;

@Component
public class ExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// change the file name
	    response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");
	    
	    @SuppressWarnings("unchecked")
	    ReportSmartRCVXTemplate reportSmartRCVXTemplate =  (ReportSmartRCVXTemplate) model.get("reportSmartRCVXTemplate");
	    if (reportSmartRCVXTemplate != null) {
	    	
	    	// create excel xls sheet
	    	Sheet sheet = workbook.createSheet("User Detail");
	    	sheet.setDefaultColumnWidth(30);
	    	
	    	// create style for header cells
	    	CellStyle style = workbook.createCellStyle();
	    	Font font = workbook.createFont();
	    	font.setFontName("Arial");
	    	style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
	    	style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    	font.setBold(true);
	    	//    font.setColor(XSSFColor.WHITE.index);
	    	style.setFont(font);
	    	int rowCount = 1;
	    	
	    	DataTable dataTable = reportSmartRCVXTemplate.getDataTable();
	    	Table<String, String, CellValue> table = dataTable.getTable();
	    	// for each row key
	    	for (String key : table.rowKeySet()) {
	    		Row userRow =  sheet.createRow(rowCount++);
	    		int colCount = 1;
	    		for (Entry<String, CellValue> dataRow : table.row(key).entrySet()) {
	    			userRow.createCell(colCount++).setCellValue(dataRow.getValue().getStringCellValue());
	    		}
	    	}
	    }
	    
	}

	
}