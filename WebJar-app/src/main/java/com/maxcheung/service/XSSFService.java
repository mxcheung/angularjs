package com.maxcheung.service;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.KeyValue;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Workbook;

public interface XSSFService {

	 Map<String, List<KeyValue>>  extractData(String clientCd, InputStream excelFileToRead) throws IOException, OpenXML4JException;

	Workbook writeToExcel();

}