package com.example.bankaccountandroid;

import java.util.List;

public class Transaction {

	private static TransactionDao transactionDao;
	private BankAccount bankAccount = new BankAccount();

	public void setTransactionDao(TransactionDao mockTransactionDao) {
		// TODO Auto-generated method stub
		Transaction.transactionDao = mockTransactionDao;
	}

	public TransactionDTO createTransaction(String accountNumber,
			long timeStamp, float amount, String description) {
		// TODO Auto-generated method stub
		BankAccountDTO accountDTO = bankAccount.getAccount(accountNumber);
		TransactionDTO transactionDTO = new TransactionDTO(accountDTO, amount,
				timeStamp, description);
		transactionDao.saveTransaction(transactionDTO);
		return transactionDTO;
	}

	public List<TransactionDTO> getTransactionOccurred(BankAccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return transactionDao.getTransactionDaoOccurred(accountDTO);
	}

	public List<TransactionDTO> getTransactionOccurredInTime(
			BankAccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return transactionDao.getTransactionDaoOccurredInTime(accountDTO);
	}

	public List<TransactionDTO> getNumberOfTransaction(BankAccountDTO accountDTO) {
		// TODO Auto-generated method stub
		return transactionDao.getNumberOfTransactionDao(accountDTO);
	}
}
