import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AccountManagementSystemTest {

	private AccountManagementSystem system;
	private Customer customer;

	@Before
	public void intialize() {
		system = new AccountManagementSystem();
		customer = new Customer("34201-1357865-7", "Muhammad Shaheer", "House no. 999, Street no. 43, G10-3, Islamabad",
				"03001234567");
	}

	// *********************************************
	// Methods to test addition of checking accounts
	// *********************************************

	@Test
	public void testAddCheckingAccountSuccess() {

		// Act
		boolean added = system.addCheckingAccount(customer);

		// Assert
		assertTrue(added);
	}

	@Test
	public void testAddCheckingAccountFail() {
		system.addCheckingAccount(customer);

		// Act
		boolean added = system.addCheckingAccount(customer);

		// Assert
		assertFalse(added);
	}

	// ********************************************
	// Methods to test addition of savings accounts
	// ********************************************

	@Test
	public void testAddSavingsAccountSuccess() {

		// Act
		boolean added = system.addSavingsAccount(customer);

		// Assert
		assertTrue(added);
	}

	@Test
	public void testAddSavingsAccountFail() {
		system.addSavingsAccount(customer);

		// Act
		boolean added = system.addSavingsAccount(customer);

		// Assert
		assertFalse(added);
	}

	// ********************************************
	// Methods to test closure of checking accounts
	// ********************************************

	@Test
	public void testCloseCheckingAccountSuccess() {
		system.addCheckingAccount(customer);
		int targetAccountNumber = system.getAccountNumberChecking(customer.getCNIC());

		// Act
		boolean closed = system.closeCheckingAccount(targetAccountNumber);

		// Assert
		assertTrue(closed);
	}

	@Test
	public void testCloseCheckingAccountFail() {
		int targetAccountNumber = 12345678;

		// Act
		boolean closed = system.closeCheckingAccount(targetAccountNumber);

		// Assert
		assertFalse(closed);
	}

	// *******************************************
	// Methods to test closure of savings accounts
	// *******************************************

	@Test
	public void testCloseSavingsAccountSuccess() {
		system.addSavingsAccount(customer);
		int targetAccountNumber = system.getAccountNumberSavings(customer.getCNIC());

		// Act
		boolean closed = system.closeSavingsAccount(targetAccountNumber);

		// Assert
		assertTrue(closed);
	}

	@Test
	public void testCloseSavingsAccountFail() {
		int targetAccountNumber = 12345678;

		// Act
		boolean closed = system.closeSavingsAccount(targetAccountNumber);

		// Assert
		assertFalse(closed);
	}

	// ***************************************
	// Methods to test checking account log in
	// ***************************************

	@Test
	public void testLoginCheckingAccountSuccess() {
		system.addCheckingAccount(customer);
		int targetAccountNumber = system.getAccountNumberChecking(customer.getCNIC());

		// Act and assert
		assertNotNull(system.loginCheckingAccount(targetAccountNumber));
	}

	@Test
	public void testLoginCheckingAccountFail() {
		int targetAccountNumber = 12345678;

		// Act and assert
		assertNull(system.loginCheckingAccount(targetAccountNumber));
	}

	// **************************************
	// Methods to test savings account log in
	// **************************************

	@Test
	public void testLoginSavingsAccountSuccess() {
		system.addSavingsAccount(customer);
		int targetAccountNumber = system.getAccountNumberSavings(customer.getCNIC());

		// Act and assert
		assertNotNull(system.loginSavingsAccount(targetAccountNumber));
	}

	@Test
	public void testLoginSavingsAccountFail() {
		int targetAccountNumber = 12345678;

		// Act and assert
		assertNull(system.loginSavingsAccount(targetAccountNumber));
	}
}