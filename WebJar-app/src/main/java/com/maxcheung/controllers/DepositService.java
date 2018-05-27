package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/**
 * Service for summary or reduction operations for Deposits.
 */
public interface DepositService {

    /**
     * Returns a historical summary of deposit totals, grouped by date and currency.
     * <p>
     * This method will consider the start and end date and produce the totals of the amount deposited
     * for each day between the first and last deposit listed.
     *
     * @return a map of dates to a map of currency pairs and values, representing the amount deposited .
     */
    Map<LocalDate, Map<String, BigDecimal>> summarisePerCurrency();

    /**
     * Returns the maturing amount for the deposit.
     * @param currency the currency of the deposit
     * @param startAmount the starting amount for the deposit
     * @param openDate the open date for the deposit
     * @param closeDate the close date for the deposit
     * @param bankRate the bank rate
     * @return the maturing amount
     */
    BigDecimal getMaturingAmount(String currency, BigDecimal startAmount, LocalDate openDate, LocalDate closeDate, Double bankRate);
}
