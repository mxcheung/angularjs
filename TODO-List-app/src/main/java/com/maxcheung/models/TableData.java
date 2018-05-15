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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "id", "universityCourseSeatTable", "accounts"})
public class TableData {

	private String id;
	private Table<String, String, Integer> universityCourseSeatTable;

	private Table<String, String, Integer> accounts;

	public TableData() {
		universityCourseSeatTable = ImmutableTable.<String, String, Integer>builder().put("Mumbai", "Chemical", 120)
				.build();
		accounts = ImmutableTable.<String, String, Integer>builder()
				.put("Account1", "AUD", 120)
				.put("Account1", "USD", 450)
				.put("Account2", "GBP", 120)
				.put("Account2", "JBP", 450)
				.build();
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
	
	
	
	
	
	

}
