package banking;

public class CreateValidator {

	private Bank bank;

	public CreateValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validateIDLength(String id) {
		return (id.length() == 8);
	}

	public boolean accountExists(int id) {
		return (bank.getAccount(id) != null) ? true : false;
	}

	public boolean validateCheckingOrSavings(String[] arguments, double apr) {
		if (arguments.length != 4) {
			return false;
		}

		if (validateIdAndAPR(arguments[2], apr)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean validateCD(String[] arguments, double apr) {
		if (arguments.length != 5) {
			return false;
		}

		if (validateIdAndAPR(arguments[2], apr)) {
			double balance = Double.parseDouble(arguments[4]);
			return (1000 <= balance && balance <= 10000);
		} else {
			return false;
		}
	}

	public boolean validateIdAndAPR(String idArg, double apr) {
		int id = Integer.parseInt(idArg);
		if (validateIDLength(idArg) && !accountExists(id)) {
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
			Integer.parseInt(arguments[2]);
			double apr = Double.parseDouble(arguments[3]);

			if (accountType.equals("savings") || accountType.equals("checking")) {
				return validateCheckingOrSavings(arguments, apr);
			} else if (accountType.equals("cd")) {
				return validateCD(arguments, apr);
			} else { // banking.Account type is not checking, savings, or cd
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}
}
