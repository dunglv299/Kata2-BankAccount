package com.qsoft.bankaccount.transaction;

import java.util.List;

import com.qsoft.bankaccount.entity.TransactionDTO;

public class Transaction {
	private static TransactionDao transactionDao;

	public static void setTransactionDao(TransactionDao mockTransactionDao) {
		// TODO Auto-generated method stub
		transactionDao = mockTransactionDao;
	}

	public TransactionDTO createTransaction(String accountNumber, float amount,
			String description, long timeStamp) {
		// TODO Auto-generated method stub
		TransactionDTO transactionDTO = new TransactionDTO(accountNumber,
				amount, description, timeStamp);
		transactionDao.save(transactionDTO);
		return transactionDTO;
	}

	public List<TransactionDTO> getTransactionsOccurred(String accountNumber) {
		// TODO Auto-generated method stub
		return transactionDao.getTransactionsOccurredDao(accountNumber);
	}

	public List<TransactionDTO> getTransactionsOccurred(String accountNumber,
			long startTime, long stopTime) {
		// TODO Auto-generated method stub
		return transactionDao.getTransactionsOccurredDao(accountNumber,
				startTime, stopTime);
	}

	public List<TransactionDTO> getLastTransaction(String accountNumber) {
		// TODO Auto-generated method stub
		return transactionDao.getLastTransactionDao(accountNumber);
	}
}
