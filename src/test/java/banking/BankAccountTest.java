package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

	private final double TEST_BALANCE = 10;
	private final double TEST_APR = 5;
	private final double TEST_TRANSACTION = 2.5;
	private final int TEST_ID = 10000001;
	private Account account;

	@Test
	public void account_when_created_has_supplied_apr_value() {
		account = new Savings(TEST_ID, TEST_APR);
		assertEquals(TEST_APR, account.apr());
	}

	@Test
	public void account_balance_increases_when_deposited_into() {
		account = new Savings(TEST_ID, 0);
		account.deposit(TEST_TRANSACTION);

		assertEquals(TEST_TRANSACTION, account.balance());

	}

	@Test
	public void account_balance_decreases_when_withdrawn_from() {
		account = new Savings(TEST_ID, 0);
		account.deposit(TEST_BALANCE);
		account.withdraw(TEST_TRANSACTION);

		assertEquals(TEST_BALANCE - TEST_TRANSACTION, account.balance());
	}

	@Test
	public void account_balance_decreases_to_zero_when_withdrawn_more_than_balance() {
		account = new Savings(TEST_ID, 0);
		account.deposit(0.01);
		account.withdraw(TEST_TRANSACTION);

		assertEquals(0, account.balance());
	}

	@Test
	public void account_when_deposited_twice_into_works() {
		account = new Savings(TEST_ID, 0);
		account.deposit(TEST_TRANSACTION);
		account.deposit(TEST_TRANSACTION);

		assertEquals(TEST_TRANSACTION * 2, account.balance());
	}

	@Test
	public void account_when_withdrawn_from_twice_works() {
		account = new Savings(TEST_ID, 0);
		account.deposit(TEST_BALANCE);
		account.withdraw(TEST_TRANSACTION);
		account.withdraw(TEST_TRANSACTION);

		assertEquals(TEST_BALANCE - (TEST_TRANSACTION * 2), account.balance());
	}
}