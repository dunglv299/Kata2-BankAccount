import java.util.Calendar;

public class BankAccount {
	private static BankAccountDao bankAccountDao;

	private BankAccountDTO accountDTO;
	private Transaction transaction;
	private static Calendar calendar;

	public BankAccountDTO openAccount(String accountNumber, float balance) {
		// TODO Auto-generated method stub
		BankAccountDTO accountDTO = new BankAccountDTO(accountNumber, balance);
		accountDTO.setAccountNumber(accountNumber);
		accountDTO.setBalance(balance);
		bankAccountDao.save(accountDTO);

		return accountDTO;
	}

	public void setBankAccountDao(BankAccountDao bankAccountDao) {
		// TODO Auto-generated method stub
		BankAccount.bankAccountDao = bankAccountDao;
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
		Long timeStamp = calendar.getTimeInMillis();
		transaction = new Transaction();
		transaction.createTransaction(accountNumber, timeStamp, amount,
				description);
	}

	public void withDraw(String accountNumber, float amount, String description) {
		// TODO Auto-generated method stub
		accountDTO = getAccount(accountNumber);
		accountDTO.setBalance(accountDTO.getBalance() - amount);
		bankAccountDao.save(accountDTO);
		transaction = new Transaction();
		Long timeStamp = calendar.getTimeInMillis();
		transaction.createTransaction(accountNumber, timeStamp, -amount,
				description);
	}

	public void setCalendar(Calendar mockCalendar) {
		// TODO Auto-generated method stub
		BankAccount.calendar = mockCalendar;
	}
}
