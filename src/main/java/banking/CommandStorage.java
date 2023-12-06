package banking;

import java.util.ArrayList;

public class CommandStorage extends CommandFunction {
	private ArrayList<String> invalidCommands;
	private ArrayList<String> accountHistory;
	private ArrayList<String> output;
	private Bank bank;

	public CommandStorage(Bank bank) {
		this.bank = bank;
		invalidCommands = new ArrayList<String>();
		accountHistory = new ArrayList<String>();
		output = new ArrayList<String>();
	}

	public void storeInvalid(String command) {
		invalidCommands.add(command);
	}

	public void storeHistory(String command) {
		accountHistory.add(command);
	}

	public ArrayList<String> get() {
		output.clear();
		for (Account account : bank.getAccounts()) {
			output.add(accountStatus(account));
			output.addAll(getAccountHistory(account));
		}
		output.addAll(invalidCommands);
		return output;
	}

	public ArrayList<String> getAccountHistory(Account account) {
		ArrayList<String> thisAccountsHistory = new ArrayList<String>();
		for (String command : accountHistory) {
			if (accountIdIsInCommand(command, account)) {
				thisAccountsHistory.add(command);
			}
		}
		return thisAccountsHistory;
	}

	public boolean accountIdIsInCommand(String command, Account account) {
		String[] arguments = readCommandArguments(command);
		return (Integer.parseInt(arguments[1]) == account.id())
				|| (arguments[0].equals("transfer") && Integer.parseInt(arguments[2]) == account.id());
	}

	public String accountStatus(Account account) {
		String accountType = "";
		String id = formatID(account.id());
		String balance = String.format("%.2f", account.balance());
		String apr = String.format("%.2f", account.apr());

		if (account.type() == 's') {
			accountType = "banking.Savings";
		} else if (account.type() == 'c') {
			accountType = "banking.Checking";
		} else if (account.type() == 'd') {
			accountType = "Cd";
		}
		return accountType + " " + id + " " + balance + " " + apr;
	}

	public String formatID(int idNum) {
		String idAsString = Integer.toString(idNum);
		String formattedID = "";

		while (8 - idAsString.length() - formattedID.length() > 0) {
			formattedID += "0";
		}

		formattedID += idAsString;

		return formattedID;
	}
}
