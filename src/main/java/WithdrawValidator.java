public class WithdrawValidator {
	private Bank bank;

	public WithdrawValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String[] arguments) {
		try {
			int id = Integer.parseInt(arguments[1]);
			double withdrawAmount = Double.parseDouble(arguments[2]);

			Account account = bank.getAccount(id);
			if (arguments.length == 3) {
				if (account != null) {
					if (!(withdrawAmount < 0)) {
						if (account.type().equals("savings") && withdrawAmount <= 1000) {
							return true;
						} else if (account.type().equals("checking") && withdrawAmount <= 400) {
							return true;
						} else if (account.type().equals("cd") && withdrawAmount >= account.balance()) {
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
