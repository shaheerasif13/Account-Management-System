public class Customer {

	private final String CNIC;
	private String name;
	private String address;
	private String phoneNumber;

	// Parameterized constructor
	public Customer(final String CNIC, String name, String address, String phoneNumber) {
		this.CNIC = CNIC;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	// Method to display customer information
	public void displayCustomerInfo() {
		System.out.println(" Name: " + this.name);
		System.out.println(" CNIC: " + this.CNIC);
		System.out.println(" Address: " + this.address);
		System.out.println(" Phone: " + this.phoneNumber);
	}

	// **************
	// Getter methods
	// **************

	public final String getCNIC() {
		return this.CNIC;
	}

	public final String getName() {
		return this.name;
	}
}