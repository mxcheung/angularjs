package com.maxcheung.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxcheung.models.AccountCashBalanceSummary;

@RestController
@RequestMapping("report")
public class ReportRestController {

	private static final Logger LOG = LoggerFactory.getLogger(ReportRestController.class);

	@Autowired
	private ReportCashBalanceService reportCashBalanceService;


	@RequestMapping( method = RequestMethod.GET, path = "/cash-balance")
	public AccountCashBalanceSummary getAccountCashBalanceSummary() {
		LOG.info("Get AccountCashBalanceSummary");
		AccountCashBalanceSummary accountCashBalanceSummary = reportCashBalanceService.getAccountCashBalanceSummary();
		return accountCashBalanceSummary;
	}

}