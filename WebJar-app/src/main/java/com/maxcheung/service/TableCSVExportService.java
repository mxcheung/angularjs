package com.maxcheung.service;

import java.io.IOException;

import com.google.common.collect.Table;
import com.maxcheung.models.CellValue;

public interface TableCSVExportService {


	Appendable tableToCSV(Appendable appendable, Table<String, String, CellValue> table) throws IOException;

//	Appendable tableToCSV(Table<String, String, CellValue> table);

}
