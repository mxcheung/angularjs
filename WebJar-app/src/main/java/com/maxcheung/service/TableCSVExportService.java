package com.maxcheung.service;

import java.io.IOException;

import com.google.common.collect.Table;
import com.maxcheung.models.CellValue;

public interface TableCSVExportService {

	void tableToCSV(Table<String, String, CellValue> table, Appendable writer) throws IOException;

}
