import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;

public class AccountManagementSystem {

	private Scanner scanner; // Scanner object for taking user inputs
	private ArrayList<CheckingAccount> checkingAccountList; // ArrayList of all checking accounts in bank
	private ArrayList<SavingsAccount> savingsAccountList; // ArrayList of all savings accounts in bank

	// Default constructor
	public AccountManagementSystem() {
		scanner = new Scanner(System.in);
		checkingAccountList = new ArrayList<CheckingAccount>();
		savingsAccountList = new ArrayList<SavingsAccount>();
	}

	// ********************************
	// Methods to display menus to user
	// ********************************

	// Method to display main menu
	private int mainMenu() {
		System.out.println("\n\t\t\t   ACCOUNT MANAGEMENT SYSTEM\n\n");
		System.out.println(" 1 - Open a new account.");
		System.out.println(" 2 - Close an existing account.");
		System.out.println(" 3 - Log in to an existing account.");
		System.out.println(" 4 - Show specific interest rate.");
		System.out.println(" 5 - Exit.");
		System.out.print("\n Enter choice: ");
		int choice = scanner.nextInt();

		// Validating user choice
		while (choice < 1 || choice > 5) {
			System.out.print("\n Invalid choice!\n Enter choice again: ");
			choice = scanner.nextInt();
		}

		if (choice == 5) {
			System.out.println("\n Exiting ...");
		}

		return choice;
	}

	// Method to display choose account menu
	private int chooseAccountMenu() {
		System.out.println("\n\t\t\t      CHOOSE ACCOUNT TYPE");
		System.out.println("\n 1 - Checking account.");
		System.out.println(" 2 - Savings account.");
		System.out.println(" 3 - Main menu.");
		System.out.print("\n Enter choice: ");

		int choice = scanner.nextInt();

		// Validating user choice
		while (choice < 1 || choice > 3) {
			System.out.print("\n Invalid choice!\n Enter choice again: ");
			choice = scanner.nextInt();
		}

		return choice;
	}

	// ****************************
	// Methods to add a new account
	// ****************************

	// Method to add a new account
	private void addAccount() {

		// Showing choose account menu
		int choice = chooseAccountMenu();

		if (choice == 1) {
			Customer newCustomer = inputCustomerInfo();
			addCheckingAccount(newCustomer);
		}

		else if (choice == 2) {
			Customer newCustomer = inputCustomerInfo();
			addSavingsAccount(newCustomer);
		}
	}

	// Method to add a new checking account
	public boolean addCheckingAccount(Customer newCustomer) {

		// Checking if new CNIC already exists or not
		for (CheckingAccount i : checkingAccountList) {

			if (i.getCNIC().equals(newCustomer.getCNIC())) {
				System.out.println("\n Checking account for CNIC: " + newCustomer.getCNIC() + " already exists!");
				return false;
			}
		}

		// If CNIC is valid add a new checking account
		CheckingAccount newCheckingAccount = new CheckingAccount(generateNewAccountNumber(), newCustomer, -1);
		checkingAccountList.add(newCheckingAccount);
		newCheckingAccount.displayAccountInfo();
		System.out.println("\n Checking account successfully created!");
		return true;
	}

	// Method to add a new savings account
	public boolean addSavingsAccount(Customer newCustomer) {

		// Checking if new CNIC already exists or not
		for (SavingsAccount i : savingsAccountList) {

			if (i.getCNIC().equals(newCustomer.getCNIC())) {
				System.out.println("\n Savings account for CNIC: " + newCustomer.getCNIC() + " already exists!");
				return false;
			}
		}

		// If CNIC is valid add a new savings account
		SavingsAccount newSavingsAccount = new SavingsAccount(generateNewAccountNumber(), newCustomer);
		savingsAccountList.add(newSavingsAccount);
		newSavingsAccount.displayAccountInfo();
		System.out.println("\n Savings account successfully created!");
		return true;
	}

