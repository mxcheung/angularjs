package com.maxcheung.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BondProxy {

	protected Bond bond;
	private LocalDate today = LocalDate.now();
	

	public BondProxy(Bond bond) {
		super();
		this.bond = bond;
	}

	public Bond getBond() {
		return bond;
	}

	public Long getDaysBetween() {
		return ChronoUnit.DAYS.between(bond.getIssueDate(), bond.getExpiryDate());
	}

	public String getStatus() {
		boolean active = false;
		if (bond.getIssueDate().isBefore(today) && bond.getExpiryDate().isAfter(today)) {
			active = true;
		}
		return active ? "ACTIVE" : "INACTIVE";
	}

}
