package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	private final String DEFAULT_VALID_GENERAL_TEST_STRING = "deposit 12345678 500";

	private CommandValidator commandValidator;
	private Bank bank;
	private Account account;

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
	}

	public void defaultSavingsAccountTestSetUp() {
		account = new Savings(12345678, .5);
		bank.addAccount(account);
	}

	// Command Layout: "deposit (existingID) (depositAmount)"

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

	@Test
	public void invalid_if_given_a_num_number_for_id() {
		assertFalse(commandValidator.validate("deposit aBcDeFgH 500"));
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
		assertFalse(commandValidator.validate("deposit 12345678 500 Nope"));
	}

	@Test
	public void invalid_if_given_less_than_3_arguments() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("deposit 12345678"));
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
		account = new CertificateOfDeposit(12345678, 1200.50, .5);
		bank.addAccount(account);
		assertFalse(commandValidator.validate("deposit 12345678 500"));
	}

	// ---Deposit Amount Tests---

	@Test
	public void invalid_if_given_a_num_number_for_deposit_amount() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("deposit 12345678 AbC"));
	}

	@Test
	public void invalid_if_deposit_amount_is_negative() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("deposit 12345678 -0.01"));
	}

	@Test
	public void valid_if_deposit_amount_is_0() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate("deposit 12345678 0"));
	}

	@Test
	public void invalid_if_deposit_amount_into_a_savings_account_is_over_2500() {
		defaultSavingsAccountTestSetUp();
		assertFalse(commandValidator.validate("deposit 12345678 2500.01"));
	}

	@Test
	public void valid_if_deposit_amount_into_a_savings_account_is_2500() {
		defaultSavingsAccountTestSetUp();
		assertTrue(commandValidator.validate("deposit 12345678 2500"));
	}

	@Test
	public void valid_if_deposit_amount_into_a_savings_account_is_under_2500() {
		defaultSavingsAccountTestSetUp();
		assertTrue(commandValidator.validate("deposit 12345678 2499.99"));
	}

	@Test
	public void invalid_if_deposit_amount_into_a_checking_account_is_over_1000() {
		defaultCheckingAccountTestSetUp();
		assertFalse(commandValidator.validate("deposit 12345678 1000.01"));
	}

	@Test
	public void valid_if_deposit_amount_into_a_checking_account_is_1000() {
		defaultCheckingAccountTestSetUp();
		assertTrue(commandValidator.validate("deposit 12345678 1000"));
	}

	@Test
	public void valid_if_deposit_amount_into_a_checking_account_is_under_1000() {
		defaultCheckingAccountTestSetUp();
		assertTrue(commandValidator.validate("deposit 12345678 999.99"));
	}

}
