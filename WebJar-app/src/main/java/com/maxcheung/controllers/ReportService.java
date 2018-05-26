package com.maxcheung.controllers;

import java.util.List;
import java.util.Map;

import com.maxcheung.models.AccountCashBalanceSummary;
import com.maxcheung.models.DefaultCellValue;

/**
 * Service class to generate account cash balance report.
 */

public interface ReportService {

    /**
     * Send an email through the email service.
     * Generate Account Cash Balance Summary Report
     * @param enquiryDate date of cash balance report.
     * @return account cash summary in original and base currency.
     */
	AccountCashBalanceSummary getAccountCashBalanceSummary();

	/**
     * Given a list of {@link DefaultCellValue} register the profile to the system.
	 * @param accountBalances list of accountBalances by accountId.
	 * @param accountMap a map of account to account group.
	 * @return accountBalances mapped by account group. 
	 */
	List<DefaultCellValue> convertTxnsOrig(List<DefaultCellValue> accountBalances, Map<String, String> accountMap);

}
