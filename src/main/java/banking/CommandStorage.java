package banking;

import java.util.ArrayList;

public class CommandStorage {
	private ArrayList<String> invalidCommands = new ArrayList<String>();

	public void store(String command) {
		invalidCommands.add(command);
	}

	public ArrayList<String> get() {
		return invalidCommands;
	}
}
