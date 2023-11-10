import java.util.ArrayList;

public class CommandStorage {
	private ArrayList<String> invalidCommands = new ArrayList<String>();

	public void storeCommand(String command) {
		invalidCommands.add(command);
	}

	public String[] getCommands() {
		String[] commands = new String[invalidCommands.size()];
		int i = 0;
		for (String command : invalidCommands) {
			commands[i] = command;
			i++;
		}
		return commands;
	}
}
