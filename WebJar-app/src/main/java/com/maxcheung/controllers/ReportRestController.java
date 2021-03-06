package com.maxcheung.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.maxcheung.models.AccountCashBalanceSummary;
import com.maxcheung.models.Bond;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.CellValueHighChartBar;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.FormatType;
import com.maxcheung.models.ReportComboTemplate;
import com.maxcheung.models.ReportSmartRCVTemplate;
import com.maxcheung.models.ReportSmartRCVXTemplate;

@RestController
@RequestMapping("report")
public class ReportRestController {

	private static final Logger LOG = LoggerFactory.getLogger(ReportRestController.class);

	@Autowired
	private ReportService reportCashBalanceService;

	@Autowired
	private DataTableService dataTableService;

	@RequestMapping(method = RequestMethod.GET, path = "/cash-balance")
	public AccountCashBalanceSummary getAccountCashBalanceSummary() {
		LOG.info("Get AccountCashBalanceSummary");
		AccountCashBalanceSummary accountCashBalanceSummary = reportCashBalanceService.getAccountCashBalanceSummary();
		return accountCashBalanceSummary;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/highchartcombo")
	public ReportComboTemplate getHighCartData() {
		LOG.info("Get getHighCartData");

		// DataTableServiceImpl dataTableServiceImpl = new DataTableServiceImpl();

		Table<String, String, CellValue> table = getTable();
		Table<String, String, CellValue> highChartTable = dataTableService.transformTable(table,
				FormatType.HIGHCHARTPIE);
		Table<String, String, CellValue> highchartBarTable = dataTableService.transformTable(table,
				FormatType.HIGHCHARTBAR);
		List<CellValue> highchartpieData = highChartTable.values().stream().collect(Collectors.toList());

		List<BigDecimal> smartChartData = dataTableService.convertToListAmounts(highChartTable);

		List<List<Object>> highchartBarValue = highchartBarTable.values().stream().map(x -> (CellValueHighChartBar) x)
				.map(y -> y.getPairValue()).collect(Collectors.toList());

		ReportComboTemplate reportComboTemplate = new ReportComboTemplate();
		reportComboTemplate.setHighchartpie(highchartpieData);
		reportComboTemplate.setSmartchartlabel(highChartTable.rowKeySet());
		reportComboTemplate.setSmartchartdata(smartChartData);
		reportComboTemplate.setHighchartbar(highchartBarValue);

		return reportComboTemplate;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/chartdata")
	public Table<String, String, CellValue> getChartData(@RequestParam("formatType") FormatType formatTypeParam) {
		LOG.info("Get getHighCartData");
		// CellType cellType = CellType.valueOf(cellTypeParam);
		// CellType.HIGHCHARTPIE
		// CellType.HIGHCHARTBAR
		Table<String, String, CellValue> table = getTable();
		Table<String, String, CellValue> dest = dataTableService.transformTable(table, formatTypeParam);
		return dest;
	}

	
	@RequestMapping(method = RequestMethod.GET, path = "/highchart2")
	public Table<String, String, CellValue> getHighCartData2() {
		LOG.info("Get getHighCartData");
		Table<String, String, CellValue> table = getTable();
		return table;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/highchart3")
	public Table<String, String, CellValue> getHighCartData3() {
		LOG.info("Get getHighCartData");
		// DataTableServiceImpl dataTableServiceImpl = new DataTableServiceImpl();
		Table<String, String, CellValue> table = getTable();
		Table<String, String, CellValue> dest = dataTableService.transformTable(table, FormatType.HIGHCHARTPIE);
		return dest;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/Defaultchart")
	public List<CellValue> getDefaultData() {
		LOG.info("Get Defaultchart");
		List<CellValue> data = new ArrayList<CellValue>();
		CellValue cellValue = new CellValueDefault();
		cellValue.setRowKey("Microsoft Internet Explorer");
		cellValue.setColumnKey("Percentage");
		cellValue.setCellValue(BigDecimal.valueOf(53.33));
		data.add(cellValue);
		return data;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/SmartTableRCV")
	public ReportSmartRCVTemplate getSmartTableRCV() {
		LOG.info("Get SmartTableRCV");
		Table<String, String, String> table = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		// Table<String, String, CellValue> sales = Tables.newCustomTable(new
		// LinkedHashMap<>(), LinkedHashMap::new);
		Bond bond = new Bond();
		bond.setName("James");
		bond.setSalary(BigDecimal.valueOf(2000));
		bond.setAge(18L);
		bond.setDob(LocalDate.of(2000, 01, 28));

		table.put("Row1", "Profile", "Profile1");
		table.put("Row1", "Name", bond.getName());
		table.put("Row1", "Salary", bond.getSalary().toString());
		table.put("Row1", "Age", bond.getAge().toString());
		table.put("Row1", "Dob", bond.getDob().toString());
		table.put("Row1", "Sales", "");
		table.put("Row1", "EmployeeId", "Employee1");
		table.put("Row2", "Profile", "Profile2");
		table.put("Row2", "Name", "Mary");
		table.put("Row2", "Salary", "1200");
		table.put("Row2", "Age", "35");
		table.put("Row2", "EmployeeId", "Employee2");

		CellValue cellValue1 = new CellValueDefault();
		cellValue1.setRowKey("Employee1");
		cellValue1.setColumnKey("Sales");
		cellValue1.setCellValue(BigDecimal.TEN);

		CellValue cellValue2 = new CellValueDefault();
		cellValue2.setRowKey("Employee1");
		cellValue2.setColumnKey("Sales");
		cellValue2.setCellValue(BigDecimal.ONE);

		CellValue cellValue3 = new CellValueDefault();
		cellValue3.setRowKey("Employee2");
		cellValue3.setColumnKey("Sales");
		cellValue3.setCellValue(BigDecimal.ONE);

		List<CellValue> moneyCells = new ArrayList<>();
		moneyCells.add(cellValue1);
		moneyCells.add(cellValue2);
		moneyCells.add(cellValue3);
		DataTable sales = dataTableService.createDataTableFromList(moneyCells);

		ReportSmartRCVTemplate reportSmartRCVTemplate = new ReportSmartRCVTemplate();
		reportSmartRCVTemplate.setTable(table);
		reportSmartRCVTemplate.setSales(sales.getTable());
		return reportSmartRCVTemplate;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/SmartTableRCVX")
	public ReportSmartRCVXTemplate getSmartTableRCVX() {
		LOG.info("Get SmartTableRCV");
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

	@RequestMapping(method = RequestMethod.GET, path = "/DefaultchartTable")
	public Table<String, String, CellValue> getDefaultDataTable() {
		LOG.info("Get DefaultchartTable");
		return getTable();
	}

	private Table<String, String, CellValue> getTable() {
		List<CellValue> data = new ArrayList<CellValue>();
		data.add(createCell("Microsoft Internet Explorer", "Percentage", BigDecimal.valueOf(56.33)));
		data.add(createCell("Chrome", "Percentage", BigDecimal.valueOf(24.03)));
		data.add(createCell("Firefox", "Percentage", BigDecimal.valueOf(10.38)));
		data.add(createCell("Safari", "Percentage", BigDecimal.valueOf(4.77)));
		data.add(createCell("Opera", "Percentage", BigDecimal.valueOf(0.91)));
		data.add(createCell("Proprietary or Undetectable", "Percentage", BigDecimal.valueOf(0.2)));
		Table<String, String, CellValue> table = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		for (CellValue cellValue : data) {
			table.put(cellValue.getRowKey(), cellValue.getColumnKey(), cellValue);
		}
		return table;
	}

	private CellValue createCell(String rowKey, String colKey, BigDecimal value) {
		CellValue cellValue = new CellValueDefault();
		cellValue.setRowKey(rowKey);
		cellValue.setColumnKey(colKey);
		cellValue.setCellValue(value);
		return cellValue;
	}
	
	
	/**
	 * Handle request to download an Excel document
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public String download(Model model) {
		ReportSmartRCVXTemplate reportSmartRCVXTemplate = getSmartTableRCVX();
	    model.addAttribute("reportSmartRCVXTemplate", reportSmartRCVXTemplate);
	    return "";
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/financialchartdata")
	public Table<String, String, CellValue> getFinancialChartData(@RequestParam("formatType") FormatType formatTypeParam) {
		LOG.info("Get getHighCartData");
		// CellType cellType = CellType.valueOf(cellTypeParam);
		// CellType.HIGHCHARTPIE
		// CellType.HIGHCHARTBAR
		Table<String, String, CellValue> table = getFinancialTable();
		Table<String, String, CellValue> dest = dataTableService.transformTable(table, formatTypeParam);
		return dest;
	}

	
	@RequestMapping(method = RequestMethod.GET, path = "/get-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public @ResponseBody byte[] getFile() throws IOException {
		LOG.info("Get getFile");
		 InputStream in = getClass()
			      .getResourceAsStream("/com/baeldung/produceimage/data.txt");
			    return IOUtils.toByteArray(in);
	}

	
	private Table<String, String, CellValue> getFinancialTable() {
	//	LocalDate start = LocalDate.now();
//		LocalDate end = LocalDate.now().plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
			LocalDate start = LocalDate.of(2018, 1, 1);
		LocalDate end = LocalDate.of(2018, 6, 13);
		List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
		    .limit(ChronoUnit.DAYS.between(start, end))
		    .collect(Collectors.toList());
		
		
		List<CellValue> data = new ArrayList<CellValue>();
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		 
		 long leftLimit = 25L;
		 long rightLimit = 90L;
		 long generatedLong = new RandomDataGenerator().nextLong(leftLimit, rightLimit);
		 
		for (LocalDate date : dates) {
			String formatDateTime = date.format(formatter);
			generatedLong = new RandomDataGenerator().nextLong(leftLimit, rightLimit);
			data.add(createCell(formatDateTime, "Series1", BigDecimal.valueOf(generatedLong)));
			data.add(createCell(formatDateTime, "Series2", BigDecimal.valueOf(45)));
			data.add(createCell(formatDateTime, "Series3", BigDecimal.valueOf(90)));
		}
		
		data.add(createCell("Chrome", "Percentage", BigDecimal.valueOf(24.03)));
		data.add(createCell("Firefox", "Percentage", BigDecimal.valueOf(10.38)));
		data.add(createCell("Safari", "Percentage", BigDecimal.valueOf(4.77)));
		data.add(createCell("Opera", "Percentage", BigDecimal.valueOf(0.91)));
		data.add(createCell("Proprietary or Undetectable", "Percentage", BigDecimal.valueOf(0.2)));
		Table<String, String, CellValue> table = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		for (CellValue cellValue : data) {
			table.put(cellValue.getRowKey(), cellValue.getColumnKey(), cellValue);
		}
		return table;
	}

	
	
}