package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {

	private Savings account;

	@BeforeEach
	public void setUp() {
		account = new Savings(10000001, 0);
	}

	@Test
	public void savings_when_created_has_0_balance() {
		assertEquals(0, account.balance());
	}

	@Test
	public void savings_has_not_withdrawn_this_month_when_created() {
		assertFalse(account.hasWithdrawnThisMonth());
	}

	@Test
	public void savings_has_withdrawn_this_month_if_withdrawn_from() {
		account.withdraw(0);
		assertTrue(account.hasWithdrawnThisMonth());
	}

	@Test
	public void savings_has_not_withdraw_this_month_if_reset() {
		account.withdraw(0);
		assertTrue(account.hasWithdrawnThisMonth());

		account.resetWithdrawnThisMonth();
		assertFalse(account.hasWithdrawnThisMonth());
	}
}
