public class CreateProcessor {
	private Bank bank;

	public CreateProcessor(Bank bank) {
		this.bank = bank;
	}

	public String[] readCommandArguments(String command) {
		int count = 1;
		command = command.toLowerCase();
		for (int i = 0; i < command.length(); i++) {
			if (command.charAt(i) == ' ') {
				count++;
			}
		}
		return command.split(" ", count);
	}

	public void process(String command) {
		String[] arguments = readCommandArguments(command);
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
