package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.maxcheung.models.ReportTable;
import com.maxcheung.models.AccountCashBalanceSummary;
import com.maxcheung.models.ReportValue;
import com.maxcheung.models.ReportSection;

@Service
public class ReportCashBalanceServiceImpl implements ReportCashBalanceService {

	private static final String USD = "USD";
	private static final String CASH_REPORT_ACCT_GROUP_PARENT = "CASH_REPORT_ACCT_GROUP_PARENT";
	private static final String CASH_REPORT_CURRENCY = "CASH_REPORT_CURRENCY";
	private static final String CASH_REPORT_ACCT_GROUP_MAP = "CASH_REPORT_ACCT_GROUP_MAP";
	


	@Override
	public AccountCashBalanceSummary getAccountCashBalanceSummary() {
		AccountCashBalanceSummary accountCashBalanceSummary = new AccountCashBalanceSummary();
		Set<String> allCurrencies= new HashSet<>();
		allCurrencies.add("USD");
		allCurrencies.add("JPY");
		allCurrencies.add("EUR");
		allCurrencies.add("HKD");
		accountCashBalanceSummary.setReports(createReports(allCurrencies) );
		return accountCashBalanceSummary;
		
	}

	private Map<String, ReportTable> createReports(Set<String> headers) {
		Map<String, ReportTable> reports = new HashMap<String, ReportTable>();
		ReportTable accountCashBalanceReportBase = new ReportTable();
		accountCashBalanceReportBase.setHeaders(headers);
		
		accountCashBalanceReportBase.setSections(getSections());
		
		ReportTable accountCashBalanceReportUSD = new ReportTable();
		accountCashBalanceReportUSD.setHeaders(headers);
		reports.put("BASE", accountCashBalanceReportBase);
		reports.put(USD, accountCashBalanceReportUSD);
		
		return reports;
	}

	
	private Map<String, ReportSection> getSections() {
		Map<String, ReportSection> sections = new LinkedHashMap<>();
		sections.put("USD", getMoneyTable());
		return sections;
	}
	
	
	private ReportSection getMoneyTable() {
		ReportSection moneyTable = new ReportSection();
		Table<String, String, ReportValue> table = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		ReportValue value = new ReportValue();
		value.setValue(BigDecimal.TEN);
		ReportValue value1 = new ReportValue();
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

	
	
	public Map<String, ReportValue> getRowTotal(Table<String, String, ReportValue> table) {
		Set<String> rowKeys = table.rowKeySet();
		Map<String, ReportValue> rowTotals = new HashMap<String, ReportValue>();
		for (String rowKey : rowKeys) {
			ReportValue total = new ReportValue();
			Collection<ReportValue> values = table.rowMap().get(rowKey).values();
			for (ReportValue value : values) {
				total.setValue(total.getValue().add(value.getValue()));
			}

			rowTotals.put(rowKey, total);
		}
		return rowTotals;
	}

	public Map<String, ReportValue> getColumnTotal(Table<String, String, ReportValue> table) {
		Set<String> columnKeys = table.columnKeySet();
		Map<String, ReportValue> columnTotals = new HashMap<String, ReportValue>();
		for (String columnKey : columnKeys) {
			ReportValue total = new ReportValue();
			Collection<ReportValue> values = table.columnMap().get(columnKey).values();
			for (ReportValue value : values) {
				total.setValue(total.getValue().add(value.getValue()));
			}
			columnTotals.put(columnKey, total);
		}
		return columnTotals;
	}
	
	public ReportValue getGrandTotal(Map<String, ReportValue> totals) {
		ReportValue moneyCell = new ReportValue();
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