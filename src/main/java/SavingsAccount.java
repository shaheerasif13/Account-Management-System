import java.util.ArrayList;

public class SavingsAccount extends Account {

	private static double interestRate;
	private String accountType;
	private double zakat;

	static {
		interestRate = 6; // 6% interest rate specified
	}

	// Parameterized constructor
	public SavingsAccount(final int ACCOUNTNUMBER, Customer newCustomer) {
		super(ACCOUNTNUMBER, newCustomer);
		accountType = "Savings";
		calculateZakat();
	}

	// **************
	// Getter methods
	// **************

	public static double getInterestRate() {
		return interestRate;
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

	// Method to transfer amount to another account
	public boolean transferAmount(double amount, int recipientAccountNumber,
			ArrayList<SavingsAccount> savingsAccountList) {

		if (this.ACCOUNTNUMBER != recipientAccountNumber) {

			// Locating amount
			for (SavingsAccount i : savingsAccountList) {

				if (i.getAccountNumber() == recipientAccountNumber) {

					// Transferring amount
					if (this.balance >= amount) {
						this.balance = this.balance - amount;
						i.balance = i.balance + amount;
						System.out.println("\n Amount transfer successfull!");
						TransactionDetail newTransactionDetail = this.saveTransactionRecord("Funds Transfer (Withdraw)",
								amount, this.ACCOUNTNUMBER, recipientAccountNumber);
						newTransactionDetail.display();
						i.saveTransactionRecord("Funds Transfer (Deposit)", amount, this.ACCOUNTNUMBER,
								recipientAccountNumber);
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

	// Method to calculate and deduct zakat from account
	public void calculateZakat() {

		if (this.balance >= 20000) {
			this.zakat = ((this.balance * 2.5) / 100);
			this.balance = this.balance - this.zakat;
		}

		else {
			this.zakat = 0.0;
		}
	}

	@Override
	public void displayAllDeductions() {
		System.out.print("\n\t\t\tALL DEDUCTIONS\n\n");
		System.out.print("\n Zakat: " + this.zakat);
		System.out.print("\n Total amount withdrawn: " + this.totalAmountWithdrawn);
		System.out.println("\n Current balance: " + this.balance);
	}

	// Method to calculate and add interest to balance
	public void calculatelnterest(int time) {
		this.balance = this.balance + ((this.balance * interestRate * time) / 100);
	}
}