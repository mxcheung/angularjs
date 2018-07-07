package com.maxcheung.service;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.maxcheung.models.CellValue;

@Service
public class TableCSVExportServiceImpl implements TableCSVExportService {

	@Override
	public void tableToCSV(Table<String, String, CellValue> table, Appendable appendable) throws IOException {
		Table<String, String, String> graph = convertToCSVTable(table);
	    CSVFormat.EXCEL.print(appendable).printRecords(
				 graph.rowMap().values().stream()
					.map(x -> x.values()).collect(Collectors.toList()));
	}

	private Table<String, String, String> convertToCSVTable(Table<String, String, CellValue> table) {
		Table<String, String, String> graph = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		table.columnKeySet().forEach(columnKey -> {
			graph.put("", columnKey, columnKey);
			table.rowKeySet().forEach(rowKey -> {
					CellValue cell = table.get(rowKey, columnKey);
					String val = (cell != null) ? cell.getStringCellValue() : "";
					graph.put(rowKey, columnKey, val);
			});
		});
		return graph;
	}

	private Table<String, String, String> convertToCSVTable2(Table<String, String, CellValue> table) {
		Table<String, String, String> graph = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		table.columnKeySet().forEach(colKey -> {
			graph.put("", colKey, colKey);
		});
		table.rowKeySet().forEach(rowKey -> {
			table.columnKeySet().forEach(columnKey -> {
				CellValue cell = table.get(rowKey, columnKey);
				String val = (cell != null) ? cell.getStringCellValue() : "";
				graph.put(rowKey, columnKey, val);
			});
		});
		return graph;
	}
}
