public abstract class Account {
	private double balance;
	private double apr;

	public Account(double balance, double apr) {
		this.balance = balance;
		this.apr = apr;
	}

	public double balance() {
		return balance;
	}

	public double apr() {
		return apr;
	}
}
