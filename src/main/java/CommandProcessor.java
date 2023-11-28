public class CommandProcessor extends CommandFunction {
	private CreateProcessor createProcessor;
	private DepositProcessor depositProcessor;
	private WithdrawProcessor withdrawProcessor;
	private PassTimeProcessor passTimeProcessor;

	public CommandProcessor(Bank bank) {
		super(bank);
		createProcessor = new CreateProcessor(bank);
		depositProcessor = new DepositProcessor(bank);
		withdrawProcessor = new WithdrawProcessor(bank);
		passTimeProcessor = new PassTimeProcessor(bank);
	}

	public void process(String command) {
		String[] arguments = readCommandArguments(command);
		switch (arguments[0]) {
		case "create":
			createProcessor.process(arguments);
			break;
		case "deposit":
			depositProcessor.process(arguments);
			break;
		case "withdraw":
			withdrawProcessor.process(arguments);
			break;
		case "pass":
			passTimeProcessor.process(arguments);
			break;
		default:
			break;
		}
	}
}
