package com.maxcheung.service;

import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.maxcheung.controllers.CellValueConverter;
import com.maxcheung.controllers.CellValueConverterImpl;
import com.maxcheung.controllers.DataTableService;
import com.maxcheung.controllers.DataTableServiceImpl;
import com.maxcheung.models.Bond;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.DataTable;

public class TableCSVExportServiceTest {

	private TableCSVExportService tableCSVExportService;

	@Before
	public void setup() throws Exception {

		tableCSVExportService = new TableCSVExportServiceImpl();

	}

	@Test
	public void testWriteToCsv() throws Exception {
		Appendable out = new StringBuilder();
		DataTable table = getDataTable();
		tableCSVExportService.tableToCSV(table.getTable(), out);
	}

	public DataTable getDataTable() throws Exception {
		CellValueConverter cellValueConverter = new CellValueConverterImpl();
		DataTableService dataTableService = new DataTableServiceImpl(cellValueConverter);

		Bond bond = new Bond();
		bond.setId("id1");
		bond.setName("James O'brean");
		bond.setSalary(BigDecimal.valueOf(2000));
		bond.setAge(18L);
		bond.setDob(LocalDate.of(2000, 01, 28));

		Bond bond1 = new Bond();
		bond1.setId("id2");
		bond1.setName("Mary , Ann");
		bond1.setSalary(BigDecimal.valueOf(1200));
		bond1.setAge(35L);

		Bond bond2 = new Bond();
		bond2.setId("id2");
		bond2.setName("Mark");

		List<CellValue> moneyCells = new ArrayList<>();

		// moneyCells.add(new CellValueDefault(bond2.getId(), "Salary",
		// bond2.getSalary()));
		// moneyCells.add(new CellValueDefault(bond.getId(), "Sales", BigDecimal.TEN));
		// moneyCells.add(new CellValueDefault(bond.getId(), "Sales", BigDecimal.TEN));

		moneyCells.add(new CellValueDefault(bond.getId(), "Name", bond.getName()));
		moneyCells.add(new CellValueDefault(bond.getId(), "Salary", bond.getSalary()));
		moneyCells.add(new CellValueDefault(bond.getId(), "Age", bond.getAge().toString()));
		moneyCells.add(new CellValueDefault(bond.getId(), "Dob", bond.getDob().toString()));
		moneyCells.add(new CellValueDefault(bond.getId(), "Sales", BigDecimal.TEN));
		moneyCells.add(new CellValueDefault(bond.getId(), "Sales", BigDecimal.TEN));
		moneyCells.add(new CellValueDefault(bond1.getId(), "Name", bond1.getName()));
		moneyCells.add(new CellValueDefault(bond1.getId(), "Salary", bond1.getSalary()));
		moneyCells.add(new CellValueDefault(bond1.getId(), "Age", bond1.getAge().toString()));
		moneyCells.add(new CellValueDefault(bond1.getId(), "Sales", BigDecimal.ONE));
		moneyCells.add(new CellValueDefault(bond2.getId(), "Salary", bond2.getSalary()));
		DataTable sales = dataTableService.createDataTableFromList(moneyCells);
		return sales;

	}

}