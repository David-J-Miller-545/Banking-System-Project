public class CommandValidator extends CommandFunction {
	private CreateValidator createValidator;
	private DepositValidator depositValidator;

	public CommandValidator(Bank bank) {
		super(bank);
		createValidator = new CreateValidator(bank);
		depositValidator = new DepositValidator(bank);
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
