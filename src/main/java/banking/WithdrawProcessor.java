package banking;

public class WithdrawProcessor {
	private Bank bank;

	public WithdrawProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		bank.withdrawFromAccount(Double.parseDouble(arguments[2]), Integer.parseInt(arguments[1]));
	}
}
