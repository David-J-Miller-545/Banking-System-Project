public class DepositValidator {
	private Bank bank;

	public DepositValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String[] arguments) {
		try {
			int id = Integer.parseInt(arguments[1]);
			double depositAmount = Double.parseDouble(arguments[2]);

			Account account = bank.getAccount(id);
			if (arguments.length == 3) {
				if (account instanceof Savings || account instanceof Checking) {
					if (!(depositAmount < 0)) {
						if (account instanceof Savings && depositAmount <= 2500) {
							return true;
						} else if (account instanceof Checking && depositAmount <= 1000) {
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
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
}
