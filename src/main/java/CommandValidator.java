public class CommandValidator extends CommandFunction {
	private CreateValidator createValidator;
	private DepositValidator depositValidator;
	private WithdrawValidator withdrawValidator;
	private PassTimeValidator passTimeValidator;

	public CommandValidator(Bank bank) {
		super(bank);
		createValidator = new CreateValidator(bank);
		depositValidator = new DepositValidator(bank);
		withdrawValidator = new WithdrawValidator(bank);
		passTimeValidator = new PassTimeValidator(bank);
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
		case "pass":
			return passTimeValidator.validate(arguments);
		default:
			return false;
		}

	}

}
