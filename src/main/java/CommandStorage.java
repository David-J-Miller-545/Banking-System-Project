import java.util.ArrayList;

public class CommandStorage extends CommandFunction {
	private ArrayList<String> invalidCommands;
	private ArrayList<String> accountHistory;
	private ArrayList<String> output;
	private Bank bank;

	public CommandStorage(Bank bank) {
		super(bank);
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

	public ArrayList<String> getInvalidCommands() {
		return invalidCommands;
	}

	public ArrayList<String> getHistory() {
		return accountHistory;
	}

	public ArrayList<String> get() {
		for (Account account : bank.getAccounts()) {
			output.add(accountStatus(account));
			for (String command : accountHistory) {
				String[] arguments = readCommandArguments(command);
				if (arguments[0].equals("transfer")) {
					if (Integer.parseInt(arguments[1]) == account.id()
							|| Integer.parseInt(arguments[2]) == account.id()) {
						output.add(command);
					}
				} else {
					if (Integer.parseInt(arguments[1]) == account.id()) {
						output.add(command);
					}
				}
			}
		}
		output.addAll(invalidCommands);
		return output;
	}

	public String accountStatus(Account account) {
		String accountType = "";
		String id = "";
		String balance = String.format("%.2f", account.balance());
		String apr = String.format("%.2f", account.apr());

		if (account.type() == 's') {
			accountType = "Savings";
		} else if (account.type() == 'c') {
			accountType = "Checking";
		} else if (account.type() == 'd') {
			accountType = "Cd";
		}
		return accountType + " " + id + " " + balance + " " + apr;
	}
}
