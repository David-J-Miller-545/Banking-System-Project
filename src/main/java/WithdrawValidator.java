public class WithdrawValidator {
	private Bank bank;

	public WithdrawValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validateWithdrawAmount(Account account, double withdrawAmount) {
		if (!(withdrawAmount < 0)) {
			if (account.type() == 's' && withdrawAmount <= 1000) {
				Savings savings = (Savings) account;
				return !(savings.hasWithdrawnThisMonth());
			} else if (account.type() == 'c' && withdrawAmount <= 400) {
				return true;
			} else if (account.type() == 'd' && withdrawAmount >= account.balance()) {
				CertificateOfDeposit cd = (CertificateOfDeposit) account;
				return (cd.monthsSinceCreation() >= 12);
			} else {
				return false;
			}
		} else {
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
