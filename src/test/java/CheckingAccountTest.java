import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;

public class CheckingAccountTest {

	private CheckingAccount checkingAccount;
	private Customer customer;

	@Before
	public void initialize() {
		customer = new Customer("34201-1357865-7", "Muhammad Shaheer", "House no. 999, Street no. 43, G10-3, Islamabad",
				"03001234567");
		checkingAccount = new CheckingAccount(11111111, customer, -1);
	}

	// Method to test deposit amount
	@Test
	public void testDepositAmount() {
		double currentBalance = 1000.5;
		double newAmount = 900.4;
		double expectedBalance = currentBalance + newAmount;
		checkingAccount.setBalance(currentBalance);

		// Act
		checkingAccount.depositAmount(newAmount);

		// Assert
		assertEquals(expectedBalance, checkingAccount.getBalance(), 0.01);
	}

	// *******************************
	// Methods to test withdraw amount
	// *******************************

	@Test
	public void testWithdrawAmountSuccess() {
		double currentBalance = 1000.5;
		double newAmount = 900.4;
		double expectedBalance = currentBalance - newAmount;
		checkingAccount.setBalance(currentBalance);

		// Act and assert
		assertNotNull(checkingAccount.withdrawAmount(newAmount));
		assertEquals(expectedBalance, checkingAccount.getBalance(), 0.01);
	}

	@Test
	public void testWithdrawAmountFailDueToNotEnoughBalance() {
		double currentBalance = 1000.5;
		double newAmount = 2000;
		checkingAccount.setBalance(currentBalance);

		// Act and assert
		assertNull(checkingAccount.withdrawAmount(newAmount));
		assertEquals(currentBalance, checkingAccount.getBalance(), 0.01);
	}

	// *********************************
	// Methods to test free transactions
	// *********************************

	@Test
	public void testFreeTransactionsLimit() {

		// Act
		checkingAccount.depositAmount(0);
		checkingAccount.withdrawAmount(0);

		// Assert
		assertEquals(0, checkingAccount.getFreeTransactions());
	}

	@Test
	public void testBalanceDeductionAfterFreeTransactions() {
		double currentBalance = 1000.5;
		double newAmount = 900.4;
		double expectedBalance = currentBalance - (2 * checkingAccount.getTransacitonFee());
		checkingAccount.setBalance(currentBalance);

		// Act
		checkingAccount.depositAmount(0);
		checkingAccount.withdrawAmount(0);

		// Performing two transactions after free transactions limit
		checkingAccount.withdrawAmount(newAmount);
		checkingAccount.depositAmount(newAmount);

		// Assert
		assertEquals(expectedBalance, checkingAccount.getBalance(), 0.01);
	}

	// *******************************
	// Methods to test transfer amount
	// *******************************

	@Test
	public void testTransferAmountSuccess() {
		int recipientAccountNumber = 22222222;
		double recipientCurrentBalance = 300.3;
		double senderCurrentBalance = 2000;
		double transferAmount = 200.5;
		double expectedSenderBalance = senderCurrentBalance - transferAmount;
		double expectedRecipientBalance = recipientCurrentBalance + transferAmount;
		ArrayList<CheckingAccount> checkingAccountList = new ArrayList<CheckingAccount>();
		Customer recipientCustomer = new Customer("34201-1645784-5", "Ali Ahmad",
				"House no. 109, Street no. 76, G10-2, Islamabad", "03001231234");
		CheckingAccount recipientCheckingAccount = new CheckingAccount(recipientAccountNumber, recipientCustomer, -1);
		checkingAccount.setBalance(senderCurrentBalance);
		checkingAccountList.add(this.checkingAccount);
		recipientCheckingAccount.setBalance(recipientCurrentBalance);
		checkingAccountList.add(recipientCheckingAccount);

		// Act
		boolean transfered = checkingAccount.transferAmount(transferAmount, recipientAccountNumber,
				checkingAccountList);

		// Assert
		assertTrue(transfered);
		assertEquals(expectedSenderBalance, checkingAccount.getBalance(), 0.01);
		assertEquals(expectedRecipientBalance, recipientCheckingAccount.getBalance(), 0.01);
	}

	@Test
	public void testTransferAmountFailDueToNotEnoughBalance() {
		int recipientAccountNumber = 22222222;
		double recipientCurrentBalance = 300.3;
		double senderCurrentBalance = 100;
		double transferAmount = 200.5;
		ArrayList<CheckingAccount> checkingAccountList = new ArrayList<CheckingAccount>();
		Customer recipientCustomer = new Customer("34201-1645784-5", "Ali Ahmad",
				"House no. 109, Street no. 76, G10-2, Islamabad", "03001231234");
		CheckingAccount recipientCheckingAccount = new CheckingAccount(recipientAccountNumber, recipientCustomer, -1);
		checkingAccount.setBalance(senderCurrentBalance);
		checkingAccountList.add(this.checkingAccount);
		recipientCheckingAccount.setBalance(recipientCurrentBalance);
		checkingAccountList.add(recipientCheckingAccount);

		// Act
		boolean transfered = checkingAccount.transferAmount(transferAmount, recipientAccountNumber,
				checkingAccountList);

		// Assert
		assertFalse(transfered);
		assertEquals(senderCurrentBalance, checkingAccount.getBalance(), 0.01);
		assertEquals(recipientCurrentBalance, recipientCheckingAccount.getBalance(), 0.01);
	}

	@Test
	public void testTransferAmountFailDueToInvalidAccountNumber() {
		ArrayList<CheckingAccount> checkingAccountList = new ArrayList<CheckingAccount>();
		checkingAccountList.add(this.checkingAccount);

		// Act
		boolean transfered = checkingAccount.transferAmount(0, 99999999, checkingAccountList);

		// Assert
		assertFalse(transfered);
	}

	@Test
	public void testTransferAmountFailDueToSameAccountNumber() {
		ArrayList<CheckingAccount> checkingAccountList = new ArrayList<CheckingAccount>();
		checkingAccountList.add(this.checkingAccount);

		// Act
		boolean transfered = checkingAccount.transferAmount(0, checkingAccount.getAccountNumber(), checkingAccountList);

		// Assert
		assertFalse(transfered);
	}

	// *********************************************
	// Methods to test transaction statement display
	// *********************************************

	@Test
	public void testPrintStatementSuccess() {
		checkingAccount.depositAmount(1000);
		checkingAccount.withdrawAmount(70);

		// Act
		boolean printed = checkingAccount.printStatement();

		// Assert
		assertTrue(printed);
	}

	@Test
	public void testPrintStatementFail() {

		// Act
		boolean printed = checkingAccount.printStatement();

		// Assert
		assertFalse(printed);
	}
}