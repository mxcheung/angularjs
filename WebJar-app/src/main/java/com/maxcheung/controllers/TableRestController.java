/*
 *  This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.maxcheung.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxcheung.models.Bond;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.TableData;
import com.maxcheung.service.TableCSVExportService;

@RestController
@RequestMapping("/table")
public class TableRestController {

	private static final Logger LOG = LoggerFactory.getLogger(TableRestController.class);

	@Autowired
	private TableCSVExportService tableCSVExportService;

	@RequestMapping(method = RequestMethod.GET)
	public TableData tasks() {
		TableData ret = new TableData();
		ret.setId("1L");
		return ret;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/tableCSV", produces = "text/csv")
	public void getCSV(HttpServletResponse response) throws IOException  {
		LOG.info("Get getCSV");
		DataTable table = getDataTable();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		  String formatDateTime = now.format(formatter);

		String fileName = "abc_" + formatDateTime + ".csv";

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		response.setContentType("text/csv");
		response.setHeader(headerKey, headerValue);
		PrintWriter writer = response.getWriter();
	//	StringWriter writer = new StringWriter();
		tableCSVExportService.tableToCSV(table.getTable(), writer);
	}

	public DataTable getDataTable()  {
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