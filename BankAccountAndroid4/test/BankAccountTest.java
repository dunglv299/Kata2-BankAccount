import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;

import com.qsoft.bankaccount.BankAccount;
import com.qsoft.bankaccount.BankAccountDTO;
import com.qsoft.bankaccount.BankAccountDao;
import com.qsoft.bankaccount.Transaction;
import com.qsoft.bankaccount.TransactionDTO;
import com.qsoft.bankaccount.TransactionDao;

public class BankAccountTest extends TestCase {
	BankAccount bankAccount;
	private Transaction transaction;
	BankAccountDao mockBankAccountDao = mock(BankAccountDao.class);
	TransactionDao mockTransactionDao = mock(TransactionDao.class);
	Calendar mockCalendar = mock(Calendar.class);

	public void setUp() {
		bankAccount = new BankAccount();
		transaction = new Transaction();
		bankAccount.setBankAccountDao(mockBankAccountDao);
		transaction.setTransactionDao(mockTransactionDao);
		bankAccount.setCalendar(mockCalendar);
	}

	// Test 1
	public void testOpenAccountWithZeroBalance() {
		String accountNumber = "0123456789";
		float balance = 0;
		bankAccount.openAccount(accountNumber, balance);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockBankAccountDao).save(argument.capture());
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
		assertEquals(0, argument.getValue().getBalance(), 0.01);
	}

	// Test 2
	public void testGetAccountByAccountNumber() {
		String accountNumber = "0123456789";
		bankAccount.getAccount(accountNumber);
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		verify(mockBankAccountDao).getAccountDao(argument.capture());
		assertEquals(accountNumber, argument.getValue());
	}

	// Test 3
	public void testDepositAccount() {
		String accountNumber = "0123456789";
		float balance = 100f;
		float amount = 100f;
		String description = "deposit 100k";
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber,
				balance);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		bankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockBankAccountDao, times(2)).save(argument.capture());
		assertEquals(200, argument.getValue().getBalance(), 0.01);
	}

	// Test 4
	public void testDepositTransactionAccount() {
		String accountNumber = "0123456789";
		long timeStamp = System.currentTimeMillis();
		float amount = 100;
		String description = "deposit 100k";
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber, 0);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		bankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(mockTransactionDao).saveTransaction(argument.capture());
		assertEquals(timeStamp, argument.getValue().getTimeStamp());
	}

	// Test 5
	public void testWithDrawAccount() {
		String accountNumber = "0123456789";
		float balance = 100f;
		float amount = 50f;
		String description = "deposit 50k";
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber,
				balance);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		bankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockBankAccountDao, times(2)).save(argument.capture());
		assertEquals(50, argument.getValue().getBalance(), 0.01);
	}

	// Test 6
	public void testWithDrawTransactionAccount() {
		String accountNumber = "0123456789";
		long timeStamp = System.currentTimeMillis();
		float amount = 100;
		String description = "withdraw 100k";
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber, 0);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		bankAccount.withdraw(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(mockTransactionDao).saveTransaction(argument.capture());
		assertEquals(timeStamp, argument.getValue().getTimeStamp());
	}

	// Test 7
	public void testGetAllTransactionOccurred() {
		String accountNumber = "0123456789";
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber, 0);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		bankAccount.getAllTransactionOccurred(accountNumber);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockTransactionDao).getAllTransactionDaoOccurred(
				argument.capture());
	}

	// Step 8
	public void testGetTransactionOccurredIntime() {
		String accountNumber = "0123456789";
		long startTime = 1l;
		long stopTime = 2l;
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber, 0);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		bankAccount.getTransactionOccurredInTime(accountNumber, startTime,
				stopTime);
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockTransactionDao).getTransactionDaoOccurredInTime(
				argument.capture());
	}

}
