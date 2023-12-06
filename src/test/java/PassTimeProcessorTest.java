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
	public void if_account_has_less_than_$100_as_a_month_passes_it_is_hit_with_a_$25_minimum_balance_fee() {
		account.withdraw(account.balance()); // Reset setUp balance to 0
		double accountStartingBalance = 99.99;
		double monthlyAPR = (account.apr() / 100) / 12;
		account.deposit(accountStartingBalance);
		commandProcessor.process("pass 1");
		assertEquals((accountStartingBalance - 25) * (1 + monthlyAPR), account.balance());
	}

	@Test
	public void if_account_has_$100_or_more_as_a_month_passes_it_is_not_hit_with_a_$25_minimum_balance_fee() {
		account.withdraw(account.balance()); // Reset setUp balance to 0
		double accountStartingBalance = 100;
		double monthlyAPR = (account.apr() / 100) / 12;
		account.deposit(accountStartingBalance);
		commandProcessor.process("pass 1");
		assertEquals(accountStartingBalance * (1 + monthlyAPR), account.balance());
	}

	@Test
	public void cd_account_accrues_interest_4_times_in_one_month() {
		bank.removeAccount(account.id()); // THIS SHOULDNT HAVE TO BE HERE
		double oldBalance = 1000;
		Account account2 = new CertificateOfDeposit(12345678, oldBalance, 5);
		double monthlyAPR = (account2.apr() / 100) / 12;

		bank.addAccount(account2);
		commandProcessor.process("pass 1");

		assertEquals((oldBalance * (1 + monthlyAPR) * (1 + monthlyAPR) * (1 + monthlyAPR) * (1 + monthlyAPR)),
				account2.balance());
	}

	@Test
	public void if_account_has_no_money_it_is_removed_from_bank() {
		account.withdraw(account.balance()); // Reset setUp balance to 0
		commandProcessor.process("pass 1");
		assertEquals(0, bank.numAccounts());
	}
}
