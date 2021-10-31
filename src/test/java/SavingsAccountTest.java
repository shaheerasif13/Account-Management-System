import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class SavingsAccountTest {

	private SavingsAccount savingsAccount;
	private Customer customer;

	@Before
	public void initialize() {
		customer = new Customer("34201-1357865-7", "Muhammad Shaheer", "House no. 999, Street no. 43, G10-3, Islamabad",
				"03001234567");
		savingsAccount = new SavingsAccount(11111111, customer);
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
		ArrayList<SavingsAccount> savingsAccountList = new ArrayList<SavingsAccount>();
		Customer recipientCustomer = new Customer("34201-1645784-5", "Ali Ahmad",
				"House no. 109, Street no. 76, G10-2, Islamabad", "03001231234");
		SavingsAccount recipientSavingsAccount = new SavingsAccount(recipientAccountNumber, recipientCustomer);
		savingsAccount.setBalance(senderCurrentBalance);
		savingsAccountList.add(this.savingsAccount);
		recipientSavingsAccount.setBalance(recipientCurrentBalance);
		savingsAccountList.add(recipientSavingsAccount);

		// Act
		boolean transfered = savingsAccount.transferAmount(transferAmount, recipientAccountNumber, savingsAccountList);

		// Assert
		assertTrue(transfered);
		assertEquals(expectedSenderBalance, savingsAccount.getBalance(), 0.01);
		assertEquals(expectedRecipientBalance, recipientSavingsAccount.getBalance(), 0.01);
	}

	@Test
	public void testTransferAmountFailDueToNotEnoughBalance() {
		int recipientAccountNumber = 22222222;
		double recipientCurrentBalance = 300.3;
		double senderCurrentBalance = 100;
		double transferAmount = 200.5;
		ArrayList<SavingsAccount> savingsAccountList = new ArrayList<SavingsAccount>();
		Customer recipientCustomer = new Customer("34201-1645784-5", "Ali Ahmad",
				"House no. 109, Street no. 76, G10-2, Islamabad", "03001231234");
		SavingsAccount recipientSavingsAccount = new SavingsAccount(recipientAccountNumber, recipientCustomer);
		savingsAccount.setBalance(senderCurrentBalance);
		savingsAccountList.add(this.savingsAccount);
		recipientSavingsAccount.setBalance(recipientCurrentBalance);
		savingsAccountList.add(recipientSavingsAccount);

		// Act
		boolean transfered = savingsAccount.transferAmount(transferAmount, recipientAccountNumber, savingsAccountList);

		// Assert
		assertFalse(transfered);
		assertEquals(senderCurrentBalance, savingsAccount.getBalance(), 0.01);
		assertEquals(recipientCurrentBalance, recipientSavingsAccount.getBalance(), 0.01);
	}

	@Test
	public void testTransferAmountFailDueToInvalidAccountNumber() {
		ArrayList<SavingsAccount> savingsAccountList = new ArrayList<SavingsAccount>();
		savingsAccountList.add(this.savingsAccount);

		// Act
		boolean transfered = savingsAccount.transferAmount(0, 99999999, savingsAccountList);

		// Assert
		assertFalse(transfered);
	}

	@Test
	public void testTransferAmountFailDueToSameAccountNumber() {
		ArrayList<SavingsAccount> savingsAccountList = new ArrayList<SavingsAccount>();
		savingsAccountList.add(this.savingsAccount);

		// Act
		boolean transfered = savingsAccount.transferAmount(0, savingsAccount.getAccountNumber(), savingsAccountList);

		// Assert
		assertFalse(transfered);
	}

	// *********************************
	// Methods to test zakat calculation
	// *********************************

	@Test
	public void testZakatCalculationIfApplicable() {
		double currentBalance = 30000;
		double expectedBalance = 29250;

		savingsAccount.setBalance(currentBalance);

		// Act
		savingsAccount.calculateZakat();

		// Assert
		assertEquals(expectedBalance, savingsAccount.getBalance(), 0.01);
	}

	@Test
	public void testZakatCalculationIfNotApplicable() {
		double currentBalance = 19999;

		savingsAccount.setBalance(currentBalance);

		// Act
		savingsAccount.calculateZakat();

		// Assert
		assertEquals(currentBalance, savingsAccount.getBalance(), 0.01);
	}

	// Method to test interest rate calculation
	@Test
	public void testInterestRateCalculation() {
		double currentBalance = 1000;
		double expectedBalance = 1120;

		savingsAccount.setBalance(currentBalance);

		// Act
		savingsAccount.calculatelnterest(2);

		// Assert
		assertEquals(expectedBalance, savingsAccount.getBalance(), 0.01);
	}
}