	// Method to create a new customer
	private Customer inputCustomerInfo() {

		// Taking customer information as input
		scanner.nextLine();
		System.out.print("\n Enter CNIC: ");
		final String newCNIC = scanner.nextLine();
		System.out.print(" Enter name: ");
		String newName = scanner.nextLine();
		System.out.print(" Enter address: ");
		String newAddress = scanner.nextLine();
		System.out.print(" Enter phone number: ");
		String newPhoneNumber = scanner.nextLine();

		// Creating a new customer
		Customer newCustomer = new Customer(newCNIC, newName, newAddress, newPhoneNumber);
		return newCustomer;
	}

	// Method to generate a new account number (For new user)
	private int generateNewAccountNumber() {
		Random random = new Random();
		int accountNumber = random.ints(10000000, 100000000).findFirst().getAsInt();

		while (!isValidNewAccountNumber(accountNumber)) {
			accountNumber = random.ints(10000000, 100000000).findFirst().getAsInt();
		}

		return accountNumber;
	}

	// Method to validate new account number for duplication
	private boolean isValidNewAccountNumber(int targetAccountNumber) {

		// Checking target account number in existing checking accounts
		for (CheckingAccount i : checkingAccountList) {

			if (i.getAccountNumber() == targetAccountNumber) {
				return false;
			}
		}

		// Checking target account number in existing savings accounts
		for (SavingsAccount i : savingsAccountList) {

			if (i.getAccountNumber() == targetAccountNumber) {
				return false;
			}
		}

		return true;
	}

	// ************************************
	// Methods to close an existing account
	// ************************************

	// Method to close an existing account
	private void closeAccount() {

		// Showing choose account menu
		int choice = chooseAccountMenu();

		if (choice == 1) {

			// Taking target account number as input
			System.out.print("\n Enter account number: ");
			int targetAccountNumber = scanner.nextInt();

			closeCheckingAccount(targetAccountNumber);
		}

		else if (choice == 2) {

			// Taking target account number as input
			System.out.print("\n Enter account number: ");
			int targetAccountNumber = scanner.nextInt();

			closeSavingsAccount(targetAccountNumber);
		}
	}

	// Method to close an existing checking account
	public boolean closeCheckingAccount(int targetAccountNumber) {

		// Searching target account number in checking account list
		for (CheckingAccount i : checkingAccountList) {

			if (i.getAccountNumber() == targetAccountNumber) {
				checkingAccountList.remove(i);
				System.out.println(
						"\n Checking account number: " + targetAccountNumber + " has been closed successfully!");
				return true;
			}
		}

		System.out.println("\n Checking account number: " + targetAccountNumber + " not found!");
		return false;
	}

	// Method to close an existing savings account
	public boolean closeSavingsAccount(int targetAccountNumber) {

		// Searching target account number in savings account list
		for (SavingsAccount i : savingsAccountList) {

			if (i.getAccountNumber() == targetAccountNumber) {
				savingsAccountList.remove(i);
				System.out.println(
						"\n Savinsgs account number: " + targetAccountNumber + " has been closed successfully!");
				return true;
			}
		}

		System.out.println("\n Savings account number: " + targetAccountNumber + " not found!");
		return false;
	}

	// ****************************************************************
	// Methods to log in and perform operations from a specific account
	// ****************************************************************

	// Method to log in to an existing account (To perform operations)
	private void loginAccount() {

		// Showing choose account menu
		int choice = chooseAccountMenu();

		if (choice == 1) {

			// Taking target account number as input
			System.out.print("\n Enter account number: ");
			int targetAccountNumber = scanner.nextInt();

			// Logging in
			CheckingAccount targetCheckingAccount = loginCheckingAccount(targetAccountNumber);

			// Performing operations if logged in
			performCheckingOperations(targetCheckingAccount);
		}

		else if (choice == 2) {

			// Taking target account number as input
			System.out.print("\n Enter account number: ");
			int targetAccountNumber = scanner.nextInt();

			// Logging in
			SavingsAccount targetSavingsAccount = loginSavingsAccount(targetAccountNumber);

			// Performing operations if logged in
			performSavingsOperations(targetSavingsAccount);
		}
	}

