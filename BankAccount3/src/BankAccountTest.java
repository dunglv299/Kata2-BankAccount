import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class BankAccountTest {
	BankAccount bankAccount;
	private Transaction transaction;

	private BankAccountDao mockBankAccountDao = mock(BankAccountDao.class);
	private TransactionDao mockTransactionDao = mock(TransactionDao.class);
	private Calendar mockCalendar = mock(Calendar.class);

	@Before
	public void setUp() {
		bankAccount = new BankAccount();
		transaction = new Transaction();
		reset(mockBankAccountDao);
		reset(mockTransactionDao);
		reset(mockCalendar);
		bankAccount.setBankAccountDao(mockBankAccountDao);
		transaction.setTransactionDao(mockTransactionDao);
		bankAccount.setCalendar(mockCalendar);
	}

	// Step 1
	@Test
	public void testOpenAccountWithZeroBalance() {
		BankAccountDTO accountDTO = bankAccount.openAccount("1234567890", 0);
		assertEquals(0, accountDTO.getBalance(), 0.01);
		assertEquals("1234567890", accountDTO.getAccountNumber());
	}

	// Step 2
	@Test
	public void testGetAccount() {
		String accountNumber = "1234567890";
		bankAccount.getAccount(accountNumber);
		ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
		verify(mockBankAccountDao).getAccountDao(argument.capture());
		assertEquals("1234567890", argument.getValue());
	}

	// Step 3
	@Test
	public void testDepositAccount() {
		String accountNumber = "1234567890";
		float balance = 100f;
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber,
				balance);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		bankAccount.deposit(accountNumber, 100f, "Deposit 100k");
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockBankAccountDao, times(2)).save(argument.capture());
		assertEquals(200, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
	}

	// Step 4
	@Test
	public void testDepositTransaction() {
		String accountNumber = "1234567890";
		float amount = 100f;
		long timeStamp = System.currentTimeMillis();
		String description = "deposit 100k";
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber, 0);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		bankAccount.deposit(accountNumber, amount, description);
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(mockTransactionDao).saveTransaction(argument.capture());
		assertEquals(100, argument.getValue().getAmount(), 0.01);
		assertEquals(timeStamp, argument.getValue().getTimeStamp(), 0.01);
	}

	// Step 5
	@Test
	public void testWithDrawAccount() {
		String accountNumber = "1234567890";
		float balance = 200f;
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber,
				balance);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		bankAccount.withDraw(accountNumber, 100f, "Withdraw 100k");
		ArgumentCaptor<BankAccountDTO> argument = ArgumentCaptor
				.forClass(BankAccountDTO.class);
		verify(mockBankAccountDao, times(2)).save(argument.capture());
		assertEquals(100, argument.getValue().getBalance(), 0.01);
		assertEquals(accountNumber, argument.getValue().getAccountNumber());
	}

	// Step 6
	@Test
	public void testWithDrawTransaction() {
		String accountNumber = "1234567890";
		float amount = 100f;
		long timeStamp = System.currentTimeMillis();
		BankAccountDTO accountDTO = bankAccount.openAccount(accountNumber, 0);
		when(mockBankAccountDao.getAccountDao(accountNumber)).thenReturn(
				accountDTO);
		when(mockCalendar.getTimeInMillis()).thenReturn(timeStamp);
		bankAccount.withDraw(accountNumber, amount, "Withdraw 100k");
		ArgumentCaptor<TransactionDTO> argument = ArgumentCaptor
				.forClass(TransactionDTO.class);
		verify(mockTransactionDao).saveTransaction(argument.capture());
		assertEquals(-100, argument.getValue().getAmount(), 0.01);
		assertEquals(timeStamp, argument.getValue().getTimeStamp(), 0.01);
	}
}
