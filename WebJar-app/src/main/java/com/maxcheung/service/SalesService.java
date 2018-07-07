package com.maxcheung.service;

import java.util.List;

import com.maxcheung.domain.Sales;
import com.maxcheung.models.SalesView;

public interface SalesService {

	void loadSales();

	List<SalesView> getSalesByDate();

	List<SalesView> getSalesByAccountAndDate();

	List<SalesView> getSalesByDateAndAccount();

	List<Sales> getSales();
	
}
