public class PassTimeProcessor {
	private Bank bank;

	public PassTimeProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		int months = Integer.parseInt(arguments[1]);
		passTime(months);

	}

	private void passTime(int months) {
		double minimumBalance = 100.00;
		double minimumBalanceFee = 25.00;
		int numAccounts = bank.getAccounts().size();

		for (int i = 0; i < numAccounts; i++) {
			Account account = bank.getAccounts().get(i);
			if (account.balance() == 0) {
				i--;
				numAccounts--;
				bank.removeAccount(account.id());
				account = null;
			} else if (account.balance() < minimumBalance) {
				if (minimumBalanceFee * months > account.balance()) {
					bank.removeAccount(account.id());
				} else {
					bank.withdrawFromAccount(minimumBalanceFee * months, account.id());
				}
			}
			if (account != null) {
				if (account.type() == 's' || account.type() == 'c') {
					if (account.balance() < minimumBalance) { // Accounts for apr each month after hit with minimum
																// balance fee
						for (int m = months; m > 0; m--) {
							bank.depositInAccount(bank.aprCalculation(account.balance() + minimumBalanceFee * (m - 1),
									account.apr(), months), account.id());
						}
					} else {
						bank.depositInAccount(bank.aprCalculation(account.balance(), account.apr(), months),
								account.id());
					}
					if (account.type() == 's') {
						Savings savings = (Savings) account;
						savings.resetWithdrawnThisMonth();
					}
				} else if (account.type() == 'd') {
					bank.depositInAccount(bank.aprCalculation(account.balance(), account.apr(), months * 4),
							account.id());
					CertificateOfDeposit cd = (CertificateOfDeposit) account;
					cd.incrementMonthsSinceCreation(months);
				}
			}
		}
	}
}
