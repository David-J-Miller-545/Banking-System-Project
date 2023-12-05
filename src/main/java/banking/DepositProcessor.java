package banking;

public class DepositProcessor {
	private Bank bank;

	public DepositProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		int id = Integer.parseInt(arguments[1]);
		double amount = Double.parseDouble(arguments[2]);
		bank.depositInAccount(amount, id);
	}
}
