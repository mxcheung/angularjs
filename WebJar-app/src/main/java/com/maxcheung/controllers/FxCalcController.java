package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxcheung.models.CalcParams;

@RestController
@RequestMapping("fxcalc")
public class FxCalcController {

	private static final Logger LOG = LoggerFactory.getLogger(FxCalcController.class);

	@RequestMapping(method = RequestMethod.GET, path = "/calc-sell-amount")
	public CalcParams calcSellAmount(CalcParams calcParams) {
		BigDecimal sellAmount = calcParams.getBuyAmount().multiply(calcParams.getFxrate());
		calcParams.setSellAmount(sellAmount);		
		return calcParams;
	}


	@RequestMapping(method = RequestMethod.GET, path = "/calc-buy-amount")
	public CalcParams calcBuyAmount(CalcParams calcParams) {
		BigDecimal buyAmount = calcParams.getSellAmount().divide(calcParams.getFxrate(), 12, RoundingMode.HALF_UP);
		calcParams.setBuyAmount(buyAmount);		
		return calcParams;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/update-buy-amount/{txnId}")
	public CalcParams updateBuyAmount(@PathVariable Long txnId, @RequestBody CalcParams calcParams) {
		BigDecimal buyAmount = calcParams.getSellAmount().divide(calcParams.getFxrate(), 12, RoundingMode.HALF_UP);
		calcParams.setBuyAmount(buyAmount);		
		return calcParams;
	}
	
}