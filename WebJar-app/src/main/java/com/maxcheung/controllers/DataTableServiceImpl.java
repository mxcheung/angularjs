package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.Tables;
import com.google.common.collect.TreeBasedTable;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.FormatType;
import com.maxcheung.util.spel.SpelUtil;


@Service
public class DataTableServiceImpl implements DataTableService {

	// DozerBeanMapper mapper = new DozerBeanMapper();
	private final CellValueConverter converter;
	
    private ExpressionParser parser;

    private final StandardEvaluationContext itemContext;

	@Autowired
	public DataTableServiceImpl(CellValueConverter converter) {
		this.converter = converter;
        itemContext = SpelUtil.getContext();
        parser = new SpelExpressionParser();

	}

	@Override
	public List<CellValue> createDefaults(Set<String> rowKeys, String columnKey, BigDecimal value) {
		List<CellValue> monies = new ArrayList<CellValue>();
		for (String rowKey : rowKeys) {
			CellValue moneyCell = new CellValueDefault();
			moneyCell.setRowKey(rowKey);
			moneyCell.setColumnKey(columnKey);
			moneyCell.setCellValue(value);
			monies.add(moneyCell);
		}
		return monies;
	}

	/*
	 * (non-Javadoc) Get rows
	 * 
	 * @see com.abnamro.clearing.treasury.cashmanagement.service.DataTableService#
	 * filterValuesByRowIds(java.util.List, java.util.Set)
	 */
	@Override
	public List<CellValue> filterValuesByRowIds(List<CellValue> moneys, Set<String> rowIds) {
		List<CellValue> filteredAccounts = moneys.stream().filter(x -> rowIds.contains(x.getRowKey()))
				.collect(Collectors.toList());
		return filteredAccounts;

	}

	@Override
	public Map<String, CellValue> getRowTotal(Table<String, String, CellValue> table) {
		Set<String> rowKeys = table.rowKeySet();
		Map<String, CellValue> rowTotals = new HashMap<String, CellValue>();
		for (String rowKey : rowKeys) {
			rowTotals.put(rowKey, calcTotal(table.row(rowKey)));
		}
		return rowTotals;
	}

	// get row where colX = 1
	//@Override
	public Map<String, Map<String, CellValue>> filterByColValue(Table<String, String, CellValue> table, String column, String colValue) {
		Map<String, Map<String, CellValue>> rowMap = table.rowMap();
		Set<String> rowKeys = table.rowKeySet();
		for (String rowKey : rowKeys) {
			 String cc = table.get(rowKey, column).getStringCellValue();
			if ( cc != colValue) {
				rowMap.remove(rowKey);
			}
		}
		return rowMap;
	}

	@Override
	public List<BigDecimal> convertToListAmounts(Table<String, String, CellValue> table) {
		List<BigDecimal> amounts = table.values().stream().map(x -> x.getBigDecimalValue())
				.collect(Collectors.toList());
		return amounts;

	}

	@Override
	public Map<String, CellValue> getColumnTotal(Table<String, String, CellValue> table) {
		Set<String> columnKeys = table.columnKeySet();
		Map<String, CellValue> columnTotals = new HashMap<String, CellValue>();
		for (String columnKey : columnKeys) {
			columnTotals.put(columnKey, calcTotal(table.column(columnKey)));
		}
		return columnTotals;
	}

	@Override
	public CellValue calcTotal(Map<String, CellValue> totals) {
		CellValue moneyCell = new CellValueDefault();
		moneyCell.setCellValue(
				totals.values().stream().map(x -> x.getBigDecimalValue()).reduce(BigDecimal.ZERO, BigDecimal::add));
		return moneyCell;
	}

	@Override
	public List<CellValue> replaceRowKeys(List<CellValue> moneys, Map<String, String> rowIdMap) {
		for (CellValue money : moneys) {
			String mappedVal = rowIdMap.get(money.getRowKey());
			if (mappedVal != null) {
				money.setRowKey(mappedVal);
			}
		}
		return moneys;
	}

	@Override
	public DataTable createDataTableFromList(List<CellValue> moneyCells) {
		DataTable moneyTable = new DataTable();
		Table<String, String, CellValue> table = populateTable(moneyCells);
		moneyTable.setTable(table);
		moneyTable.setRowTotals(getRowTotal(table));
		moneyTable.setColumnTotals(getColumnTotal(table));
		moneyTable.setGrandTotal(calcTotal(moneyTable.getRowTotals()));

		return moneyTable;
	}

	@Override
	public Table<String, String, CellValue> transformTable(Table<String, String, CellValue> source,
			FormatType formatType) {
		Table<String, String, CellValue> dest = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		for (Cell<String, String, CellValue> cell : source.cellSet()) {
			dest.put(cell.getRowKey(), cell.getColumnKey(), convert(cell.getValue(), formatType));
		}
		;
		return dest;
	}

	public CellValue convert(CellValue source, FormatType formatType) {
		return converter.convert(source, formatType);
		// CellValue dest = null;
		// if ( cellType == CellType.HIGHCHARTPIE) {
		// return mapper.map(source, CellValueHighChartPie.class);
		// } else if ( cellType == CellType.HIGHCHARTBAR) {
		// return mapper.map(source, CellValueHighChartBar.class);
		// }
		// return dest;
	}

	public DataTable createMoneyTable(Table<String, String, CellValue> table) {
		DataTable moneyTable = new DataTable();
		moneyTable.setTable(table);
		moneyTable.setRowTotals(getRowTotal(table));
		moneyTable.setColumnTotals(getColumnTotal(table));
		return moneyTable;
	}

	public Table<String, String, CellValue> populateTable(List<CellValue> moneyCells) {
		Table<String, String, CellValue> table = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
		for (CellValue moneyCell : moneyCells) {
			switch (moneyCell.getCellType()) {
			case CELLTYPE_BIGDECIMAL:
				table.put(moneyCell.getRowKey(), moneyCell.getColumnKey(), getNewBalance(table, moneyCell));
				break;
			default:
				table.put(moneyCell.getRowKey(), moneyCell.getColumnKey(), moneyCell);
			}
		}
		return table;
	}

	public Table<String, String, CellValue> initialise(List<String> rowKeys, String columnKey, CellValue value) {
		Table<String, String, CellValue> table = TreeBasedTable.create();
		
	
		
		for (String rowKey : rowKeys) {
			table.put(rowKey, columnKey, value);
		}
		return table;
	}

	private CellValue getNewBalance(Table<String, String, CellValue> table, CellValue moneyCell) {
		CellValue existingMoneyCell = table.get(moneyCell.getRowKey(), moneyCell.getColumnKey());
		if (! table.contains(moneyCell.getRowKey(), moneyCell.getColumnKey())) {
			existingMoneyCell = new CellValueDefault();
		}
		existingMoneyCell.setCellValue(existingMoneyCell.getBigDecimalValue().add(moneyCell.getBigDecimalValue()));
		return existingMoneyCell;
	}
	
	
	public static <R, C, V> Table<R, C, V> table(Map<R, Map<C, V>> fromTable)
	{
	    Table<R, C, V> table =  Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);
	    for (R rowKey : fromTable.keySet())
	    {
	        Map<C, V> rowMap = fromTable.get(rowKey);
	        for (C columnKey : rowMap.keySet())
	        {
	            V value = rowMap.get(columnKey);
	            table.put(rowKey, columnKey, value);
	        }
	    }
	    return table;
	}

}
