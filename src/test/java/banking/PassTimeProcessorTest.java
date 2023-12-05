package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeProcessorTest {
	private CommandProcessor commandProcessor;
	private Bank bank;
	private Account account;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		account = new Checking(12345678, 0.5);
		account.deposit(750);
		bank.addAccount(account);
	}

	@Test
	public void account_balance_gains_apr_as_months_pass() {
		double oldBalance = account.balance();
		commandProcessor.process("pass 1");
		assertTrue(oldBalance < account.balance());
	}

	@Test
	public void if_account_has_less_than_$100_as_a_month_passes_it_is_hit_with_a_$25_balance_fee() {
		account.withdraw(account.balance()); // Reset setUp balance to 0
		double accountStartingBalance = 99.99;
		double monthlyAPR = (account.apr() / 100) / 12;
		account.deposit(accountStartingBalance);
		commandProcessor.process("pass 1");
		assertEquals((accountStartingBalance - 25) * (1 + monthlyAPR), account.balance());
	}

	@Test
	public void if_account_has_no_money_it_is_removed_from_bank() {
		account.withdraw(account.balance()); // Reset setUp balance to 0
		commandProcessor.process("pass 1");
		assertEquals(0, bank.numAccounts());
	}
}
