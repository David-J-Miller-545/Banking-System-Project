import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SavingsTest {

	private Savings account;

	@Test
	public void savings_when_created_has_0_balance() {
		account = new Savings(10000001, 0);
		assertEquals(0, account.balance());
	}
}
