package com.maxcheung.datatable.service;

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

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
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
import com.maxcheung.models.AccountCashBalanceSummary;
import com.maxcheung.models.Bond;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.ReportSmartRCVXTemplate;

public class DataTableWriterTest {

	private static final String XSSF_TEMPLATE_RESOURCE = "/xssf/testrangetemplate.xlsx";

	private InputStream excelTemplateFileToRead = getClass().getResourceAsStream(XSSF_TEMPLATE_RESOURCE);

	private DataTableWriter dataTableWriter;

	private DataTableService dataTableService;
	
	private CellValueConverter cellValueConverter;



	@Before
	public void setup() throws Exception {
		dataTableWriter = new DataTableWriterImpl();
		cellValueConverter = new CellValueConverterImpl();
		dataTableService = new DataTableServiceImpl(cellValueConverter);

	}

	@Test
	public void testWriteToExcelTemplate() throws IOException, OpenXML4JException {
		List<XLWorkSheetReport> worksheets = new ArrayList<>();
		worksheets.add(getCashBalWorkSheet());
		worksheets.add(getSmartTableRCVXWorkSheet());
		File currDir = new File(".");
		String path = currDir.getAbsolutePath();
		String fileLocation = path.substring(0, path.length() - 1) + "templateReport.xlsx";
		FileOutputStream outputStream = new FileOutputStream(fileLocation);
		dataTableWriter.writeToFile(worksheets, excelTemplateFileToRead, outputStream);
	}

	private XLWorkSheetReport getCashBalWorkSheet() {
		XLWorkSheetReport xLWorkSheetReport = new XLWorkSheetReport();
		xLWorkSheetReport.setSheetName("CashBal");
		Map<String, DataTable> sections = new LinkedHashMap<String, DataTable>();
		ReportService reportService = new ReportServiceImpl();
		AccountCashBalanceSummary accountCashBalanceSummary = reportService.getAccountCashBalanceSummary();
		Map<String, DataTable> origSections = accountCashBalanceSummary.getReports().get("ORIG").getSections();
		for (Map.Entry<String, DataTable> entry : origSections.entrySet()) {
		    sections.put("ORIG" + entry.getKey(), entry.getValue());
		}
		Map<String, DataTable> baseSections = accountCashBalanceSummary.getReports().get("BASE").getSections();
		for (Map.Entry<String, DataTable> entry : baseSections.entrySet()) {
		    sections.put("BASE" + entry.getKey(), entry.getValue());
		}
		xLWorkSheetReport.setSections(sections);
		return xLWorkSheetReport;
	}


	private XLWorkSheetReport getSmartTableRCVXWorkSheet() {
		XLWorkSheetReport xLWorkSheetReport = new XLWorkSheetReport();
		xLWorkSheetReport.setSheetName("SmartTableRCVX");
		Map<String, DataTable> sections = new LinkedHashMap<String, DataTable>();
		ReportSmartRCVXTemplate reportSmartRCVXTemplate = getSmartTableRCVX();
		sections.put("SmartTableRCVX", reportSmartRCVXTemplate.getDataTable());
		xLWorkSheetReport.setSections(sections);
		return xLWorkSheetReport;
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
		dataTable.setTitle("Sales Report");
		ReportSmartRCVXTemplate reportSmartRCVXTemplate = new ReportSmartRCVXTemplate();
		reportSmartRCVXTemplate.setDataTable(dataTable);
		return reportSmartRCVXTemplate;
	}

	
}