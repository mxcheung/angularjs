/*
 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.maxcheung.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class CalcParams {

	private Long id;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate tradeDate;
	
	private LocalDate expiryDate;
	
	private BigDecimal buyAmount;
	private BigDecimal sellAmount;
	private BigDecimal fxrate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
	public BigDecimal getBuyAmount() {
		return buyAmount;
	}
	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}
	public BigDecimal getSellAmount() {
		return sellAmount;
	}
	public void setSellAmount(BigDecimal sellAmount) {
		this.sellAmount = sellAmount;
	}
	public BigDecimal getFxrate() {
		return fxrate;
	}
	public void setFxrate(BigDecimal fxrate) {
		this.fxrate = fxrate;
	}
	
	
	

	
	
}
