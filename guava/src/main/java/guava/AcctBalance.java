package guava;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AcctBalance  {

	@JsonIgnore
	public String accountId;
	@JsonIgnore
	public String accountGroupId;
	@JsonIgnore
	public String currency;

	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	

	
	public String getAccountGroupId() {
		return accountGroupId;
	}
	public void setAccountGroupId(String accountGroupId) {
		this.accountGroupId = accountGroupId;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@JsonIgnore
	public String getRowKey() {
		return (accountGroupId != null) ? accountGroupId : accountId;
	}
	@JsonIgnore
	public String getColumnKey() {
		return currency;
	}

	
}
