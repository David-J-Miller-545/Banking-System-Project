public abstract class Account {
	private double balance;
	private double apr;
	private int id;
	private char type;

	Account(int id, double balance, double apr, char type) {
		this.balance = balance;
		this.apr = apr;
		this.id = id;
		this.type = type;
	}

	public char type() {
		return type;
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
		if (amount <= balance) {
			balance -= amount;
		} else {
			balance = 0;
		}
	}

	public int id() {
		return id;
	}
}
