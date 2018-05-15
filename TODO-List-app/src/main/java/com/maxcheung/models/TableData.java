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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "universityCourseSeatTable", "accounts", "rowTotal", "colTotal" })
public class TableData {

	private String id;
	private Table<String, String, Integer> universityCourseSeatTable;

	private Table<String, String, Integer> accounts;

	private Map<String, Integer> colTotal;
	private Map<String, Integer> rowTotal;

	public TableData() {
		universityCourseSeatTable = ImmutableTable.<String, String, Integer>builder().put("Mumbai", "Chemical", 120)
				.build();
		accounts = ImmutableTable.<String, String, Integer>builder().put("Account1", "AUD", 120)
				.put("Account1", "USD", 450).put("Account2", "AUD", 120).put("Account2", "GBP", 120)
				.put("Account2", "JBP", 450).build();

		colTotal = getColTotals();
		rowTotal = getRowTotals();

	}

	private Map<String, Integer> getColTotals() {
		Map<String, Integer> colTotal = new HashMap<String, Integer>();
		accounts.columnKeySet().forEach(k -> {
			Map<String, Integer> data = accounts.columnMap().get(k);
			colTotal.put(k, data.values().stream().mapToInt(Number::intValue).sum());
		});

		return colTotal;
	}

	private Map<String, Integer> getRowTotals() {
		Map<String, Integer> rowTotal = new HashMap<String, Integer>();
		Set<String> rowkeys = accounts.rowKeySet();
		for (String key : rowkeys) {
			Map<String, Integer> data = accounts.rowMap().get(key);
			int sum = data.values().stream().reduce(0, Integer::sum);
			rowTotal.put(key, sum);
		}
		return rowTotal;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Table<String, String, Integer> getUniversityCourseSeatTable() {
		return universityCourseSeatTable;
	}

	public void setUniversityCourseSeatTable(Table<String, String, Integer> universityCourseSeatTable) {
		this.universityCourseSeatTable = universityCourseSeatTable;
	}

	public Table<String, String, Integer> getAccounts() {
		return accounts;
	}

	public void setAccounts(Table<String, String, Integer> accounts) {
		this.accounts = accounts;
	}

	public Map<String, Integer> getColTotal() {
		return colTotal;
	}

	public void setColTotal(Map<String, Integer> colTotal) {
		this.colTotal = colTotal;
	}

	public Map<String, Integer> getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(Map<String, Integer> rowTotal) {
		this.rowTotal = rowTotal;
	}

}
