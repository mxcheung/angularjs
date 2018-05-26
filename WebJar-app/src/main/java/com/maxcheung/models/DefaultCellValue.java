package com.maxcheung.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultCellValue extends AbsCellValue   {


	@Override
	public CellType getCellType() {
		return CellType.DEFAULT;
	}

	
	@JsonProperty("valueAmt")
	@Override
	public BigDecimal getValue() {
		return super.getValue();
	}
	
}
