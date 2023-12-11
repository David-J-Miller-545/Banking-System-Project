package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateProcessorTest {
	private CommandProcessor commandProcessor;
	private Bank bank;

	@BeforeEach
	public void set_up() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
	}

	@Test
	public void processor_creates_new_account_in_bank() {
		commandProcessor.process("create savings 12345678 0.6");
		assertEquals(1, bank.numAccounts());
	}

	@Test
	public void processor_makes_new_account_with_given_id() {
		commandProcessor.process("create savings 12345678 0.6");
		assertTrue(bank.getAccount(12345678) instanceof Account);
	}

	@Test
	public void processor_makes_new_account_with_given_apr() {
		commandProcessor.process("create savings 12345678 0.6");
		assertEquals(.6, bank.getAccount(12345678).apr());
	}

	@Test
	public void processor_creates_new_savings_account() {
		commandProcessor.process("create savings 12345678 0.6");
		assertTrue(bank.getAccount(12345678) instanceof Savings);
	}

	@Test
	public void processor_creates_new_checking_account() {
		commandProcessor.process("create checking 12345678 0.6");
		assertTrue(bank.getAccount(12345678) instanceof Checking);
	}

	@Test
	public void processor_creates_new_cd_account() {
		commandProcessor.process("create cd 12345678 0.6 1000");
		assertTrue(bank.getAccount(12345678) instanceof CertificateOfDeposit);
	}

	@Test
	public void processor_creates_new_cd_with_given_balance() {
		commandProcessor.process("create cd 12345678 0.6 1000");
		assertEquals(1000, bank.getAccount(12345678).balance());
	}
}
