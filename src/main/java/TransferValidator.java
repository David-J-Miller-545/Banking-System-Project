public class TransferValidator {
	private Bank bank;
	private DepositValidator depositValidator;
	private WithdrawValidator withdrawValidator;

	public TransferValidator(Bank bank, DepositValidator depositValidator, WithdrawValidator withdrawValidator) {
		this.bank = bank;
		this.depositValidator = depositValidator;
		this.withdrawValidator = withdrawValidator;
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
			int fromID = Integer.parseInt(arguments[1]);
			int toID = Integer.parseInt(arguments[2]);
			double transferAmount = Double.parseDouble(arguments[3]);
			Account sender = bank.getAccount(fromID);
			Account receiver = bank.getAccount(toID);

			if (sender != null && receiver != null) {
				if (sender.type() == 'd' || receiver.type() == 'd') {
					return false;
				}
				return validateWithdrawingAndDepositing(arguments, sender, transferAmount);
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
}