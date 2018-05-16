package guava;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.collect.Table;

public class TableTest {

	private TableService tableService;
	
	private List<String> accounts;
	private ObjectMapper mapper;
    @Before
    public void setUp() {
    	tableService = new TableServiceImpl();
    	mapper = new ObjectMapper();
    	mapper.registerModule(new GuavaModule());
    	accounts = getAccounts();
    }

    
    @Test
    public void shouldInitialiseTree() throws JsonProcessingException {
    	AccountBalance accountBalance = new AccountBalance();
    	accountBalance.setCurrency("AUD");
    	accountBalance.setAccountAmt(BigDecimal.ONE);
		Table<String, String, AccountBalance> table = tableService.initialise(accounts, "AUD",accountBalance );
		assertEquals(2,table.size());
	//	String json = mapper.writeValueAsString(table);
    	
    }
    
    @Test
    public void shouldConvertToTree() throws JsonProcessingException {
    	List<  AccountBalance> accountBalances = new ArrayList< AccountBalance>();
    	AccountBalance accountBalance1 = new AccountBalance();
    	accountBalance1.setAccountId("Account1");
    	accountBalance1.setAccountGroupId("AccountGrp1");
    	accountBalance1.setCurrency("AUD");
    	accountBalance1.setAccountAmt(BigDecimal.ONE);
    	accountBalance1.setAccountAmtOther(BigDecimal.TEN);
    	AccountBalance accountBalance2 = new AccountBalance();
    	accountBalance2.setAccountId("Account2");
    	accountBalance2.setAccountGroupId("AccountGrp1");
    	accountBalance2.setCurrency("AUD");
    	accountBalance2.setAccountAmt(BigDecimal.ONE);
    	accountBalance2.setAccountAmtOther(BigDecimal.TEN);
		accountBalances.add(accountBalance1 );
		accountBalances.add(accountBalance2 );
		Table<String, String, AccountBalance> table = tableService.listToTree(accountBalances);
		assertEquals(1,table.size());
		assertEquals(BigDecimal.valueOf(2),table.get("AccountGrp1", "AUD").getAccountAmt());
		assertEquals(BigDecimal.valueOf(20),table.get("AccountGrp1", "AUD").getAccountAmtOther());
//		String json = mapper.writeValueAsString(table);
    	
    }


	private List<String> getAccounts() {
		List<String> accounts = new ArrayList<String>(); 
    	accounts.add("Account1");
    	accounts.add("Account2");
		return accounts;
	}
 
}
