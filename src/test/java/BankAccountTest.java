import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BankAccountTest {

	private Savings savings;
	private Checking checking;
	private CertificateOfDeposit certificateOfDeposit;

	@Test
	public void savings_and_checkings_when_created_has_0_balance() {
		savings = new Savings(0);
		checking = new Checking(0);
		assertEquals(0, savings.balance() + checking.balance());

	}

	@Test
	public void certificate_of_deposit_when_created_has_supplied_starting_balance() {
		certificateOfDeposit = new CertificateOfDeposit(10, 0);
		assertEquals(10, certificateOfDeposit.balance());
	}

	@Test
	public void all_accounts_when_created_has_supplied_apr_value() {
		savings = new Savings(5);
		checking = new Checking(5);
		certificateOfDeposit = new CertificateOfDeposit(0, 5);
		assertEquals(15, savings.apr() + checking.apr() + certificateOfDeposit.apr());
	}

	@Test
	public void all_accounts_balances_increases_when_deposited_into() {
		final double TEST_DEPOSIT = 5.50;
		savings = new Savings(0);
		checking = new Checking(0);
		certificateOfDeposit = new CertificateOfDeposit(0, 0);
		savings.deposit(TEST_DEPOSIT);
		checking.deposit(TEST_DEPOSIT);
		certificateOfDeposit.deposit(TEST_DEPOSIT);

		assertEquals(TEST_DEPOSIT * 3, checking.balance() + savings.balance() + certificateOfDeposit.balance());

	}

}
