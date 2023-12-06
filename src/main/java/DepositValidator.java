public class DepositValidator {
	private Bank bank;

	public DepositValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validateDepositAmount(Account account, double depositAmount) {
		if (depositAmount < 0) {
			return false;
		}
		return ((account.type() == 's' && depositAmount <= 2500) || (account.type() == 'c' && depositAmount <= 1000));
	}

	public boolean validate(String[] arguments) {
		if (arguments.length != 3) {
			return false;
		}

		try {
			int id = Integer.parseInt(arguments[1]);
			double depositAmount = Double.parseDouble(arguments[2]);

			Account account = bank.getAccount(id);

			if (account != null && (account.type() == 's' || account.type() == 'c')) {
				return validateDepositAmount(account, depositAmount);
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
