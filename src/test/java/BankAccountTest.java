import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

	private final double TEST_BALANCE = 10;
	private final double TEST_APR = 5;
	private final double TEST_TRANSACTION = 2.5;
	private Account account;

	@Test
	public void account_balance_when_withdrawn_from_does_not_go_below_zero() {
		account = new Savings(0);
		account.withdraw(TEST_TRANSACTION);

		assertEquals(0, account.balance());
	}

	@Test
	public void account_when_created_has_supplied_apr_value() {
		account = new Savings(TEST_APR);
		assertEquals(TEST_APR, account.apr());
	}

	@Test
	public void account_balance_increases_when_deposited_into() {
		account = new Savings(0);
		account.deposit(TEST_TRANSACTION);

		assertEquals(TEST_TRANSACTION, account.balance());

	}

	@Test
	public void account_balance_decreases_when_withdrawn_from() {
		account = new Savings(0);
		account.deposit(TEST_BALANCE);
		account.withdraw(TEST_TRANSACTION);

		assertEquals(TEST_BALANCE - TEST_TRANSACTION, account.balance());
	}

	@Test
	public void account_when_deposited_twice_into_works() {
		account = new Savings(0);
		account.deposit(TEST_TRANSACTION);
		account.deposit(TEST_TRANSACTION);

		assertEquals(TEST_TRANSACTION * 2, account.balance());
	}

	@Test
	public void account_when_withdrawn_from_twice_works() {
		account = new Savings(0);
		account.deposit(TEST_BALANCE);
		account.withdraw(TEST_TRANSACTION);
		account.withdraw(TEST_TRANSACTION);

		assertEquals(TEST_BALANCE - (TEST_TRANSACTION * 2), account.balance());
	}
}