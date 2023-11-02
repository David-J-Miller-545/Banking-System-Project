public class CreateValidator {
	private Bank bank;

	public CreateValidator(Bank bank) {
		this.bank = bank;
	}

	public String[] readCommandArguments(String command) {
		int count = 1;
		command = command.toLowerCase();
		for (int i = 0; i < command.length(); i++) {
			if (command.charAt(i) == ' ') {
				count++;
			}
		}
		return command.split(" ", count);
	}

	public boolean validateIdAndAPR(String[] args) {
		if (validateID(args[2])) {
			return validateAPR(args[3]);
		} else {
			return false;
		}
	}

	public boolean validateID(String arg3) {
		try {
			int ID = Integer.parseInt(arg3);
			if ((ID / 10000000) < 10 && (ID / 10000000) > 0) {
				return (bank.getAccount(ID) instanceof Account) ? false : true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validateAPR(String arg4) {
		try {
			double apr = Double.parseDouble(arg4);
			return (0 <= apr && apr <= 10) ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validate(String command) {
		String[] arguments = readCommandArguments(command);
		if (arguments[0].equals("create")) {
			if (arguments[1].equals("savings") || arguments[1].equals("checking")) {
				if (!(arguments.length > 4 || arguments.length < 4)) {
					if (validateIdAndAPR(arguments)) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}

			} else if (arguments[1].equals("cd")) {
				if (!(arguments.length > 5 || arguments.length < 5)) {
					if (validateIdAndAPR(arguments)) {
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
	}
}
