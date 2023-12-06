package banking;

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

	public boolean validateCheckingOrSavings(String[] arguments, int id, double apr) {
		if (arguments.length != 4) {
			return false;
		}

		if (validateIdAndAPR(id, apr)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validateCD(String[] arguments, int id, double apr) {
		if (arguments.length != 5) {
			return false;
		}

		if (validateIdAndAPR(id, apr)) {
			double balance = Double.parseDouble(arguments[4]);
			return (1000 <= balance && balance <= 10000);
		} else {
			return false;
		}
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
				return validateCheckingOrSavings(arguments, id, apr);
			} else if (accountType.equals("cd")) {
				return validateCD(arguments, id, apr);
			} else { // banking.Account type is not checking, savings, or cd
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
}
