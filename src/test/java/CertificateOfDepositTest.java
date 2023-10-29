import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CertificateOfDepositTest {

	private CertificateOfDeposit account;

	@Test
	public void certificate_of_deposit_when_created_has_supplied_starting_balance() {
		account = new CertificateOfDeposit(10000001, 10, 0);
		assertEquals(10, account.balance());
	}
}