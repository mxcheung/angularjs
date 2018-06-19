package com.maxcheung.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.maxcheung.controllers.CellValueConverter;
import com.maxcheung.controllers.CellValueConverterImpl;
import com.maxcheung.controllers.DataTableService;
import com.maxcheung.controllers.DataTableServiceImpl;
import com.maxcheung.controllers.ReportService;
import com.maxcheung.controllers.ReportServiceImpl;
import com.maxcheung.datatable.service.XLWorkSheetReport;
import com.maxcheung.models.AccountCashBalanceSummary;
import com.maxcheung.models.Bond;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.ReportSmartRCVXTemplate;
import com.maxcheung.models.ReportSummary;

public class XSSFServiceTest {

	private static String FILE_NAME = "temp.xlsx";

	private static final String XSSF_RESOURCE = "/xssf/testdata2.xlsx";

	private static final String XSSF_RANGE_RESOURCE = "/xssf/testrangedata.xlsx";

	private static final String XSSF_TEMPLATE_RESOURCE = "/xssf/testrangetemplate.xlsx";

	
	private InputStream excelFileToRead = getClass().getResourceAsStream(XSSF_RESOURCE);

	private InputStream excelRangeFileToRead = getClass().getResourceAsStream(XSSF_RANGE_RESOURCE);

	private InputStream excelTemplateFileToRead = getClass().getResourceAsStream(XSSF_TEMPLATE_RESOURCE);

	private XSSFService xSSFService;

	private DataTableService dataTableService;
	private CellValueConverter cellValueConverter;
	private ReportService reportService;

	@Before
	public void setup() throws Exception {
		xSSFService = new XSSFServiceImpl();
		cellValueConverter = new CellValueConverterImpl();
		dataTableService = new DataTableServiceImpl(cellValueConverter);

	}

	@Test
	public void testWriteToExcel() throws IOException, OpenXML4JException {
		Workbook workbook = xSSFService.writeToExcel();
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";
		 
		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		workbook.write(outputStream);
		workbook.close();
	}

	@Test
	public void testReadRange() throws IOException, OpenXML4JException {
		
		RowMapper rowMapper = new RowMapper();
		 // Setup code
	    	String cellName = "rangeTxn1";
          Workbook workbook = new XSSFWorkbook(excelRangeFileToRead);
          Sheet datatypeSheet = workbook.getSheetAt(0);
          // Retrieve the named range
          // Will be something like "$C$10,$D$12:$D$14";
          int namedCellIdx = workbook.getNameIndex(cellName);
          Name aNamedCell = workbook.getNameAt(namedCellIdx);
          // retrieve the cell at the named range and test its contents
          AreaReference aref = new AreaReference(aNamedCell.getRefersToFormula());
          
          CellReference topLeft = aref.getFirstCell();
          CellReference botRight = aref.getLastCell();
          
          
          
          
          CellReference[] crefs = aref.getAllReferencedCells();
          for (int i=0; i<crefs.length; i++) {
              Sheet s = workbook.getSheet(crefs[i].getSheetName());
              Row r = s.getRow(crefs[i].getRow());
              Cell c = r.getCell(crefs[i].getCol());
              int rowidx = c.getRowIndex();
              int colidx = c.getColumnIndex();
               CellType cellType = c.getCellTypeEnum();
              Object cellValue = rowMapper.getCellValue(c);
              cellValue = rowMapper.getCellValue(c);
              // extract the cell contents based on cell type etc.
          }

	}

	@Test
	public void testWriteToExcelTemplate() throws IOException, OpenXML4JException {
		Workbook workbook = new XSSFWorkbook(excelTemplateFileToRead);
	    Sheet sheet = workbook.createSheet();
		Row headingRow = sheet.createRow(0);
		headingRow.createCell(1).setCellValue("ID");
		short rowNo = 1;
		Row row = sheet.createRow(rowNo);
        row.createCell((short)0).setCellValue("Id1");
		
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "templateReport.xlsx";
		 
		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		workbook.write(outputStream);
		workbook.close();
	}


