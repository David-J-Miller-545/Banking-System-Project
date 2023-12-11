package banking;

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
			numAccounts = processAccounts(numAccounts, minimumBalance, minimumBalanceFee);
		}
	}

	public int processAccounts(int numAccounts, double minimumBalance, double minimumBalanceFee) {
		for (int i = 0; i < numAccounts; i++) {
			Account account = bank.getAccounts().get(i);
			if (account.balance() == 0) {
				i--;
				numAccounts--;
				bank.removeAccount(account.id());
				account = null;
			}
			if (account != null) {
				processAccountBalance(account, minimumBalance, minimumBalanceFee);
			}
		}
		return numAccounts;
	}

	public void processAccountBalance(Account account, double minimumBalance, double minimumBalanceFee) {
		if (account.balance() < minimumBalance) {
			bank.withdrawFromAccount(minimumBalanceFee, account.id());
		}
		accrueInterest(account);
	}

	public void accrueInterest(Account account) {
		if (account.type() == 's' || account.type() == 'c') {
			bank.depositInAccount(bank.aprCalculation(account.balance(), account.apr(), 1), account.id());
			if (account.type() == 's') {
				processSavings(account);
			}
		} else if (account.type() == 'd') {
			bank.depositInAccount(bank.aprCalculation(account.balance(), account.apr(), 4), account.id());
			processCD(account);
		}
	}

	public void processSavings(Account account) {
		Savings savings = (Savings) account;
		savings.resetWithdrawnThisMonth();
	}

	public void processCD(Account account) {
		CertificateOfDeposit cd = (CertificateOfDeposit) account;
		cd.incrementMonthsSinceCreation();
	}
}
