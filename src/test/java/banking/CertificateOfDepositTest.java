package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CertificateOfDepositTest {

	private CertificateOfDeposit account;

	@BeforeEach
	public void setUp() {
		account = new CertificateOfDeposit(10000001, 10, 0);
	}

	@Test
	public void certificate_of_deposit_when_created_has_supplied_starting_balance() {
		assertEquals(10, account.balance());
	}

	@Test
	public void certificate_of_deposit_is_0_months_old_upon_creation() {
		assertEquals(0, account.monthsSinceCreation());
	}

	@Test
	public void certificate_of_deposit_is_1_month_old_if_incremented_after_creation() {
		assertEquals(0, account.monthsSinceCreation());
		account.incrementMonthsSinceCreation();
		assertEquals(1, account.monthsSinceCreation());
	}

	@Test
	public void certificate_of_deposit_is_2_months_old_if_incremented_after_being_1_month_old() {
		assertEquals(0, account.monthsSinceCreation());
		account.incrementMonthsSinceCreation();
		assertEquals(1, account.monthsSinceCreation());
		account.incrementMonthsSinceCreation();
		assertEquals(2, account.monthsSinceCreation());
	}
}