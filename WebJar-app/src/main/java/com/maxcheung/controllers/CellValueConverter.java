package com.maxcheung.controllers;

import com.maxcheung.models.CellType;
import com.maxcheung.models.CellValue;

public interface CellValueConverter {

	CellValue convert(CellValue source, CellType cellType);

}
