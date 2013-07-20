package com.qsoft.bankaccount.business;

import com.qsoft.bankaccount.dao.BankAccountDao;
import com.qsoft.bankaccount.entity.BankAccountDTO;

public class BankAccount {
	private static BankAccountDao bankAccountDao;

	public static BankAccountDTO openAccount(String accountNumber) {
		BankAccountDTO accountDTO = new BankAccountDTO(accountNumber);
		bankAccountDao.save(accountDTO);
		return accountDTO;
	}

	public static void setBankAccountDao(BankAccountDao bankAccountDao) {
		BankAccount.bankAccountDao = bankAccountDao;
	}

	public static BankAccountDTO getAccountByNumber(String accountNumber) {
		// TODO Auto-generated method stub
		return bankAccountDao.getAccountDaoByNumber(accountNumber);
	}

	public static void deposite(BankAccountDTO accountDTO, float amount,
			String description) {
		// TODO Auto-generated method stub
		accountDTO.setBalance(accountDTO.getBalance() + amount);
		accountDTO.setDescription(description);
		bankAccountDao.save(accountDTO);
	}

	public static void deposite(BankAccountDTO accountDTO, float amount,
			String description, long timeStamp) {
		// TODO Auto-generated method stub
		accountDTO.setBalance(accountDTO.getBalance() + amount);
		accountDTO.setDescription(description);
		accountDTO.setOpenTimestamp(timeStamp);
		bankAccountDao.save(accountDTO);
	}

	public static void withDraw(BankAccountDTO accountDTO, float amount,
			String description, long timeStamp) {
		// TODO Auto-generated method stub
		accountDTO.setBalance(accountDTO.getBalance() - amount);
		accountDTO.setDescription(description);
		accountDTO.setOpenTimestamp(timeStamp);
		bankAccountDao.save(accountDTO);
	}

}
