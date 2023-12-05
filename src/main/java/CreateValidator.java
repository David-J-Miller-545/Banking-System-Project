public class CreateValidator {

	private Bank bank;

	public CreateValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validateID(int id) {
		return (Integer.toString(id).length() == 8);
	}

	public boolean accountExists(int id) {
		return (bank.getAccount(id) != null) ? true : false;
	}

	public boolean validateIdAndAPR(int id, double apr) {
		if (validateID(id) && !accountExists(id)) {
			return validateAPR(apr);
		} else {
			return false;
		}
	}

	public boolean validateAPR(double apr) {
		return (0 <= apr && apr <= 10) ? true : false;
	}

	public boolean validate(String[] arguments) {
		try {
			String accountType = arguments[1];
			int id = Integer.parseInt(arguments[2]);
			double apr = Double.parseDouble(arguments[3]);

			if (accountType.equals("savings") || accountType.equals("checking")) {
				if (arguments.length == 4) {
					if (validateIdAndAPR(id, apr)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}

			} else if (accountType.equals("cd")) {
				if (arguments.length == 5) {
					if (validateIdAndAPR(id, apr)) {
						double balance = Double.parseDouble(arguments[4]);
						if (1000 <= balance && balance <= 10000) {
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
			} else { // Account type is not checking, savings, or cd
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
}
