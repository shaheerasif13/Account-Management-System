public class TransactionDetail {

	private String type;
	private String date;
	private String time;
	private double transactionAmount;
	private double transactionFee;
	private double remainingBalance;
	private int recipientAccountNumber;
	private int senderAccountNumber;

	// Parameterized constructor
	public TransactionDetail(String type, String date, String time, double transactionAmount, double transactionFee,
			double remainingBalance, int senderAccountNumber, int recipientAccountNumber) {
		this.type = type;
		this.date = date;
		this.time = time;
		this.transactionAmount = transactionAmount;
		this.transactionFee = transactionFee;
		this.remainingBalance = remainingBalance;
		this.recipientAccountNumber = recipientAccountNumber;
		this.senderAccountNumber = senderAccountNumber;
	}

	// **************
	// Setter methods
	// **************

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public void setRemainingBalance(double remainingBalance) {
		this.remainingBalance = remainingBalance;
	}

	// Method to display transaction details
	public void display() {
		System.out.println("\n Transaction type: " + this.type);

		// If transaction type is funds transfer
		if (this.senderAccountNumber != this.recipientAccountNumber) {
			System.out.println(" Sender acocount number: " + this.senderAccountNumber);
			System.out.println(" Recipient acocount number: " + this.recipientAccountNumber);
		}

		System.out.println(" Date of transaction: " + this.date);
		System.out.println(" Time of transaction: " + this.time);
		System.out.println(" Transaction amount: " + this.transactionAmount);
		System.out.println(" Transaciton fee: " + this.transactionFee);
		System.out.println(" Remaining balance: " + this.remainingBalance);
	}
}