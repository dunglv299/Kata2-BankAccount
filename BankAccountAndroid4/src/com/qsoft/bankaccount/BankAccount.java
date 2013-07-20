package com.qsoft.bankaccount;

import java.util.Calendar;
import java.util.List;

public class BankAccount {
	private static BankAccountDao bankAccountDao;
	private BankAccountDTO accountDTO;
	// private TransactionDao transactionDao;
	private Calendar calendar;
	private Transaction transaction;

	public BankAccountDTO openAccount(String accountNumber, float balance) {
		// TODO Auto-generated method stub
		BankAccountDTO accountDTO = new BankAccountDTO(accountNumber, balance);
		bankAccountDao.save(accountDTO);
		return accountDTO;
	}

	public void setBankAccountDao(BankAccountDao mockBankAccountDao) {
		// TODO Auto-generated method stub
		BankAccount.bankAccountDao = mockBankAccountDao;
	}

	// public void setTransactionDao(TransactionDao mockTransactionDao) {
	// // TODO Auto-generated method stub
	// BankAccount.transactionDao = mockTransactionDao;
	// }

	public void setCalendar(Calendar mockCalendar) {
		// TODO Auto-generated method stub
		this.calendar = mockCalendar;
	}

	public BankAccountDTO getAccount(String accountNumber) {
		// TODO Auto-generated method stub
		return bankAccountDao.getAccountDao(accountNumber);
	}

	public void deposit(String accountNumber, float amount, String description) {
		// TODO Auto-generated method stub
		accountDTO = getAccount(accountNumber);
		accountDTO.setBalance(accountDTO.getBalance() + amount);
		bankAccountDao.save(accountDTO);
		long timeStamp = calendar.getTimeInMillis();
		transaction = new Transaction();
		transaction.createTransaction(accountNumber, timeStamp, amount,
				description);
	}

	public void withdraw(String accountNumber, float amount, String description) {
		// TODO Auto-generated method stub
		accountDTO = getAccount(accountNumber);
		accountDTO.setBalance(accountDTO.getBalance() - amount);
		bankAccountDao.save(accountDTO);
		long timeStamp = calendar.getTimeInMillis();
		transaction = new Transaction();
		transaction.createTransaction(accountNumber, timeStamp, amount,
				description);
	}

	public List<TransactionDTO> getAllTransactionOccurred(String accountNumber) {
		// TODO Auto-generated method stub
		accountDTO = getAccount(accountNumber);
		transaction = new Transaction();
		return transaction.getTransactionOccurred(accountDTO);
	}

	public List<TransactionDTO> getTransactionOccurredInTime(
			String accountNumber, long startTime, long stopTime) {
		// TODO Auto-generated method stub
		accountDTO = getAccount(accountNumber);
		transaction = new Transaction();

		return transaction.getTransactionOccurredInTime(accountDTO);
	}
}
