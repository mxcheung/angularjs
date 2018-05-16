package guava;

import java.math.BigDecimal;

public class AccountBalance extends AcctBalance  {


	public BigDecimal accountAmt = BigDecimal.ZERO;
	public BigDecimal accountAmtOther = BigDecimal.ZERO;
	
	public BigDecimal getAccountAmt() {
		return accountAmt;
	}
	public void setAccountAmt(BigDecimal accountAmt) {
		this.accountAmt = accountAmt;
	}
	
	
	
	public BigDecimal getAccountAmtOther() {
		return accountAmtOther;
	}
	public void setAccountAmtOther(BigDecimal accountAmtOther) {
		this.accountAmtOther = accountAmtOther;
	}

	
	
	

}
