public class PassTimeValidator {

	private Bank bank;

	public PassTimeValidator(Bank bank) {
		this.bank = bank;
	}

	public boolean validate(String[] arguments) {
		if (arguments.length != 2) {
			return false;
		}
		try {
			int months = Integer.parseInt(arguments[1]);
			return (1 <= months && months <= 60);
		} catch (Exception e) {
			return false;
		}
	}
}