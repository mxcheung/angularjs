package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.maxcheung.models.CashTransaction;

@Service
public class CashTransactionServiceImpl implements CashTransactionService {

	private CashTransaction cashtransaction1;
	private CashTransaction cashtransaction2;
	private Map<Long, CashTransaction> cashtransactionMap;

	public CashTransactionServiceImpl() {
		cashtransaction1 = getCashTransaction();
		cashtransaction2 = getCashTransaction();
		cashtransaction2.setId(2L);
		cashtransaction2.setAccount("account2");
		cashtransaction2.setAmount(BigDecimal.ONE);
		cashtransactionMap =  new HashMap<Long, CashTransaction>();
		cashtransactionMap.put(1L, cashtransaction1);
		cashtransactionMap.put(2L, cashtransaction2);
	}

	@Override
	public List<CashTransaction> getAdjustmentsByCriteria(boolean displayDeleted) {
		List<CashTransaction> cashtransactions = new ArrayList<CashTransaction>();
		cashtransactions.add(cashtransaction1);
		cashtransactions.add(cashtransaction2);
		return cashtransactions;
	}

	@Override
	public CashTransaction getCashTransaction(Long cashTransactionId) {
		CashTransaction cashtransaction = getCashTransaction();
		cashtransaction.setId(cashTransactionId);
		return cashtransactionMap.getOrDefault(cashTransactionId, cashtransaction1);
	}

	


	@Override
	public void updateCashTransaction(Long cashTransactionId, CashTransaction cashTransaction) {
		CashTransaction cashtransaction  =getCashTransaction(cashTransactionId);
		cashtransaction.setVerifiedBy("verifier1");
		
	}

	private CashTransaction getCashTransaction() {
		CashTransaction cashtransaction = new CashTransaction();
		cashtransaction.setId(1L);
		cashtransaction.setAccount("account1");
		cashtransaction.setTransactionDate(LocalDate.of(2017, 05, 10));
		cashtransaction.setCurrency("USD");
		cashtransaction.setAmount(BigDecimal.TEN);
		cashtransaction.setInputBy("user1");
		cashtransaction.setVerifiedBy("user2");
		return cashtransaction;
	}

}