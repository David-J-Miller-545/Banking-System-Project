import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferProcessorTest {
	private CommandProcessor commandProcessor;
	private Bank bank;
	private Account fromAccount;
	private Account toAccount;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		fromAccount = new Checking(12345678, .6);
		toAccount = new Checking(23456789, .7);
		fromAccount.deposit(390);
		bank.addAccount(fromAccount);
		bank.addAccount(toAccount);
	}

	@Test
	public void processor_withdraws_money_from_the_first_given_account() {
		commandProcessor.process("transfer 12345678 23456789 350");
		assertEquals(40, fromAccount.balance());
	}

	@Test
	public void processor_withdraws_to_0_if_amount_is_greater_than_balance_from_the_first_given_account() {
		commandProcessor.process("transfer 12345678 23456789 390.01");
		assertEquals(0, fromAccount.balance());
	}

	@Test
	public void processor_deposits_money_to_the_second_given_account() {
		commandProcessor.process("transfer 12345678 23456789 350");
		assertEquals(350, toAccount.balance());
	}

	@Test
	public void processor_deposits_full_balance_of_first_account_into_second_account_if_balance_was_lower_than_the_transfer_amount() {
		commandProcessor.process("transfer 12345678 23456789 400");
		assertEquals(390, toAccount.balance());
	}
}
