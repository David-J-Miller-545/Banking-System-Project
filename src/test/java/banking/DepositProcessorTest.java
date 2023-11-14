package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DepositProcessorTest {
	private CommandProcessor commandProcessor;
	private Bank bank;
	private Account account;

	@Test
	public void proccesor_deposits_money_in_account() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		account = new Checking(12345678, .6);
		bank.addAccount(account);

		commandProcessor.process("deposit 12345678 500");
		assertEquals(500, account.balance());
	}
}
