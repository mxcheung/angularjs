package com.maxcheung.controllers;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.maxcheung.models.Bond;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.DataTable;

public class DataTableServiceTest {

	@Test
	public void shouldLogValidationErrors() throws Exception {
		CellValueConverter cellValueConverter = new CellValueConverterImpl();
		DataTableService dataTableService = new DataTableServiceImpl(cellValueConverter);

		Bond bond = new Bond();
		bond.setId("id1");
		bond.setName("James");
		bond.setSalary(BigDecimal.valueOf(2000));
		bond.setAge(18L);
		bond.setDob(LocalDate.of(2000, 01, 28));

		Bond bond1 = new Bond();
		bond1.setId("id2");
		bond1.setName("Mary");
		bond1.setSalary(BigDecimal.valueOf(1200));
		bond1.setAge(35L);

		Bond bond2 = new Bond();
		bond2.setId("id2");
		bond2.setName("Mark");



		List<CellValue> moneyCells = new ArrayList<>();

//		moneyCells.add(new CellValueDefault(bond2.getId(), "Salary", bond2.getSalary()));
//		moneyCells.add(new CellValueDefault(bond.getId(), "Sales", BigDecimal.TEN));
//		moneyCells.add(new CellValueDefault(bond.getId(), "Sales", BigDecimal.TEN));

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
		CellValue cellValue = sales.getTable().get(bond.getId(), "Sales");
		assertEquals(BigDecimal.valueOf(20),cellValue.getBigDecimalValue());

	}

}
