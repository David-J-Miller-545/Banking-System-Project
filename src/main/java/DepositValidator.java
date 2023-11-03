public class DepositValidator extends CommandValidator {

	public DepositValidator(Bank bank) {
		super(bank);
	}

	public boolean validate(String command) {
		String[] arguments = readCommandArguments(command);
		try {
			String commandKeyword = arguments[0];
			int id = Integer.parseInt(arguments[1]);
			double depositAmount = Double.parseDouble(arguments[2]);

			if (arguments[0].equals("deposit")) {
				if (validateID(id) && accountExists(id)) {

					if (!(depositAmount < 0)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