	// Method to log in to an existing checking account
	public CheckingAccount loginCheckingAccount(int targetAccountNumber) {

		// Searching target account number in checking account list
		for (CheckingAccount i : checkingAccountList) {

			if (i.getAccountNumber() == targetAccountNumber) {
				System.out.print("\n Authenticated!");
				return i;
			}
		}

		System.out.println("\n Checking account number: " + targetAccountNumber + " not found!");
		return null;
	}

	// Method to log in to an existing savings account
	public SavingsAccount loginSavingsAccount(int targetAccountNumber) {

		// Searching target account number in savings account list
		for (SavingsAccount i : savingsAccountList) {

			if (i.getAccountNumber() == targetAccountNumber) {
				System.out.print("\n Authenticated!");
				return i;
			}
		}

		System.out.println("\n Savings account number: " + targetAccountNumber + " not found!");
		return null;
	}

	// Method for performing operations on a checking account
	public boolean performCheckingOperations(CheckingAccount currentCheckingAccount) {

		if (currentCheckingAccount != null) {
			int choice = -1;

			while (choice != 8) {

				// Displaying operations menu
				choice = currentCheckingAccount.performOperationsMenu();

				if (choice == 1) {
					currentCheckingAccount.displayAccountInfo();
				}

				else if (choice == 2) {
					currentCheckingAccount.displayAllDeductions();
				}

				else if (choice == 3) {
					System.out.print("\n\n\t\t\t\tDEPOSIT AMOUNT\n");

					// Taking amount to deposit as input
					System.out.print("\n Enter amount to deposit: ");
					double amount = scanner.nextDouble();

					// Depositing amount
					TransactionDetail newTransactionDetail = currentCheckingAccount.depositAmount(amount);

					// Displaying transaction details
					newTransactionDetail.display();
				}

				else if (choice == 4) {
					System.out.print("\n\n\t\t\tWITHDRAW AMOUNT\n");

					// Taking amount to withdraw as input
					System.out.print("\n Enter amount to withdraw: ");
					double amount = scanner.nextDouble();

					// Withdrawing amount
					TransactionDetail newTransactionDetail = currentCheckingAccount.withdrawAmount(amount);

					// Displaying transaction details if successful
					if (newTransactionDetail != null) {
						newTransactionDetail.display();
					}
				}

				else if (choice == 5) {
					currentCheckingAccount.checkBalance();
				}

				else if (choice == 6) {
					currentCheckingAccount.printStatement();
				}

				else if (choice == 7) {
					System.out.print("\n\n\t\t\t   FUNDS TRANSFER (CHECKING)\n");

					// Taking recipients account number as input
					System.out.print("\n Enter recipients account number: ");
					int recipientAccountNumber = scanner.nextInt();

					// Taking amount to transfer as input
					System.out.print("\n Enter amount to transfer: ");
					double amount = scanner.nextDouble();

					// Transferring amount
					currentCheckingAccount.transferAmount(amount, recipientAccountNumber, checkingAccountList);
				}
			}

			return true;
		}

		else {
			return false;
		}
	}

