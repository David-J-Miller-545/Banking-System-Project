public class WithdrawProcessor {
	private Bank bank;

	public WithdrawProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		int id = Integer.parseInt(arguments[1]);
		double amount = Double.parseDouble(arguments[2]);
		bank.withdrawFromAccount(amount, id);
	}
}
