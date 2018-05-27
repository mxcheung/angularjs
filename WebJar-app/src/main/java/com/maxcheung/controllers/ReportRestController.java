package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.maxcheung.models.AccountCashBalanceSummary;
import com.maxcheung.models.CellType;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.CellValueHighChartBar;
import com.maxcheung.models.ReportComboTemplate;

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

//		DataTableServiceImpl dataTableServiceImpl = new DataTableServiceImpl();

		Table<String, String, CellValue> table = getTable();
		Table<String, String, CellValue> highChartTable = dataTableService.transformTable(table, CellType.HIGHCHARTPIE);
		Table<String, String, CellValue> highchartBarTable = dataTableService.transformTable(table, CellType.HIGHCHARTBAR);
		List<CellValue> highchartpieData = highChartTable.values()
				.stream()
				.collect(Collectors.toList());
		
		List<BigDecimal> smartChartData = dataTableService.convertToListAmounts(highChartTable);

		List<List<Object>> highchartBarValue = highchartBarTable.values().stream()
			.map(x -> (CellValueHighChartBar) x)
			.map(y ->   y.getPairValue())
			.collect(Collectors.toList());  
		
		
		ReportComboTemplate reportComboTemplate = new ReportComboTemplate();
		reportComboTemplate.setHighchartpie(highchartpieData);
		reportComboTemplate.setSmartchartlabel(highChartTable.rowKeySet());
		reportComboTemplate.setSmartchartdata(smartChartData);
		reportComboTemplate.setHighchartbar(highchartBarValue );

		return reportComboTemplate;
	}

	@RequestMapping( method = RequestMethod.GET, path = "/chartdata")
	public Table<String, String, CellValue> getChartData(@RequestParam("cellType") CellType cellTypeParam) {
		LOG.info("Get getHighCartData");
	//	CellType cellType = CellType.valueOf(cellTypeParam);
		// CellType.HIGHCHARTPIE
		// CellType.HIGHCHARTBAR
		Table<String, String, CellValue> table = getTable();
		Table<String, String, CellValue> dest = dataTableService.transformTable(table, cellTypeParam);
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
//		DataTableServiceImpl dataTableServiceImpl = new DataTableServiceImpl();
		Table<String, String, CellValue> table = getTable();
		Table<String, String, CellValue> dest = dataTableService.transformTable(table, CellType.HIGHCHARTPIE);
		return dest;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/Defaultchart")
	public List<CellValue> getDefaultData() {
		LOG.info("Get Defaultchart");
		List<CellValue> data = new ArrayList<CellValue>();
		CellValue cellValue = new CellValueDefault();
		cellValue.setRowKey("Microsoft Internet Explorer");
		cellValue.setColumnKey("Percentage");
		cellValue.setValue(BigDecimal.valueOf(53.33));
		data.add(cellValue);
		return data;
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
		cellValue.setValue(value);
		return cellValue;
	}
}