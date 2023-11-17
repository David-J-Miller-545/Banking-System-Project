public class CertificateOfDeposit extends Account {
	private int age;

	public CertificateOfDeposit(int id, double balance, double apr) {
		super(id, balance, apr, "cd");
		age = 0;
	}

	public int monthsSinceCreation() {
		return age;
	}
}
