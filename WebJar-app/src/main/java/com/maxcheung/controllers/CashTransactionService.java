package com.maxcheung.controllers;

import java.util.List;

import com.maxcheung.models.CashTransaction;

public interface CashTransactionService {

	List<CashTransaction> getAdjustmentsByCriteria(boolean displayDeleted);

	CashTransaction getCashTransaction(Long cashTransactionId);

	void updateCashTransaction(Long cashTransactionId, CashTransaction cashTransaction);

}
