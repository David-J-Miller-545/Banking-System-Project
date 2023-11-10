public abstract class CommandFunction {
	private Bank bank;

	public CommandFunction(Bank bank) {
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
}
