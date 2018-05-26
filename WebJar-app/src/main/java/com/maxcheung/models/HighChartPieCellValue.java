package com.maxcheung.models;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HighChartPieCellValue extends AbsCellValue   {

	
	@Override
	public CellType getCellType() {
		return CellType.HIGHCHARTPIE;
	}

	@JsonProperty("name")
	public String getName() {
		return getRowKey();
	}
	
	@JsonProperty("y")
	public BigDecimal getY() {
		return getValue();
	}
	
	
//    name: "Firefox",
 //   y: 10.38

	
}
