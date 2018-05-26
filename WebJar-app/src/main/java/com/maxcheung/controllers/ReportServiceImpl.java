package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.maxcheung.models.AccountCashBalanceSummary;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.ReportSummary;

@Service
public class ReportServiceImpl implements ReportService {

	private static final String BASE_CCY = "USD";
	private static final String CASH_REPORT_CURRENCY = "CASH_REPORT_CURRENCY";
	private static final String CASH_REPORT_SECTIONS = "CASH_REPORT_ACCT_GROUP_PARENT";
	private static final String CASH_REPORT_ACCT_MAP = "CASH_REPORT_ACCT_GROUP_MAP";
	


    /**
     * {@inheritDoc}
     */
    @Override
	public AccountCashBalanceSummary getAccountCashBalanceSummary() {
		/*
		 *  1.1. Fetch Currencies
		 *  1.2. Fetch Account Mapping
		 *  1.3. Fetch Section
		 *  1.4. Fetch Exchange rates
		 *  1.5. Fetch projections
		 *  1.6. Fetch projections base currency		 *  
		 */
		LocalDate enquiryDt = LocalDate.of(2018, 05, 01) ;
		Set<String> allCurrencies = fetchCurrencies(CASH_REPORT_CURRENCY);
		Set<String> sections = fetchSections(CASH_REPORT_SECTIONS);
		Map<String,String> accountMap = fetchAccountMap(CASH_REPORT_ACCT_MAP);
		Map<String,BigDecimal> exchangeRate = fetchExchangeRate(enquiryDt);
		List<CellValueDefault> txnsOriginal;
		List<CellValueDefault> txnsBase;
		txnsOriginal = fetchTxns(enquiryDt);
		
		/*
		 *  2.1. Convert origtxn
		 *  2.2. Convert txn
		 */
		txnsOriginal = convertTxnsOrig(txnsOriginal, accountMap);
		txnsBase = convertTxnsBase(txnsOriginal, exchangeRate);
		
		/*
		 *  3.1 Create report original currency
		 *  3.2 Create sections
		 *  3.2.1. Filter projections by section account
		 *  3.3 Create report base currency
		 *  3.4 Create sections base currency
		 */
		
		ReportSummary reportOrig = createReport(allCurrencies, sections, txnsOriginal);
		ReportSummary reportBase = createReport(allCurrencies, sections, txnsBase);
		Map<String, ReportSummary> reports = new HashMap<String, ReportSummary>();
		reports.put("ORIG", reportOrig);
		reports.put("BASE", reportBase);
		
		
		/*
		 * 3. Create Summary
		 * 3.1 Assemble  contents 
		 */
		
		AccountCashBalanceSummary accountCashBalanceSummary = new AccountCashBalanceSummary();
		accountCashBalanceSummary.setReports(reports);
		return accountCashBalanceSummary;
		
	}

	
	private Set<String> fetchCurrencies(String refType) {
		Set<String> allCurrencies= new HashSet<>();
		allCurrencies.add("USD");
		allCurrencies.add("JPY");
		allCurrencies.add("EUR");
		allCurrencies.add("HKD");
		return allCurrencies;
	}
	
	private Set<String> fetchSections(String refType) {
		Set<String> sections= new HashSet<>();
		sections.add("APAC");
		sections.add("EMEA");
		sections.add("TRI");
		return sections;
	}

	private Map<String,String> fetchAccountMap(String refType) {
		Map<String,String> accountMap= new HashMap<>();
		accountMap.put("Account1", "Group1");
		accountMap.put("Account2", "Group1");
		accountMap.put("Account3", "Group3");
		return accountMap;
	}

	private Map<String,BigDecimal> fetchExchangeRate(LocalDate enquiryDt) {
		Map<String,BigDecimal> exchangeRate = new HashMap<>();
		exchangeRate.put("AUD", BigDecimal.ONE);
		exchangeRate.put("USD", BigDecimal.ONE);
		return exchangeRate;
	}

	private List<CellValueDefault> fetchTxns(LocalDate enquiryDt) {
		List<CellValueDefault> txns = new ArrayList<>();
		CellValueDefault cellValue = new CellValueDefault();
		cellValue.setValue(BigDecimal.ONE);
		txns.add(cellValue);
		return txns;
	}

	/**************************************************
	 * 
	 *     Convertors
	 */

	/**
     * {@inheritDoc}
     */
    @Override
	public List<CellValueDefault> convertTxnsOrig(List<CellValueDefault> accountBalances, Map<String,String> accountMap) {
		List<CellValueDefault> convertTxns = new ArrayList<>();
		CellValueDefault cellValue = new CellValueDefault();
		cellValue.setValue(BigDecimal.ONE);
		convertTxns.add(cellValue);
		return convertTxns;
	}

