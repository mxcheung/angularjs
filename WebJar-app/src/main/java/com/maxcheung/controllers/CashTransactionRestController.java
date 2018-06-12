package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxcheung.models.CashTransaction;

@RestController
@RequestMapping("cash-transaction")
public class CashTransactionRestController {

	@Autowired
	CashTransactionService cashTransactionService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/list-by-criteria")
	public List<CashTransaction> getAdjustmentsByCriteria(@RequestParam("display-deleted") boolean displayDeleted) {
		List<CashTransaction> cashtransactions =  cashTransactionService.getAdjustmentsByCriteria(displayDeleted);
		return cashtransactions;
	}
	
    @RequestMapping(method = RequestMethod.GET, path = "/{cashTransactionId}")
    public CashTransaction getDepositForId(@PathVariable Long cashTransactionId) {
        return cashTransactionService.getCashTransaction(cashTransactionId);
    }
    
    
    @RequestMapping(method = RequestMethod.POST, path = "/{cashTransactionId}")
    public void updateCashTransaction(@PathVariable Long cashTransactionId, @RequestBody CashTransaction cashTransaction) {
    	cashTransactionService.updateCashTransaction(cashTransactionId,cashTransaction );
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