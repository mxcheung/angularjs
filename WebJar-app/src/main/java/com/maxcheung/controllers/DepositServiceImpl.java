package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.maxcheung.domain.Deposit;

@Service
public class DepositServiceImpl implements DepositService {


    /**
     * Returns a historical summary of deposit totals, grouped by date and currency.
     * <p>
     * This method will consider the start and end date and produce the totals of the amount deposited
     * for each day between the first and last deposit listed.
     *
     * @return a map of dates to a map of currency pairs and values, representing the amount deposited .
     */
    @Override
    public Map<LocalDate, Map<String, BigDecimal>> summarisePerCurrency() {
        // Keep track of all the currencies which exist in the system, to ensure that days where a given currency isn't active are
        // recorded as zero.
        Set<String> seenCurrencies = new HashSet<>();

        Map<LocalDate, Map<String, BigDecimal>> summaries = new HashMap<>();
     //   List<Deposit> deposits = repository.findAll();
        List<Deposit> deposits = new ArrayList<Deposit>();
        for (Deposit deposit: deposits) {
            if (!seenCurrencies.contains(deposit.getCurrency())) {
                seenCurrencies.add(deposit.getCurrency());
            }

            // For each date between the open and close date, add the value of each record
            // existant between the open and close date.
            for (LocalDate currentDate = deposit.getOpenDate();
                    currentDate.isBefore(deposit.getCloseDate());
                    currentDate = currentDate.plusDays(1)) {

                // Populate the date and currency if either don't exist
                populateDateAndCurrencyIfMissing(summaries, currentDate, deposit.getCurrency());

                // Add the record to the total
                Map<String, BigDecimal> currencyMap = summaries.get(currentDate);
                BigDecimal newAmount = currencyMap.get(deposit.getCurrency()).add(deposit.getStartingAmount());

                currencyMap.put(deposit.getCurrency(), newAmount);
            }
        }

        // Populate any currency for any date in the range where there is no value as zero.
        for (LocalDate currentDate = Collections.min(summaries.keySet());
                    !currentDate.isAfter(Collections.max(summaries.keySet()));
                    currentDate = currentDate.plusDays(1)) {

            for (String currency: seenCurrencies) {
                populateDateAndCurrencyIfMissing(summaries, currentDate, currency);
            }
        }

        return summaries;
    }

    /**
     * Convinience method to add the date and/or currency to a two-dimentional map - used for summarizing deposits.
     *
     * @param summaries the target map to add the date and currency to
     * @param date the date to add if missing
     * @param currency the currency to add if missing
     */
    private void populateDateAndCurrencyIfMissing(Map<LocalDate, Map<String, BigDecimal>> summaries,
            LocalDate date,
            String currency) {

        if (!summaries.containsKey(date)) {
            summaries.put(date, new HashMap<>());
        }

        Map<String, BigDecimal> summary = summaries.get(date);
        if (!summary.containsKey(currency)) {
            summary.put(currency,  BigDecimal.ZERO);
        }
    }

    /*
     * Method to calculate maturing amount. The list of currencies with 365 days is taken from MICS and expected to be constant.
     * @param currency the currency of the deposit
     * @param startAmount the starting amount for the deposit
     * @param openDate the open date for the deposit
     * @param closeDate the close date for the deposit
     * @param bankRate the bank rate
     */
    @Override
    public BigDecimal getMaturingAmount(String currency, BigDecimal startAmount, LocalDate openDate, LocalDate closeDate, Double bankRate) {
        List<String> currencies = Arrays.asList("AUD", "GBP", "KRW", "SGD", "HKD", "TWD", "NZD", "MYR", "INR", "CNY", "IDR", "THB");
        int totalDays;
        BigDecimal maturingAmount;
        if (currency != null && currencies.contains(currency)) {
            totalDays = 365;
        } else {
            totalDays = 360;
        }
        int intervalBetweenDates = Period.between(openDate, closeDate).getDays();
        maturingAmount = startAmount.add(startAmount.divide(new BigDecimal(totalDays), 2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(intervalBetweenDates)).multiply(new BigDecimal(bankRate / 100.00)));
        maturingAmount = maturingAmount.setScale(2, RoundingMode.HALF_EVEN);
        return maturingAmount.negate();
    }
}
