public class PassTimeProcessor {
	private Bank bank;

	public PassTimeProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		int months = Integer.parseInt(arguments[1]);
		passTime(months);

	}

	public void passTime(int months) {
		double minimumBalance = 100.00;
		double minimumBalanceFee = 25.00;
		int numAccounts = bank.getAccounts().size();

		for (int m = 0; m < months; m++) {
			for (int i = 0; i < numAccounts; i++) {
				Account account = bank.getAccounts().get(i);
				if (account.balance() == 0) {
					i--;
					numAccounts--;
					bank.removeAccount(account.id());
					account = null;
				} else if (account.balance() < minimumBalance) {
					bank.withdrawFromAccount(minimumBalanceFee, account.id());
				}
				if (account != null) {
					accrueInterest(account);
				}
			}
		}
	}

	public void accrueInterest(Account account) {
		if (account.type() == 's' || account.type() == 'c') {
			bank.depositInAccount(bank.aprCalculation(account.balance(), account.apr(), 1), account.id());
			if (account.type() == 's') {
				Savings savings = (Savings) account;
				if (savings.hasWithdrawnThisMonth()) {
					savings.resetWithdrawnThisMonth();
				}
			}
		} else if (account.type() == 'd') {
			bank.depositInAccount(bank.aprCalculation(account.balance(), account.apr(), 4), account.id());
			CertificateOfDeposit cd = (CertificateOfDeposit) account;
			cd.incrementMonthsSinceCreation();
		}
	}
}
