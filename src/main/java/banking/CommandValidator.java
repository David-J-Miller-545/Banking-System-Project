package banking;

public class CommandValidator extends CommandFunction {
	private CreateValidator createValidator;
	private DepositValidator depositValidator;
	private WithdrawValidator withdrawValidator;
	private TransferValidator transferValidator;
	private PassTimeValidator passTimeValidator;

	public CommandValidator(Bank bank) {
		createValidator = new CreateValidator(bank);
		depositValidator = new DepositValidator(bank);
		withdrawValidator = new WithdrawValidator(bank);
		transferValidator = new TransferValidator(bank, depositValidator, withdrawValidator);
		passTimeValidator = new PassTimeValidator();
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
		case "transfer":
			return transferValidator.validate(arguments);
		case "pass":
			return passTimeValidator.validate(arguments);
		default:
			return false;
		}

	}

}