	private List<CellValueDefault> convertTxnsBase(List<CellValueDefault> origTxn, Map<String,BigDecimal> exchangeRate) {
		List<CellValueDefault> txns = new ArrayList<>();
		CellValueDefault cellValue = new CellValueDefault();
		cellValue.setValue(BigDecimal.ONE);
		txns.add(cellValue);
		return txns;
	}
	
	

	private ReportSummary createReport(Set<String> headers, Set<String> sections, List<CellValueDefault> txns) {
		ReportSummary accountCashBalanceReportBase = new ReportSummary();
		accountCashBalanceReportBase.setHeaders(headers);
		accountCashBalanceReportBase.setSections(getSections(sections,txns ));
		return accountCashBalanceReportBase;
	}


	private DataTable createSection(String section,  List<CellValueDefault> txns) {
		DataTable moneyTable = new DataTable();
		Table<String, String, CellValue> table = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		// populate table
		CellValueDefault value = new CellValueDefault();
		value.setValue(BigDecimal.TEN);
		CellValueDefault value1 = new CellValueDefault();
		value1.setValue(BigDecimal.ONE);
		table.put("Row1", "USD", value );
		table.put("Row1", "HKD", value1 );
		table.put("Row3", "EUR", value );
		moneyTable.setTable(table);
		moneyTable.setRowTotals(getRowTotal(table));
		moneyTable.setColumnTotals(getColumnTotal(table));
		moneyTable.setGrandTotal(getGrandTotal(moneyTable.getRowTotals()));
		return moneyTable;
	}

	private Map<String, DataTable> getSections(Set<String> sections,  List<CellValueDefault> txns) {
		Map<String, DataTable> sectionmap = new LinkedHashMap<>();
		for (String section : sections) {
			List<CellValueDefault> filteredTxns =  txns; 
			sectionmap.put("USD", createSection(section, filteredTxns));
		}
		return sectionmap;
	}
	
	

	
	
	public Map<String, CellValue> getRowTotal(Table<String, String, CellValue> table) {
		Set<String> rowKeys = table.rowKeySet();
		Map<String, CellValue> rowTotals = new HashMap<String, CellValue>();
		for (String rowKey : rowKeys) {
	//		ReportValue total = new ReportValue();
	//		Collection<ReportValue> values = table.rowMap().get(rowKey).values();
	//		for (ReportValue value : values) {
	//			total.setValue(total.getValue().add(value.getValue()));
	//		}

//			rowTotals.put(rowKey, total);
			rowTotals.put(rowKey, getGrandTotal(table.row(rowKey)));
		}
		return rowTotals;
	}

	public Map<String, CellValue> getColumnTotal(Table<String, String, CellValue> table) {
		Set<String> columnKeys = table.columnKeySet();
		Map<String, CellValue> columnTotals = new HashMap<String, CellValue>();
		for (String columnKey : columnKeys) {
//			ReportValue total = new ReportValue();
//			Collection<ReportValue> values = table.columnMap().get(columnKey).values();
//			for (ReportValue value : values) {
//				total.setValue(total.getValue().add(value.getValue()));
//			}
			columnTotals.put(columnKey, getGrandTotal(table.column(columnKey)));
		}
		return columnTotals;
	}
	
	public CellValue getGrandTotal(Map<String, CellValue> totals) {
		CellValue moneyCell = new CellValueDefault();
		moneyCell.setValue(totals.values().stream().map(x -> x.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add));
		return moneyCell;
	}
	/*
	private Map<String, AccountCashBalanceReport> createReports() {
		AccountCashBalanceReport accountCashBalanceReportBase = new AccountCashBalanceReport();
		accountCashBalanceReportBase.setSections(mapBaseCCy);
		AccountCashBalanceReport accountCashBalanceReportUSD = new AccountCashBalanceReport();
		accountCashBalanceReportUSD.setSections(mapBaseUSD);
		
		reports.put("BASE", accountCashBalanceReportBase);
		reports.put(USD, accountCashBalanceReportUSD);
		
		return reports;
	}
	*/

	
/*
	private Map<String, MoneyTable> getMoneyBag(List<MoneyCell> allMonies, Set<String> accounts,
			Map<String, Set<String>> parentMap, LocalDate endDate, Optional<String> exchangeCCY) {
		Map<String, MoneyTable> cashBalanceMap = new LinkedHashMap<>();
		String suffix = exchangeCCY.isPresent() ? exchangeCCY.get() : "";
		for (String account : accounts) {

			cashBalanceMap.put(account + suffix, getMoneyTable(account, parentMap, allMonies));
		}

		return cashBalanceMap;
	}
*/
	
	
}