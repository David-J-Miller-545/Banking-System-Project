public abstract class CommandValidator {
	Bank bank;

	public CommandValidator(Bank bank) {
		this.bank = bank;
	}

	public String[] readCommandArguments(String command) {
		int count = 1;
		command = command.toLowerCase();
		for (int i = 0; i < command.length(); i++) {
			if (command.charAt(i) == ' ') {
				count++;
			}
		}
		return command.split(" ", count);
	}

	public boolean accountExists(int id) {
		return (bank.getAccount(id) instanceof Account) ? true : false;
	}

	public boolean validateID(int id) {
		return ((id / 10000000) < 10 && (id / 10000000) > 0) ? true : false;
	}
}
