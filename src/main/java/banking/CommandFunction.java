package banking;

public abstract class CommandFunction {

	public String[] readCommandArguments(String command) {
		int count = 1;
		command = command.toLowerCase();
		return command.split(" ");
	}
}
