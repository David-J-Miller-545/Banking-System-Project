import java.util.Random;

public abstract class Account {
	Random rnd = new Random();
	private double balance;
	private double apr;
	private int id;

	public Account(double balance, double apr) {
		this.balance = balance;
		this.apr = apr;
		id = 10000000 + rnd.nextInt(90000000);
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
