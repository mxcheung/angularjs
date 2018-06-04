package com.maxcheung.models;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

public class BondProxyTest {
	
    @Before
    public void setup() throws Exception {
    }

    @Test
    public void testGetDaysBetween() {
    	Bond bond = new Bond();
    	bond.setIssueDate(LocalDate.of(2017, 9, 30));
    	bond.setExpiryDate(LocalDate.of(2018, 9, 30));
    	BondProxy bondProxy = new BondProxy(bond);
    	assertEquals(365L,bondProxy.getDaysBetween().longValue());
    	assertEquals("ACTIVE",bondProxy.getStatus());
    }

  

}
