public abstract class CommandFunction {
	private Bank bank;

	public CommandFunction(Bank bank) {
		this.bank = bank;
	}

	public String[] readCommandArguments(String command) {
		int count = 1;
		command = command.toLowerCase();
		return command.split(" ");
	}
}
