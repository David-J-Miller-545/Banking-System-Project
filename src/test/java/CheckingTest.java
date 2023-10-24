import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CheckingTest {

	private Checking account;

	@Test
	public void checking_when_created_has_0_balance() {
		account = new Checking(0);
		assertEquals(0, account.balance());
	}
}
