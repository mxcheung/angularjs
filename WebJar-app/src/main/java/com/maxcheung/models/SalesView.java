package com.maxcheung.models;

import static java.util.stream.Collectors.groupingBy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.maxcheung.domain.Sales;

public class SalesView {

	private String account;

	private LocalDate transactionDate;

	private Map<String, Map<String, BigDecimal>> amountByType;

	public SalesView(LocalDate transactionDate, List<Sales> sales) {
		super();
		this.transactionDate = transactionDate;
		this.amountByType = convert(sales);
	}

	public SalesView(String account, LocalDate transactionDate, List<Sales> sales) {
		super();
		this.account = account;
		this.transactionDate = transactionDate;
		this.amountByType = convert(sales);
	}

	private Map<String, Map<String, BigDecimal>> convert(List<Sales> sales) {
		Map<String, Map<String, BigDecimal>> amountMap = new HashMap<String, Map<String, BigDecimal>>();
		Map<String, List<Sales>> grpByType = sales.stream().collect(groupingBy(Sales::getType));
		grpByType.forEach((type, v) -> {
			Map<String, BigDecimal> amountByCurrency = v.stream()
					.collect(Collectors.toMap(Sales::getCurrency, Sales::getAmount));
			amountMap.put(type, amountByCurrency);
		});
		return amountMap;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Map<String, Map<String, BigDecimal>> getAmountByType() {
		return amountByType;
	}

	public void setAmountByType(Map<String, Map<String, BigDecimal>> amountByType) {
		this.amountByType = amountByType;
	}

}
