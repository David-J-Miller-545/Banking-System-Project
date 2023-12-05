public class CreateProcessor {
	private Bank bank;

	public CreateProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		int id = Integer.parseInt(arguments[2]);
		double apr = Double.parseDouble(arguments[3]);

		switch (arguments[1]) {
		case "savings":
			bank.addAccount(new Savings(id, apr));
			break;
		case "checking":
			bank.addAccount(new Checking(id, apr));
			break;
		case "cd":
			double balance = Double.parseDouble(arguments[4]);
			bank.addAccount(new CertificateOfDeposit(id, balance, apr));
			break;
		default:
			break;
		}
	}
}
