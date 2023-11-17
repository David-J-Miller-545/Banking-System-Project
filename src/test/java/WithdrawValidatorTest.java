import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
	private final String DEFAULT_VALID_GENERAL_TEST_STRING = "withdraw 12345678 250";
	Account account;
	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	public void defaultGeneralAccountTestSetUp() {
		defaultCheckingAccountTestSetUp();
	}

	public void defaultCheckingAccountTestSetUp() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		account.deposit(750);
	}

	public void defaultSavingsAccountTestSetUp() {
		account = new Savings(12345678, .5);
		bank.addAccount(account);
		account.deposit(750);
	}

	// Command Layout: "deposit (existingID) (depositAmount)"

	@Test
	public void valid_if_first_argument_is_withdraw_keyword() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	// ---Existing ID Tests---

	@Test
	public void valid_if_given_8_digit_id_belongs_to_an_account_within_the_bank() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_given_8_digit_id_does_not_belong_to_an_account_within_the_bank() {
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	// ---Argument Count Tests---

	@Test
	public void valid_if_command_contains_3_arguments() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_given_more_than_3_arguments() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("withdraw 12345678 500 Nope"));
	}

	@Test
	public void invalid_if_given_less_than_3_arguments() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("withdraw 12345678"));
	}

	// ---ID Account Type Tests---

	@Test
	public void valid_if_given_id_belong_to_a_checking_account() {
		defaultCheckingAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void valid_if_given_id_belongs_to_a_savings_account() {
		defaultSavingsAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void valid_if_given_id_belongs_to_a_cd_account() {
		account = new CertificateOfDeposit(12345678, 1200.50, .5);
		bank.addAccount(account);
		assertTrue(commandValidator.validate("withdraw 12345678 1200.5"));
	}

	// ---Withdraw Amount Tests---

}
