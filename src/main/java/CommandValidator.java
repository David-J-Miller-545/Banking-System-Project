public class CommandValidator {
	Bank bank;
	CreateValidator createValidator;
	DepositValidator depositValidator;

	public CommandValidator(Bank bank) {
		this.bank = bank;
		createValidator = new CreateValidator(bank);
		depositValidator = new DepositValidator(bank);
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

	public boolean validate(String command) {
		String[] arguments = readCommandArguments(command);
		switch (arguments[0]) {
		case "create":
			return createValidator.validate(arguments);
		case "deposit":
			return depositValidator.validate(arguments);
		default:
			return false;
		}

	}

}
