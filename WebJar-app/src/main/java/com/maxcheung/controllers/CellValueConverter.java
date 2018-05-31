package com.maxcheung.controllers;

import com.maxcheung.models.FormatType;
import com.maxcheung.models.CellValue;

public interface CellValueConverter {

	CellValue convert(CellValue source, FormatType formatType);

}
