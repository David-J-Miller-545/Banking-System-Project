public class CommandProcessor {
	private Bank bank;
	private CreateProcessor createProcessor;
	private DepositProcessor depositProcessor;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
		createProcessor = new CreateProcessor(bank);
		depositProcessor = new DepositProcessor(bank);
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
		switch (arguments[0]) {
		case "create":
			createProcessor.process(arguments);
			break;
		case "deposit":
			depositProcessor.process(arguments);
			break;
		default:
			break;
		}
	}
}
