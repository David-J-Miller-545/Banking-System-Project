package banking;

public class WithdrawValidator {
	private Bank bank;

	public WithdrawValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validateSavings(Account account, double withdrawAmount) {
		Savings savings = (Savings) account;
		return (withdrawAmount <= 1000 && !(savings.hasWithdrawnThisMonth()));
	}

	public boolean validateCD(Account account, double withdrawAmount) {
		CertificateOfDeposit cd = (CertificateOfDeposit) account;
		return (cd.monthsSinceCreation() >= 12) && (withdrawAmount >= account.balance());
	}

	public boolean validateWithdrawAmount(Account account, double withdrawAmount) {
		if ((withdrawAmount < 0)) {
			return false;
		}

		switch (account.type()) {
		case 's':
			return validateSavings(account, withdrawAmount);
		case 'c':
			return (withdrawAmount <= 400);
		case 'd':
			return validateCD(account, withdrawAmount);
		default:
			return false;
		}
	}

	public boolean validate(String[] arguments) {
		if (arguments.length != 3) {
			return false;
		}
		try {
			int id = Integer.parseInt(arguments[1]);
			double withdrawAmount = Double.parseDouble(arguments[2]);

			Account account = bank.getAccount(id);

			if (account != null) {
				return validateWithdrawAmount(account, withdrawAmount);
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
