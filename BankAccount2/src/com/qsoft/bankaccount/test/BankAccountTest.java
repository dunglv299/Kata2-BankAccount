package com.qsoft.bankaccount.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.qsoft.bankaccount.business.BankAccount;
import com.qsoft.bankaccount.dao.BankAccountDao;
import com.qsoft.bankaccount.entity.BankAccountDTO;
import com.qsoft.bankaccount.entity.TransactionDTO;
import com.qsoft.bankaccount.transaction.Transaction;
import com.qsoft.bankaccount.transaction.TransactionDao;

public class BankAccountTest {
	BankAccountDao mockAccountDao = mock(BankAccountDao.class);
	TransactionDao mockTransactionDao = mock(TransactionDao.class);

	@Before
	public void setUp() {
		reset(mockAccountDao);
		reset(mockTransactionDao);
		BankAccount.setBankAccountDao(mockAccountDao);
		Transaction.setTransactionDao(mockTransactionDao);
	}

	@Test
	public void testOpenAccountWithZeroBalance() {
		BankAccount.openAccount("1234567890");
		// Capture account 1234567890
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		// Verify mock data and test save function with captured argument
		verify(mockAccountDao).save(argument.capture());
		assertEquals(0.0, argument.getValue().getBalance(), 0.01);
		assertEquals("1234567890", argument.getValue().getAccountNumber());
	}

	@Test
	public void testGetAccount() {
		BankAccount.getAccountByNumber("1234567890");
		verify(mockAccountDao).getAccountDaoByNumber("1234567890");
	}

	@Test
	public void testDeposit() {
		BankAccountDTO account = BankAccount.openAccount("1234567890");
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);

		BankAccount.deposite(account, 200, "Deposit 200k");

		// Verify mock data and test invoke save() with this Account
		verify(mockAccountDao, times(2)).save(argument.capture());
		assertEquals(200.0, argument.getValue().getBalance(), 0.01);

		BankAccount.deposite(account, -100, "Withdraw 100k");
		verify(mockAccountDao, times(3)).save(argument.capture());
		assertEquals(100.0, argument.getValue().getBalance(), 0.01);
	}

	@Test
	public void testSaveAccountWithTimeStamp() {
		// Open account
		BankAccountDTO accountDTO = BankAccount.openAccount("1234567890");

		// Capture account 1234567890
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		BankAccount.deposite(accountDTO, 100, "Deposit 100k", 1352653200000l);
		verify(mockAccountDao, times(2)).save(argument.capture());
		assertEquals(1352653200000l, argument.getValue().getOpenTimestamp(),
				0.01);
	}

	@Test
	public void testDepositeTransaction() {
		Transaction transaction = new Transaction();
		transaction.createTransaction("1234567890", 100, "Deposit 100k",
				1352653200000l);

		ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(mockTransactionDao, times(1))
				.save(argumentTransaction.capture());
		assertEquals("1234567890", argumentTransaction.getValue()
				.getAccountNumber());
		assertEquals(100, argumentTransaction.getValue().getAmount(), 0.01);
		assertEquals("Deposit 100k", argumentTransaction.getValue()
				.getDescription());
		assertTrue(argumentTransaction.getValue().getTimeStamp() != 0);
	}

	@Test
	public void testWithDraw() {
		BankAccountDTO accountDTO = BankAccount.openAccount("1234567890");
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		BankAccount.withDraw(accountDTO, 50, "Withdraw 50k", 1352653200000l);
		verify(mockAccountDao, times(2)).save(argument.capture());
		assertEquals(-50, argument.getValue().getBalance(), 0.01);
	}

	@Test
	public void testWithDrawTransaction() {
		Transaction transaction = new Transaction();
		transaction.createTransaction("123456789", 50, "Withdraw 50k",
				1352653200000l);
		ArgumentCaptor<TransactionDTO> argumentTransaction = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(mockTransactionDao).save(argumentTransaction.capture());
		assertEquals("123456789", argumentTransaction.getValue()
				.getAccountNumber());
		assertEquals(50, argumentTransaction.getValue().getAmount(), 0.01);
		assertEquals("Withdraw 50k", argumentTransaction.getValue()
				.getDescription());
		assertTrue(argumentTransaction.getValue().getTimeStamp() != 0);
	}

	@Test
	public void testGetTransactionsOccurred() {
		Transaction transaction = new Transaction();
		transaction.getTransactionsOccurred("1234567890");
		verify(mockTransactionDao).getTransactionsOccurredDao("1234567890");
	}

	@Test
	public void testGetTransactionOccurredInTime() {
		Transaction transaction = new Transaction();
		transaction.getTransactionsOccurred("1234567890", 1352653200000l,
				1384189200000l);
		verify(mockTransactionDao).getTransactionsOccurredDao("1234567890",
				1352653200000l, 1384189200000l);
	}

	@Test
	public void testGetLastTransaction() {
		Transaction transaction = new Transaction();
		transaction.getLastTransaction("1234567890");
		verify(mockTransactionDao).getLastTransactionDao("1234567890");
	}
}
