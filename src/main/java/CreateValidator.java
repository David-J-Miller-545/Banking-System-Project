public class CreateValidator {

	public String[] readCommandArguments(String command) {
		int count = 1;
		for (int i = 0; i < command.length(); i++) {
			if (command.charAt(i) == ' ') {
				count++;
			}
		}
		return command.split(" ", count);
	}

	public boolean validate(String command) {
		String[] arguments = readCommandArguments(command);
		if (arguments[0].equals("create")) {
			return true;
		} else {
			return false;
		}
	}
}
