package com.qsoft.bankaccount.entity;

public class BankAccountDTO {
	private String accountNumber;
	private float balance;
	private long openTimestamp;
	private String description;

	public BankAccountDTO(String accountNumber) {
		super();
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the balance
	 */
	public float getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(float balance) {
		this.balance = balance;
	}

	/**
	 * @return the openTimestamp
	 */
	public long getOpenTimestamp() {
		return openTimestamp;
	}

	/**
	 * @param openTimestamp
	 *            the openTimestamp to set
	 */
	public void setOpenTimestamp(long openTimestamp) {
		this.openTimestamp = openTimestamp;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
