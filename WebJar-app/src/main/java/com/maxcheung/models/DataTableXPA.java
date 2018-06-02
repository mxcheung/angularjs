package com.maxcheung.models;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

public class DataTableXPA  {

	
	private Map<String, Map<String, String>> tableData;
	
	@JsonIgnore
	private Table<String, String, String> table;

	public Table<String, String, String> getTable() {
		return table;
	}

	public void setTable(Table<String, String, String> table) {
		this.table = table;
	}


	public Map<String, Map<String, String>> getTableData() {
		return table.rowMap();
	}

	public void setTableData(Map<String, Map<String, String>> tableData) {
		this.tableData = tableData;
		this.table = table(tableData);
	}

	public static <R, C, V> Table<R, C, V> table(Map<R, Map<C, V>> fromTable)
	{
	    Table<R, C, V> table =  Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
	    for (R rowKey : fromTable.keySet())
	    {
	        Map<C, V> rowMap = fromTable.get(rowKey);
	        for (C columnKey : rowMap.keySet())
	        {
	            V value = rowMap.get(columnKey);
	            table.put(rowKey, columnKey, value);
	        }
	    }
	    return table;
	}	
}
