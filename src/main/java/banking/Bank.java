package banking;

import java.util.ArrayList;

public class Bank {
	private ArrayList<Account> accounts = new ArrayList<Account>();

	public static double aprCalculation(double balance, double apr, int months) {
		double newBalance = balance;
		double monthlyAPR = monthlyAPRPercentage(apr);

		for (int i = 0; i < months; i++) {
			newBalance *= 1 + monthlyAPR;
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
		Account account = getAccount(id);
		accounts.remove(account);
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
}
