public class TransferValidator {
	private Bank bank;
	private DepositValidator depositValidator;
	private WithdrawValidator withdrawValidator;

	public TransferValidator(Bank bank, DepositValidator depositValidator, WithdrawValidator withdrawValidator) {
		this.bank = bank;
		this.depositValidator = depositValidator;
		this.withdrawValidator = withdrawValidator;
	}

	public boolean validateAccounts(String[] arguments, Account sender, Account receiver, double transferAmount) {
		if (sender != null && receiver != null) {
			if (sender.type() == 'd' || receiver.type() == 'd') {
				return false;
			}
			return validateWithdrawingAndDepositing(arguments, sender, transferAmount);
		} else {
			return false;
		}
	}

	public boolean validateWithdrawingAndDepositing(String[] arguments, Account sender, double transferAmount) {
		String[] validateCommandArgs = { "VALIDATE", arguments[1], arguments[3] };
		if (withdrawValidator.validate(validateCommandArgs)) {
			if (sender.balance() < transferAmount) {
				transferAmount = sender.balance();
			}
			validateCommandArgs[1] = arguments[2];
			validateCommandArgs[2] = Double.toString(transferAmount);
			return depositValidator.validate(validateCommandArgs);
		} else {
			return false;
		}
	}

	public boolean validate(String[] arguments) {
		if (arguments.length != 4) {
			return false;
		}
		try {
			Account sender = bank.getAccount(Integer.parseInt(arguments[1]));
			Account receiver = bank.getAccount(Integer.parseInt(arguments[2]));
			double transferAmount = Double.parseDouble(arguments[3]);

			return validateAccounts(arguments, sender, receiver, transferAmount);
		} catch (Exception e) {
			return false;
		}
	}
}