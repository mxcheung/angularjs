package com.maxcheung.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxcheung.domain.Sales;
import com.maxcheung.models.SalesView;
import com.maxcheung.service.SalesService;

@RestController
@RequestMapping("sales")
public class SalesController {

	private static final Logger LOG = LoggerFactory.getLogger(SalesController.class);

	@Autowired
	private SalesService salesService;

    @RequestMapping(value = "/getSales", method = RequestMethod.GET)
    public List<Sales> getSales() {
    	LOG.info(" getSales");
		return salesService.getSales();
    }

    @RequestMapping(value = "/getSalesByDate", method = RequestMethod.GET)
    public List<SalesView> getSalesByDate() {
    	LOG.info(" getSales");
		return salesService.getSalesByDate();
    }

    @RequestMapping(value = "/getSalesByAccountAndDate", method = RequestMethod.GET)
    public List<SalesView> getSalesByAccountAndDate() {
    	LOG.info(" getSalesByAccountAndDate");
		return salesService.getSalesByAccountAndDate();
    }

    @RequestMapping(value = "/getSalesByDateAndAccount", method = RequestMethod.GET)
    public List<SalesView> getSalesByDateAndAccount() {
    	LOG.info(" getSalesByDateAndAccount");
		return salesService.getSalesByDateAndAccount();
    }

    @RequestMapping(value = "/loadSales", method = RequestMethod.GET)
    public void loadSales() {
    	LOG.info(" loadSales");
		salesService.loadSales();
    }



}