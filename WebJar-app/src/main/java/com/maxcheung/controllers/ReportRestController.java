package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.maxcheung.models.AccountCashBalanceSummary;
import com.maxcheung.models.CellType;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.DefaultCellValue;
import com.maxcheung.models.HighChartBarCellValue;
import com.maxcheung.models.ReportComboTemplate;

@RestController
@RequestMapping("report")
public class ReportRestController {

	private static final Logger LOG = LoggerFactory.getLogger(ReportRestController.class);

	@Autowired
	private ReportService reportCashBalanceService;

	@RequestMapping(method = RequestMethod.GET, path = "/cash-balance")
	public AccountCashBalanceSummary getAccountCashBalanceSummary() {
		LOG.info("Get AccountCashBalanceSummary");
		AccountCashBalanceSummary accountCashBalanceSummary = reportCashBalanceService.getAccountCashBalanceSummary();
		return accountCashBalanceSummary;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/highchartcombo")
	public ReportComboTemplate getHighCartData() {
		LOG.info("Get getHighCartData");

		DataTableServiceImpl dataTableServiceImpl = new DataTableServiceImpl();

		Table<String, String, CellValue> table = getTable();
		Table<String, String, CellValue> highChartTable = dataTableServiceImpl.transformTable(table, CellType.HIGHCHARTPIE);
		Table<String, String, CellValue> highchartBarTable = dataTableServiceImpl.transformTable(table, CellType.HIGHCHARTBAR);

		// List<CellValue> target = new ArrayList<CellValue>();
		// for ( CellValue sourceCell : sourceCells) {
		// target.add(dataTableServiceImpl.convert(sourceCell, CellType.HIGHCHART));
		// }

		List<CellValue> highchartpie = highChartTable.values().stream().collect(Collectors.toList());

		ReportComboTemplate reportComboTemplate = new ReportComboTemplate();
		reportComboTemplate.setHighchartpie(highchartpie);
		reportComboTemplate.setSmartchartlabel(highChartTable.rowKeySet());

		List<BigDecimal> amounts = highChartTable.column("Percentage").values().stream().map(x -> x.getValue())
				.collect(Collectors.toList());
		reportComboTemplate.setSmartchartdata(amounts);

		
/*		
		List<List<Object>> highchartbar = new ArrayList<List<Object>>();
		for ( CellValue cell : highchartpie) {
			highchartbar.add(Arrays.asList(cell.getRowKey(), cell.getValue()));
		}
*/		
	//	List<List<Object>> highchartBarValue = highchartBarTable.values().stream().map(x ->  (List<Object>) x.getValue()).collect(Collectors.toList());
		
		
		List<List<Object>> highchartBarValue = highchartBarTable.values().stream()
			.map(x -> (HighChartBarCellValue) x)
			.map(y ->   y.getSpecialValue())
			.collect(Collectors.toList());  
		
//		List<BigDecimal> amounts = dest.column("Percentage").values().stream().map(x -> x.getValue())

		reportComboTemplate.setHighchartbar(highchartBarValue );

		return reportComboTemplate;
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
		DataTableServiceImpl dataTableServiceImpl = new DataTableServiceImpl();
		Table<String, String, CellValue> table = getTable();
		Table<String, String, CellValue> dest = dataTableServiceImpl.transformTable(table, CellType.HIGHCHARTPIE);
		return dest;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/Defaultchart")
	public List<CellValue> getDefaultData() {
		LOG.info("Get Defaultchart");
		List<CellValue> data = new ArrayList<CellValue>();
		CellValue cellValue = new DefaultCellValue();
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
		CellValue cellValue = new DefaultCellValue();
		cellValue.setRowKey(rowKey);
		cellValue.setColumnKey(colKey);
		cellValue.setValue(value);
		return cellValue;
	}
}