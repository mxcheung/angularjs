package com.maxcheung.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.Tables;
import com.google.common.collect.TreeBasedTable;
import com.maxcheung.models.CellType;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.DataTable;
import com.maxcheung.models.CellValueDefault;
import com.maxcheung.models.CellValueHighChartBar;
import com.maxcheung.models.CellValueHighChartPie;

@Service
public class DataTableServiceImpl implements DataTableService {

	DozerBeanMapper mapper = new DozerBeanMapper();;
	 
	
    @Override
    public List<CellValue> createDefaults(Set<String> rowKeys, String columnKey, BigDecimal value) {
        List<CellValue> monies = new ArrayList<CellValue>();
        for (String rowKey : rowKeys) {
            CellValue moneyCell = new CellValueDefault();
            moneyCell.setRowKey(rowKey);
            moneyCell.setColumnKey(columnKey);
            moneyCell.setValue(value);
            monies.add(moneyCell);
        }
        return monies;
    }

    /* (non-Javadoc)
     *   Get rows 
     * @see com.abnamro.clearing.treasury.cashmanagement.service.DataTableService#filterValuesByRowIds(java.util.List, java.util.Set)
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
        moneyCell.setValue(totals.values().stream().map(x -> x.getValue()).reduce(BigDecimal.ZERO, BigDecimal::add));
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

    public Table<String, String, CellValue>  transformTable(Table<String, String, CellValue> source, CellType cellType) {
	 Table<String, String, CellValue> dest = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new) ; 
		for (Cell<String, String, CellValue> cell: source.cellSet()){
			dest.put(cell.getRowKey(), cell.getColumnKey(), convert(cell.getValue(), cellType));
		};
        return dest;
    }

    public CellValue  convert(CellValue source,  CellType cellType) {
    	CellValue dest = null;
    	if ( cellType == CellType.HIGHCHARTPIE) {
    		return mapper.map(source, CellValueHighChartPie.class);
    	} else  if ( cellType == CellType.HIGHCHARTBAR) {
    		return mapper.map(source, CellValueHighChartBar.class);
    	}
        return dest;
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
            CellValue existingMoneyCell = getNewBalance(table, moneyCell);
            table.put(moneyCell.getRowKey(), moneyCell.getColumnKey(), existingMoneyCell);
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
        if (existingMoneyCell == null) {
        	if (moneyCell.getCellType() == CellType.DEFAULT) {
        		
        		existingMoneyCell = new CellValueDefault();
        	}
        	
        }
        existingMoneyCell.setValue(existingMoneyCell.getValue().add(moneyCell.getValue()));
        return existingMoneyCell;
    }
}
