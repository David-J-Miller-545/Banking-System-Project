public class DepositProcessor {
	private Bank bank;

	public DepositProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		bank.depositInAccount(Double.parseDouble(arguments[2]), Integer.parseInt(arguments[1]));
	}
}
