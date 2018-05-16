package guava;

import java.util.List;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

public class TableServiceImpl implements TableService {

	
	
	@Override
	public Table<String, String, AccountBalance> initialise(List< String> rowKeys , String columnKey,  AccountBalance value) {
		Table<String, String, AccountBalance> table = TreeBasedTable.create();
		for ( String  rowKey : rowKeys) {
			table.put(rowKey, columnKey, value);
		}
		return table;
	}
	
	@Override
	public Table<String, String, AccountBalance> listToTree(List< AccountBalance> accountBalances) {
		Table<String, String, AccountBalance> table = TreeBasedTable.create();
		for (  AccountBalance accountBalance : accountBalances) {
			AccountBalance existingAccountBalance = getNewBalance(table, accountBalance);
			table.put(accountBalance.getRowKey(), accountBalance.getColumnKey(), existingAccountBalance);
		}
		return table;
	}

	private AccountBalance getNewBalance(Table<String, String, AccountBalance> table, AccountBalance accountBalance) {
		AccountBalance existingAccountBalance = table.get(accountBalance.getRowKey(), accountBalance.getColumnKey());
		if (existingAccountBalance == null) {
			existingAccountBalance = new AccountBalance();
		}
		existingAccountBalance.setAccountAmt(existingAccountBalance.getAccountAmt().add(accountBalance.getAccountAmt()));
		existingAccountBalance.setAccountAmtOther(existingAccountBalance.getAccountAmtOther().add(accountBalance.getAccountAmtOther()));
		return existingAccountBalance;
	}
}
