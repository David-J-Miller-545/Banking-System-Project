public class CommandValidator extends CommandFunction {
	private CreateValidator createValidator;
	private DepositValidator depositValidator;
	private WithdrawValidator withdrawValidator;

	public CommandValidator(Bank bank) {
		super(bank);
		createValidator = new CreateValidator(bank);
		depositValidator = new DepositValidator(bank);
		withdrawValidator = new WithdrawValidator(bank);
	}

	public boolean validate(String command) {
		String[] arguments = readCommandArguments(command);
		switch (arguments[0]) {
		case "create":
			return createValidator.validate(arguments);
		case "deposit":
			return depositValidator.validate(arguments);
		case "withdraw":
			return withdrawValidator.validate(arguments);
		default:
			return false;
		}

	}

}
