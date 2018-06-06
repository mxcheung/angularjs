package com.maxcheung.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	
    @Before
    public void setup() throws Exception {
    }

    @Test
    public void givenCurrencyCode_whenString_thanExist() {
        CurrencyUnit usd = Monetary.getCurrency("USD");
     
        assertNotNull(usd);
        assertEquals(usd.getCurrencyCode(), "USD");
        assertEquals(usd.getNumericCode(), 840);
        assertEquals(usd.getDefaultFractionDigits(), 2);
    }

    @Test
    public void givenAmount_whenConversion_thenNotNull() {
        MonetaryAmount oneDollar = Monetary.getDefaultAmountFactory().setCurrency("USD")
          .setNumber(1).create();
     
        CurrencyConversion conversionEUR = MonetaryConversions.getConversion("EUR");
     
        MonetaryAmount convertedAmountUSDtoEUR = oneDollar.with(conversionEUR);
     
        assertEquals("USD 1", oneDollar.toString());
        assertNotNull(convertedAmountUSDtoEUR);
    }
}
