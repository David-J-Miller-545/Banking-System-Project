public abstract class Account {
	private double balance;
	private double apr;
	private int id;

	public Account(int id, double balance, double apr) {
		this.balance = balance;
		this.apr = apr;
		this.id = id;
	}

	public double balance() {
		return balance;
	}

	public double apr() {
		return apr;
	}

	public void deposit(double amount) {
		balance += amount;
	}

	public void withdraw(double amount) {
		if ((balance - amount) >= 0) {
			balance -= amount;
		}
	}

	public int id() {
		return id;
	}
}
