import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.ArrayList;

public abstract class Account {

	protected final int ACCOUNTNUMBER;
	protected final String DATECREATED;
	protected Customer customer;
	protected double balance;
	protected ArrayList<TransactionDetail> transactionRecord; // List to keep record of transaction of this account
	protected double totalAmountWithdrawn;
	protected double transactionFee;
	protected Scanner scanner;

	// Parameterized constructor
	public Account(final int ACCOUNTNUMBER, Customer newCustomer) {
		this.ACCOUNTNUMBER = ACCOUNTNUMBER;
		customer = newCustomer;
		balance = 0.0;
		transactionRecord = new ArrayList<TransactionDetail>();
		totalAmountWithdrawn = 0.0;
		transactionFee = 0;
		scanner = new Scanner(System.in);

		// Getting current date and time (Setting date of creation of account)
		DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
		DATECREATED = formatDateTime.format(LocalDateTime.now());
	}

	// **************
	// Getter methods
	// **************

	public final String getCNIC() {
		return this.customer.getCNIC();
	}

	public final int getAccountNumber() {
		return this.ACCOUNTNUMBER;
	}

	public final double getBalance() {
		return this.balance;
	}

	public final double getTransacitonFee() {
		return this.transactionFee;
	}

	// **************
	// Setter methods
	// **************

	public void setBalance(double balance) {

		if (balance < 0) {
			throw new IllegalArgumentException("Invalid balance!");
		}

		this.balance = balance;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	// ***************
	// Utility methods
	// ***************

	// Method to display user account information
	public void displayAccountInfo() {
		System.out.print("\n\t\t\t   CUSTOMER ACCOUNT INFORMATION\n\n");
		System.out.println(" Account number: " + this.ACCOUNTNUMBER);
		customer.displayCustomerInfo();
		System.out.println(" Balance: " + this.balance);
		System.out.println(" Date created: " + this.DATECREATED);
	}

	// Method to display operations menu
	public int performOperationsMenu() {
		System.out.print("\n\n\t\t\t\tPERFORM OPERATIONS\n\n");
		System.out.println(" 1 - Account details.");
		System.out.print(" 2 - Account deductions.");
		System.out.print("\n 3 - Deposit amount.");
		System.out.print("\n 4 - Withdraw amount.");
		System.out.print("\n 5 - Check balance.");
		System.out.print("\n 6 - Print statement.");
		System.out.print("\n 7 - Transfer amount.");
		return 0;
	}

	// Method to save transaction record
	public TransactionDetail saveTransactionRecord(String type, double newAmount, int senderAccountNumber,
			int recipientAccountNumber) {

		// Displaying and saving transaction details
		DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String currentDate = formatDateTime.format(LocalDateTime.now());
		formatDateTime = DateTimeFormatter.ofPattern("hh:mm:ss");
		String currentTime = formatDateTime.format(LocalDateTime.now());

		TransactionDetail newTransactionDetail = new TransactionDetail(type, currentDate, currentTime, newAmount,
				this.transactionFee, this.balance, senderAccountNumber, recipientAccountNumber);
		transactionRecord.add(newTransactionDetail);
		return newTransactionDetail;
	}

	// Method to deposit amount in account
	public TransactionDetail depositAmount(double newAmount) {

		// Performing transaction
		this.balance = this.balance + newAmount;
		System.out.println("\n Transaction successfull!");

		// Saving transaction record
		TransactionDetail newTransactionDetail = this.saveTransactionRecord("Deposit", newAmount,
				this.getAccountNumber(), this.getAccountNumber());

		return newTransactionDetail;
	}

	// Method to withdraw amount from account
	public TransactionDetail withdrawAmount(double newAmount) {

		if (this.balance >= newAmount) {

			// Performing transaction
			this.balance = this.balance - newAmount;
			this.totalAmountWithdrawn = this.totalAmountWithdrawn + newAmount;
			System.out.println("\n Transaction successfull!");

			// Saving transaction record
			TransactionDetail newTransactionDetail = this.saveTransactionRecord("Withdraw", newAmount,
					this.getAccountNumber(), this.getAccountNumber());

			return newTransactionDetail;
		}

		else {
			System.out.println("\n Transaction unsuccessful!\n Not enough balance!");
			return null;
		}
	}

	// Method to check balance of account
	public void checkBalance() {
		System.out.print("\n\n\t\t\t\tCURRENT BALANCE\n");
		System.out.println("\n Name: " + this.customer.getName());
		System.out.println(" Balance: " + this.balance);
	}

	// Method to display transaction statement
	public boolean printStatement() {
		displayAccountInfo();

		// If any transaction record is present
		if (!transactionRecord.isEmpty()) {
			System.out.print("\n\n\t\t\t     TRANSACTION STATEMENT\n");

			for (TransactionDetail i : transactionRecord) {
				i.display();
			}

			return true;
		}

		System.out.println("\n No transaction details!");
		return false;
	}

	// Abstract methods
	public abstract void displayAllDeductions(); // Method to display all deductions (Tax, Zakat etc)
}