	// Method for performing operations on a savings account
	public boolean performSavingsOperations(SavingsAccount currentSavingsAccount) {

		if (currentSavingsAccount != null) {
			int choice = -1;

			while (choice != 8) {

				// Displaying operations menu
				choice = currentSavingsAccount.performOperationsMenu();

				if (choice == 1) {
					currentSavingsAccount.displayAccountInfo();
				}

				else if (choice == 2) {
					currentSavingsAccount.displayAllDeductions();
				}

				else if (choice == 3) {
					System.out.print("\n\n\t\t\t\tDEPOSIT AMOUNT\n");

					// Taking amount to deposit as input
					System.out.print("\n Enter amount to deposit: ");
					double amount = scanner.nextDouble();

					// Depositing amount
					TransactionDetail newTransactionDetail = currentSavingsAccount.depositAmount(amount);

					// Displaying transaction details
					newTransactionDetail.display();
				}

				else if (choice == 4) {
					System.out.print("\n\n\t\t\tWITHDRAW AMOUNT\n");

					// Taking amount to withdraw as input
					System.out.print("\n Enter amount to withdraw: ");
					double amount = scanner.nextDouble();

					// Withdrawing amount
					TransactionDetail newTransactionDetail = currentSavingsAccount.withdrawAmount(amount);

					// Saving transaction record if successful
					if (newTransactionDetail != null) {
						newTransactionDetail.display();
					}
				}

				else if (choice == 5) {
					currentSavingsAccount.checkBalance();
				}

				else if (choice == 6) {
					currentSavingsAccount.printStatement();
				}

				else if (choice == 7) {
					System.out.print("\n\n\t\t\t    FUNDS TRANSFER (SAVINGS)\n");

					// Taking recipients account number as input
					System.out.print("\n Enter recipients account number: ");
					int recipientAccountNumber = scanner.nextInt();

					// Taking amount to transfer as input
					System.out.print("\n Enter amount to transfer: ");
					double amount = scanner.nextDouble();

					// Transferring amount
					currentSavingsAccount.transferAmount(amount, recipientAccountNumber, savingsAccountList);
				}
			}

			return true;
		}

		else {
			return false;
		}
	}

	// ****************************************
	// Methods to get account number given CNIC
	// ****************************************

	public int getAccountNumberChecking(String targetCNIC) {

		for (CheckingAccount i : checkingAccountList) {

			if (i.getCNIC().equals(targetCNIC)) {
				return i.getAccountNumber();
			}
		}

		return -1;
	}

	public int getAccountNumberSavings(String targetCNIC) {

		for (SavingsAccount i : savingsAccountList) {

			if (i.getCNIC().equals(targetCNIC)) {
				return i.getAccountNumber();
			}
		}

		return -1;
	}

	// **********************
	// Method to start system
	// **********************

	public void start() {
		int choice = -1;

		while (choice != 5) {
			choice = mainMenu();

			if (choice == 1) {
				addAccount();
			}

			else if (choice == 2) {
				closeAccount();
			}

			else if (choice == 3) {
				loginAccount();
			}

			else if (choice == 4) {
				System.out.println("\n Interest rate (Specified to all savings accounts): "
						+ (int) SavingsAccount.getInterestRate() + "%");
			}
		}
	}

	// ***************************************************
	// Method to check username and password from database
	// ***************************************************

	public boolean validateAdminLogin(String username, String password) {
		boolean valid = true;

		try {
			String url = "jdbc:mysql://localhost:3308/accountmanagementsystem";
			String uname = "root";
			String pass = "tiger123";

			// Load and register driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Establishing connection
			try {
				Connection connection = DriverManager.getConnection(url, uname, pass);

				// Creating statement
				String query = "SELECT * FROM Login WHERE Username = ? AND Password = ?";
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, password);

				// Executing query and storing in result set
				ResultSet resultSet = preparedStatement.executeQuery();

				// If username or password is invalid
				if (!resultSet.next()) {
					valid = false;
				}

				preparedStatement.close();
				connection.close();
			}

			catch (SQLException e) {
				e.printStackTrace();
			}
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return valid;
	}

	// ************************
	// Method to turn on system
	// ************************
	public void ON() {
		System.out.print("\nUsername: ");
		String username = scanner.nextLine();
		System.out.print("Password: ");
		String password = scanner.nextLine();

		// Validating admin log in
		if (!this.validateAdminLogin(username, password)) {
			System.out.println("\nInvalid username or password!");
			return;
		}

		// Starting system
		this.start();
	}
}