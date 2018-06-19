package com.maxcheung.datatable.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;

public interface DataTableWriter {


	void writeToFile(List<XLWorkSheetReport> worksheets, InputStream excelTemplateFileToRead, OutputStream outputStream)
			throws IOException, OpenXML4JException;

}
