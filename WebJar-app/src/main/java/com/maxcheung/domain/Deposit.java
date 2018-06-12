package com.maxcheung.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Entity for transactions.
 */
@Entity
@Table(name = "deposit")
public class Deposit implements java.io.Serializable {

    private static final long serialVersionUID = -7565529459957331673L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "starting_amount", precision = 18, scale = 5)
    private BigDecimal startingAmount;

    @Column(name = "open_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate;

    @Formula("((EXTRACT(DAY from close_date - open_date) "
            + "* bank_rate / 100.00 "
            + "/ case when currency in ('AUD', 'GBP', 'KRW', 'SGD', 'HKD', 'TWD', 'NZD', 'MYR', 'INR', 'CNY', 'IDR', 'THB') "
                + "then 365 "
                + "else 360 end "
            + ") + 1 ) "
            + "* starting_amount "
            + "* -1")
    private BigDecimal maturingAmount;

    @Column(name = "close_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate closeDate;

    @Column(name = "central_counter_party")
    private String centralCounterParty;

    @Column(name = "bank_rate", precision = 10, scale = 5)
    private BigDecimal bankRate;

    @Column(name = "client")
    private String client;

    @Column(name = "client_rate", precision = 10, scale = 5)
    private BigDecimal clientRate;

    @Column(name = "type")
    private String type;

    @Column(name = "nostro")
    private String nostro;

    @Column(name = "client_location")
    private String clientLocation;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(BigDecimal startingAmount) {
        this.startingAmount = trim(startingAmount);
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public BigDecimal getMaturingAmount() {
        return maturingAmount;
    }

    public void setMaturingAmount(BigDecimal maturingAmount) {
        this.maturingAmount = trim(maturingAmount);
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public String getCentralCounterParty() {
        return centralCounterParty;
    }

    public void setCentralCounterParty(String centralCounterParty) {
        this.centralCounterParty = centralCounterParty;
    }

    public BigDecimal getBankRate() {
        return bankRate;
    }

    public void setBankRate(BigDecimal bankRate) {
        this.bankRate = trim(bankRate);
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public BigDecimal getClientRate() {
        return clientRate;
    }

    public void setClientRate(BigDecimal clientRate) {
        this.clientRate = clientRate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNostro() {
        return nostro;
    }

    public void setNostro(String nostro) {
        this.nostro = nostro;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object other) {
        return EqualsBuilder.reflectionEquals(this, other);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    private BigDecimal trim(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return value.stripTrailingZeros();
    }
}
