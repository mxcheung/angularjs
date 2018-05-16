package guava;

import java.util.List;

import com.google.common.collect.Table;

public interface TableService {

	Table<String, String, AccountBalance> initialise(List<String> rowKeys, String columnKey, AccountBalance value);

	Table<String, String, AccountBalance> listToTree(List<  AccountBalance> accountBalances);


}