	@Test
	public void testWriteDataTemplate() throws IOException, OpenXML4JException {
		ReportService reportService = new ReportServiceImpl();

		AccountCashBalanceSummary accountCashBalanceSummary = reportService.getAccountCashBalanceSummary();
		
		
		ReportSmartRCVXTemplate reportSmartRCVXTemplate = getSmartTableRCVX();
		
		XLWorkSheetReport xLWorkSheetReport = new XLWorkSheetReport();
		Map<String, DataTable> sections = new LinkedHashMap<String, DataTable>();
		
		Map<String, DataTable> origSections = accountCashBalanceSummary.getReports().get("ORIG").getSections();
		for (Map.Entry<String, DataTable> entry : origSections.entrySet()) {
		    sections.put("ORIG" + entry.getKey(), entry.getValue());
		}

		Map<String, DataTable> baseSections = accountCashBalanceSummary.getReports().get("BASE").getSections();
		for (Map.Entry<String, DataTable> entry : baseSections.entrySet()) {
		    sections.put("BASE" + entry.getKey(), entry.getValue());
		}
	    sections.put("RCVX" , reportSmartRCVXTemplate.getDataTable());
		xLWorkSheetReport.setSections(sections );
		
		
		DataTable rcxDataTable = reportSmartRCVXTemplate.getDataTable();
		Table<String, String, CellValue> table = rcxDataTable.getTable();
		
		
		
		Workbook workbook = new XSSFWorkbook(excelTemplateFileToRead);
	    
		/***********************************************/

	    // create excel xls sheet
	    Sheet sheet = workbook.createSheet("User Detail");
	    sheet.setDefaultColumnWidth(30);

	    // create style for header cells
	    CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setFontName("Arial");
	    style.setFillForegroundColor(HSSFColor.BLUE.index);
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    font.setBold(true);
	    font.setColor(HSSFColor.WHITE.index);
	    style.setFont(font);

	    
	    
	    CellStyle footerStyle = workbook.createCellStyle();
	    footerStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
	    footerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    int rowCount = 1;
	    
	    Map<String, DataTable> reportSections = xLWorkSheetReport.getSections();
		for (Map.Entry<String, DataTable> entry : reportSections.entrySet()) {
			//***************************************
			// ****** SECTION
			//
		     DataTable dataTable = entry.getValue();
		     table = dataTable.getTable();
		     
		     // section name
		     Row header = sheet.createRow(rowCount++);
	    	 header.createCell(0).setCellValue(entry.getKey());
		     
		     // create header row
		     header = sheet.createRow(rowCount++);
		     Set<String> colheaders = table.columnKeySet();
		     int colCount = 0;
		     for (String colheader : colheaders) {
		    	 header.createCell(colCount).setCellValue(colheader);
		    	 header.getCell(colCount).setCellStyle(style);
		    	 colCount++;
		     }
		     // create body row
		     // for each row key
		     
		     table.rowKeySet();
		     
		     for (String rowKey : table.rowKeySet()) {
		    	 Row userRow =  sheet.createRow(rowCount++);
		    	 colCount = 0;
			     for (String colheader : colheaders) {
			    	 CellValue cellValue = table.get(rowKey, colheader);
			    	 if (cellValue != null) {
			    		 userRow.createCell(colCount).setCellValue(cellValue.getStringCellValue());
			    	 } else {
			    		 String x = "GGF";
			    		 x = "GGF";
			    	 }
			    	 colCount++;
			     }
		     }
		     // Footer
		     Map<String, CellValue> columnTotals =  dataTable.getColumnTotals();
	    	 Row userRow =  sheet.createRow(rowCount);
	    	 colCount = 0;
		     for (String colheader : colheaders) {
		    	 CellValue cellValue = columnTotals.get(colheader);
		    	 if (cellValue != null) {
		    		 userRow.createCell(colCount).setCellValue(cellValue.getStringCellValue());
		    		 userRow.getCell(colCount).setCellStyle(footerStyle);
		    	 } else {
		    		 String x = "GGF";
		    		 x = "GGF";
		    	 }
		    	 colCount++;
		     }
		     rowCount++;
		     rowCount++;
		     
		     
		     
//		 	private Map<String, CellValue> rowTotals;

//			private Map<String, CellValue> columnTotals;

	//		private CellValue grandTotal;
		     
		}
	    
	    

	    
	    
	    
	    
	    /***********************************************/
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "templateReport.xlsx";
		 
		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		workbook.write(outputStream);
		workbook.close();
	}

	private ReportSmartRCVXTemplate getSmartTableRCVX() {
		Table<String, String, String> table = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		// Table<String, String, CellValue> sales = Tables.newCustomTable(new
		// LinkedHashMap<>(), LinkedHashMap::new);
		Bond bond = new Bond();
		bond.setId("id1");
		bond.setName("James");
		bond.setSalary(BigDecimal.valueOf(2000));
		bond.setAge(18L);
		bond.setDob(LocalDate.of(2000, 01, 28));

		Bond bond1 = new Bond();
		bond1.setId("id2");
		bond1.setName("Mary");
		bond1.setSalary(BigDecimal.valueOf(1200));
		bond1.setAge(35L);

		Bond bond2 = new Bond();
		bond2.setId("id3");
		bond2.setName("Mark");


		List<CellValue> moneyCells = new ArrayList<>();
		moneyCells.add(new CellValueDefault(bond.getId(), "Name", bond.getName()));
		moneyCells.add(new CellValueDefault(bond.getId(), "Salary", bond.getSalary()));
		moneyCells.add(new CellValueDefault(bond.getId(), "Age", bond.getAge().toString()));
		moneyCells.add(new CellValueDefault(bond.getId(), "Dob", bond.getDob().toString()));
		moneyCells.add(new CellValueDefault(bond.getId(), "Sales", BigDecimal.TEN));
		moneyCells.add(new CellValueDefault(bond.getId(), "Sales", BigDecimal.ONE));
		moneyCells.add(new CellValueDefault(bond1.getId(), "Name", bond1.getName()));
		moneyCells.add(new CellValueDefault(bond1.getId(), "Salary", bond1.getSalary()));
		moneyCells.add(new CellValueDefault(bond1.getId(), "Age", bond1.getAge().toString()));
		moneyCells.add(new CellValueDefault(bond1.getId(), "Sales", BigDecimal.ONE));
		moneyCells.add(new CellValueDefault(bond2.getId(), "Name", bond2.getName()));
		moneyCells.add(new CellValueDefault(bond2.getId(), "Salary", bond2.getSalary()));
		DataTable dataTable = dataTableService.createDataTableFromList(moneyCells);

		ReportSmartRCVXTemplate reportSmartRCVXTemplate = new ReportSmartRCVXTemplate();
		reportSmartRCVXTemplate.setDataTable(dataTable);
		return reportSmartRCVXTemplate;
	}
}