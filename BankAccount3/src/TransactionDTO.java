public class TransactionDTO {
	private BankAccountDTO accountDTO;
	private float amount;
	private long timeStamp;
	private String description;

	public TransactionDTO(BankAccountDTO accountDTO, float amount, long timeStamp,
			String description) {
		super();
		this.accountDTO = accountDTO;
		this.amount = amount;
		this.timeStamp = timeStamp;
		this.description = description;
	}

	/**
	 * @return the amount
	 */
	public float getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(float amount) {
		this.amount = amount;
	}

	/**
	 * @return the timeStamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * @param timeStamp
	 *            the timeStamp to set
	 */
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
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
