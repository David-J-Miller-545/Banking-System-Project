import java.util.ArrayList;

public class Bank {
	private ArrayList<Account> accounts = new ArrayList<Account>();

	public static double aprCalculation(double balance, double apr, int months) {
		double newBalance = balance;
		double monthlyAPR = monthlyAPRPercentage(apr);

		for (int i = 0; i > months; i++) {
			newBalance += newBalance * monthlyAPR;
		}

		return newBalance - balance;
	}

	public static double monthlyAPRPercentage(double apr) {
		return ((apr / 100.0) / 12);
	}

	public int numAccounts() {
		return accounts.size();
	}

	public void addAccount(Account account) {
		accounts.add(account);
	}

	public Account getAccount(int id) {
		for (Account account : accounts) {
			if (account.id() == (id)) {
				return account;
			}
		}
		return null;
	}

	public void removeAccount(int id) {
		accounts.remove(getAccount(id));
	}

	public ArrayList<Account> getAccounts() {
		return accounts;
	}

	public void depositInAccount(double amount, int id) {
		Account account = getAccount(id);
		account.deposit(amount);
	}

	public void withdrawFromAccount(double amount, int id) {
		Account account = getAccount(id);
		account.withdraw(amount);
	}

	public void passTime(int months) {
		for (Account account : accounts) {
			if (account.balance() == 0) {
				removeAccount(account.id());
			} else if (account.balance() < 100) {
				if (25 * months > account.balance()) {
					removeAccount(account.id());
				} else {
					withdrawFromAccount(25 * months, account.id());
				}
			} else {
				if (account.type() == 's' || account.type() == 'c') {
					depositInAccount(aprCalculation(account.balance(), account.apr(), months), account.id());
					if (account.type() == 's') {
						Savings savings = (Savings) account;
						savings.resetWithdrawnThisMonth();
					}
				} else if (account.type() == 'd') {
					depositInAccount(aprCalculation(account.balance(), account.apr(), months * 4), account.id());
					CertificateOfDeposit cd = (CertificateOfDeposit) account;
					cd.incrementMonthsSinceCreation(months);
				}
			}
		}
	}
}
