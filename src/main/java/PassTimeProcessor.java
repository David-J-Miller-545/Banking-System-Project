public class PassTimeProcessor {
	private Bank bank;

	public PassTimeProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		int months = Integer.parseInt(arguments[1]);
		bank.passTime(months);
	}

}
