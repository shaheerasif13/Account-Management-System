import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CheckingAccount extends Account {

	private String accountType;
	private int freeTransactions;
	private int lastResetMonth;
	private double totalTransactionFee; // Total fees on transactions (Tax)

	// Parameterized constructor
	public CheckingAccount(final int ACCOUNTNUMBER, Customer newCustomer, int lastResetMonth) {
		super(ACCOUNTNUMBER, newCustomer);
		super.setTransactionFee(10);
		this.accountType = "Checking";
		this.totalTransactionFee = 0;
		this.lastResetMonth = lastResetMonth; // Can be modified by file handling
		resetFreeTransactions();
	}

	// **************
	// Getter methods
	// **************

	public final int getFreeTransactions() {
		return this.freeTransactions;
	}

	// **************
	// Setter methods
	// **************

	public void setFreeTransactions(int freeTransactions) {
		this.freeTransactions = freeTransactions;
	}

	// ***************
	// Utility methods
	// ***************

	// Method to reset free transactions every month
	public void resetFreeTransactions() {

		// Getting current month
		DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("MM");
		int currentMonth = Integer.parseInt(formatDateTime.format(LocalDateTime.now()));

		if (currentMonth != this.lastResetMonth) {
			this.lastResetMonth = currentMonth;
			setFreeTransactions(2);
		}
	}

	@Override
	public void displayAccountInfo() {
		super.displayAccountInfo();
		System.out.println(" Account type: " + accountType);
	}

	@Override
	public int performOperationsMenu() {
		super.performOperationsMenu();
		System.out.println("\n 8 - Main Menu.");
		System.out.print("\n Enter choice: ");
		int choice = scanner.nextInt();
		return choice;
	}

	@Override
	public TransactionDetail depositAmount(double newAmount) {
		TransactionDetail newTransactionDetail = super.depositAmount(newAmount);

		if (this.freeTransactions > 0) {
			this.freeTransactions = this.freeTransactions - 1;
			newTransactionDetail.setTransactionFee(0);
		}

		else {

			if (this.balance >= this.transactionFee) {
				this.balance = this.balance - this.transactionFee;
				this.totalTransactionFee = this.totalTransactionFee + this.transactionFee;
				newTransactionDetail.setRemainingBalance(this.balance);
			}
		}

		return newTransactionDetail;
	}

	@Override
	public TransactionDetail withdrawAmount(double newAmount) {
		TransactionDetail newTransactionDetail = super.withdrawAmount(newAmount);

		if (newTransactionDetail != null) {

			if (this.freeTransactions > 0) {
				this.freeTransactions = this.freeTransactions - 1;
				newTransactionDetail.setTransactionFee(0);
			}

			else {

				if (this.balance >= this.transactionFee) {
					this.balance = this.balance - this.transactionFee;
					this.totalTransactionFee = this.totalTransactionFee + this.transactionFee;
					newTransactionDetail.setRemainingBalance(this.balance);
				}
			}

			return newTransactionDetail;
		}

		return null;
	}

	// Method to transfer amount to another account
	public boolean transferAmount(double amount, int recipientAccountNumber,
			ArrayList<CheckingAccount> checkingAccountList) {

		if (this.ACCOUNTNUMBER != recipientAccountNumber) {

			// Locating account
			for (CheckingAccount i : checkingAccountList) {

				if (i.getAccountNumber() == recipientAccountNumber) {

					// Transferring amount
					if (this.balance >= amount) {
						this.balance = this.balance - amount;
						i.balance = i.balance + amount;
						System.out.println("\n Amount transfer successfull!");
						TransactionDetail newTransactionDetail = this.saveTransactionRecord("Funds Transfer", amount,
								this.ACCOUNTNUMBER, recipientAccountNumber);
						newTransactionDetail.display();
						i.saveTransactionRecord("Funds Transfer", amount, this.ACCOUNTNUMBER, recipientAccountNumber);
						return true;
					}

					System.out.println("\n Transfer unsuccessfull!\n Not enough balance!");
					return false;
				}
			}

			System.out.println("\n Recipients account number: " + recipientAccountNumber + " not found!");
			return false;
		}

		else {
			System.out.println("\n Recipient account number must be different!");
			return false;
		}
	}

	@Override
	public void displayAllDeductions() {
		System.out.print("\n\t\t\tALL DEDUCTIONS\n\n");
		System.out.print("\n Tax: " + this.totalTransactionFee);
		System.out.print("\n Total amount withdrawn: " + this.totalAmountWithdrawn);
		System.out.println("\n Current balance: " + this.balance);
	}
}