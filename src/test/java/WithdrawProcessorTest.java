import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawProcessorTest {
	private CommandProcessor commandProcessor;
	private Bank bank;
	private Account account;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		account = new Checking(12345678, .6);
		account.deposit(750);
		bank.addAccount(account);
	}

	@Test
	public void processor_withdraws_money_from_account() {
		commandProcessor.process("withdraw 12345678 500");
		assertEquals(250, account.balance());
	}

	@Test
	public void processor_withdraws_to_0_if_amount_is_greater_than_balance() {
		commandProcessor.process("withdraw 12345678 750.01");
		assertEquals(0, account.balance());
	}
}
