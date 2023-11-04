import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {

	private final String DEFAULT_VALID_TEST_SAVINGS_STRING = "create savings 12345678 0.6";
	private final String DEFAULT_VALID_TEST_CHECKING_STRING = "create checking 12345678 0.6";
	private final String DEFAULT_VALID_TEST_CD_STRING = "create cd 12345678 0.6 1000";
	private final String DEFAULT_VALID_GENERAL_TEST_STRING = DEFAULT_VALID_TEST_SAVINGS_STRING;

	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	// Command Layout = "create (accountType) (uniqueID) (aprValue)"
	// [5th argument will be initialBalance only if accountType = "cd"]

	@Test
	public void valid_if_first_argument_is_create_keyword() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	// ---Account Type Tests---

	@Test
	public void valid_if_creating_savings_account() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_TEST_SAVINGS_STRING));
	}

	@Test
	public void valid_if_creating_checking_account() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_TEST_CHECKING_STRING));
	}

	@Test
	public void valid_if_creating_cd_account() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_TEST_CD_STRING));
	}

	@Test
	public void invalid_if_creating_anything_other_than_a_valid_account_type() {
		assertFalse(commandValidator.validate("create notAnAccount 12345678 0.6"));
	}

	// ---Argument Count Tests---

	// Savings
	@Test
	public void invalid_if_given_more_than_4_arguments_for_creating_a_savings_account() {
		assertFalse(commandValidator.validate("create savings 12345678 0.6 Nope"));
	}

	@Test
	public void invalid_if_given_less_than_4_arguments_for_creating_a_savings_account() {
		assertFalse(commandValidator.validate("create savings 12345678"));
	}

	// Checking

	@Test
	public void valid_if_command_contains_4_arguments_for_creating_a_checking_account() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_TEST_CHECKING_STRING));
	}

	@Test
	public void invalid_if_given_more_than_4_arguments_for_creating_a_checking_account() {
		assertFalse(commandValidator.validate("create checking 12345678 0.6 Nope"));
	}

	@Test
	public void invalid_if_given_less_than_4_arguments_for_creating_a_checking_account() {
		assertFalse(commandValidator.validate("create checking 12345678"));
	}

	// CD

	@Test
	public void valid_if_command_contains_5_arguments_for_creating_a_cd_account() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_TEST_CD_STRING));
	}

	@Test
	public void invalid_if_given_more_than_5_arguments_for_creating_a_cd_account() {
		assertFalse(commandValidator.validate("create cd 12345678 0.6 1000 Nope"));
	}

	@Test
	public void invalid_if_given_less_than_5_arguments_for_creating_a_cd_account() {
		assertFalse(commandValidator.validate("create cd 12345678 0.6"));
	}

	// ---Unique ID Tests---

	@Test
	public void valid_if_given_an_8_digit_id() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_given_anything_but_an_8_digit_id() {
		assertFalse(commandValidator.validate("create savings 123456789 0.6"));
	}

	@Test
	public void invalid_if_given_an_existing_id() {
		Account account = new Checking(12345678, 0);
		bank.addAccount(account);
		assertFalse(commandValidator.validate("create savings 12345678 0.6"));
	}

	// ---APR Value Tests---

	@Test
	public void valid_if_given_an_apr_value_that_is_a_positive_float_between_0_and_10() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_given_a_negative_float_for_apr_value() {
		assertFalse(commandValidator.validate("create savings 12345678 -.01"));
	}

	@Test
	public void invalid_if_given_a_positive_float_greater_than_10_for_apr_value() {
		assertFalse(commandValidator.validate("create savings 12345678 10.01"));
	}

	@Test
	public void valid_if_command_contains_4_arguments_for_creating_a_savings_account() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_TEST_SAVINGS_STRING));
	}

	// ---CD Initial Balance Tests---

	@Test
	public void valid_if_given_cd_initial_balance_is_between_1000_and_10000() {
		assertTrue(commandValidator.validate(DEFAULT_VALID_TEST_CD_STRING));
	}

	@Test
	void invalid_if_given_cd_initial_balance_is_less_than_1000() {
		assertFalse(commandValidator.validate("create cd 12345678 0.6 999"));
	}

	@Test
	void invalid_if_given_cd_initial_balance_is_more_than_10000() {
		assertFalse(commandValidator.validate("create cd 12345678 0.6 10001"));
	}

}
