package banking;

public class CommandProcessor extends CommandFunction {
	private CreateProcessor createProcessor;
	private DepositProcessor depositProcessor;
	private WithdrawProcessor withdrawProcessor;
	private TransferProcessor transferProcessor;
	private PassTimeProcessor passTimeProcessor;

	public CommandProcessor(Bank bank) {
		createProcessor = new CreateProcessor(bank);
		depositProcessor = new DepositProcessor(bank);
		withdrawProcessor = new WithdrawProcessor(bank);
		transferProcessor = new TransferProcessor(bank);
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
		case "transfer":
			transferProcessor.process(arguments);
			break;
		case "pass":
			passTimeProcessor.process(arguments);
			break;
		default:
			break;
		}
	}
}
