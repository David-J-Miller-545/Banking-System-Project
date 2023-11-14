package banking;

import java.util.ArrayList;

public class Bank {
	private ArrayList<Account> accounts = new ArrayList<Account>();

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

	public void depositInAccount(double amount, int id) {
		Account account = getAccount(id);
		account.deposit(amount);
	}

	public void withdrawFromAccount(double amount, int id) {
		Account account = getAccount(id);
		account.withdraw(amount);
	}
}
