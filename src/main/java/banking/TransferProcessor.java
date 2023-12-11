package banking;

public class TransferProcessor {
	private Bank bank;

	public TransferProcessor(Bank bank) {
		this.bank = bank;
	}

	public void process(String[] arguments) {
		int fromID = Integer.parseInt(arguments[1]);
		int toID = Integer.parseInt(arguments[2]);
		double amount = Double.parseDouble(arguments[3]);
		if (bank.getAccount(fromID).balance() < amount) {
			amount = bank.getAccount(fromID).balance();
		}
		bank.withdrawFromAccount(amount, fromID);
		bank.depositInAccount(amount, toID);
	}
}
