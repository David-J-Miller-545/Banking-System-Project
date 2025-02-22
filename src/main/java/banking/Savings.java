package banking;

public class Savings extends Account {
	private boolean withdrawnThisMonth;

	public Savings(int id, double apr) {
		super(id, 0, apr, 's');
		withdrawnThisMonth = false;
	}

	@Override
	public void withdraw(double amount) {
		super.withdraw(amount);
		withdrawnThisMonth = true;
	}

	public boolean hasWithdrawnThisMonth() {
		return withdrawnThisMonth;
	}

	public void resetWithdrawnThisMonth() {
		withdrawnThisMonth = false;
	}
}
