import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {
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

	// Command Layout: "transfer (existingFromID) (existingToID) (withdrawAmount)

	@Test
	public void valid_if_first_argument_is_transfer_keyword() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	// ---Existing ID Tests---

	@Test
	public void valid_if_first_given_8_digit_id_belongs_to_an_account_within_the_bank() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_first_given_8_digit_id_does_not_belong_to_an_account_within_the_bank() {
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void valid_if_second_given_8_digit_id_belongs_to_an_account_within_the_bank() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_second_given_8_digit_id_does_not_belong_to_an_account_within_the_bank() {
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	// ---Argument Count Tests---

	@Test
	public void valid_if_command_contains_4_arguments() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_given_more_than_4_arguments() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("transfer 12345678 23456789 500 Nope"));
	}

	@Test
	public void invalid_if_given_less_than_4_arguments() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("transfer 12345678 23456789"));
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
	public void invalid_if_given_id_belongs_to_a_cd_account() {
		// setup CD
		assertFalse(commandValidator.validate("FILL IN"));
	}

	// ---Withdraw Amount Tests---

	@Test
	public void invalid_if_transfer_amount_is_negative() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("transfer 12345678 23456789 -0.01"));
	}

	@Test
	public void valid_if_transfer_amount_is_0() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate("withdraw 12345678 23456789 0"));
	}

	@Test
	public void invalid_if_withdraw_amount_from_a_savings_account_is_over_1000() {
		defaultSavingsAccountTestSetUp();
		assertFalse(commandValidator.validate("withdraw 12345678 1000.01"));
	}

	@Test
	public void valid_if_withdraw_amount_from_a_savings_account_is_1000() {
		defaultSavingsAccountTestSetUp();
		assertTrue(commandValidator.validate("withdraw 12345678 1000"));
	}

	@Test
	public void valid_if_withdraw_amount_from_a_savings_account_is_under_1000() {
		defaultSavingsAccountTestSetUp();
		assertTrue(commandValidator.validate("withdraw 12345678 999.99"));
	}

	@Test
	public void invalid_if_withdraw_amount_from_a_checking_account_is_over_400() {
		defaultCheckingAccountTestSetUp();
		assertFalse(commandValidator.validate("withdraw 12345678 400.01"));
	}

	@Test
	public void valid_if_withdraw_amount_from_a_checking_account_is_400() {
		defaultCheckingAccountTestSetUp();
		assertTrue(commandValidator.validate("withdraw 12345678 400"));
	}

	@Test
	public void valid_if_withdraw_amount_from_a_checking_account_is_under_400() {
		defaultCheckingAccountTestSetUp();
		assertTrue(commandValidator.validate("withdraw 12345678 399.99"));
	}

}
