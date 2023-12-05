import java.util.List;

public class MasterControl {
	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (commandValidator.validate(command)) {
				commandProcessor.process(command);
				if (!commandValidator.readCommandArguments(command)[0].equals("create")
						&& !commandValidator.readCommandArguments(command)[0].equals("pass")) {
					commandStorage.storeHistory(command);
				}
			} else {
				commandStorage.storeInvalid(command);
			}
		}
		return commandStorage.get();
	}
}
