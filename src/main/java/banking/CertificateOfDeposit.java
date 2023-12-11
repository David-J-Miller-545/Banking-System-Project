package banking;

public class CertificateOfDeposit extends Account {
	private int age;

	public CertificateOfDeposit(int id, double balance, double apr) {
		super(id, balance, apr, 'd');
		age = 0;
	}

	public int monthsSinceCreation() {
		return age;
	}

	public void incrementMonthsSinceCreation() {
		age++;
	}
}
