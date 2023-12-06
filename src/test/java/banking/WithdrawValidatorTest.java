package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
	private final String DEFAULT_VALID_GENERAL_TEST_STRING = "withdraw 12345678 250";
	Account account;
	private String DEFAULT_VALID_CD_TEST_STRING;
	private CommandValidator commandValidator;
	private CommandProcessor commandProcessor;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		commandProcessor = new CommandProcessor(bank);
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

	private void defaultCertificateOfDepositAccountTestSetUp() {
		account = new CertificateOfDeposit(12345678, 1200.50, .5);
		bank.addAccount(account);
		commandProcessor.process("pass 12");
		DEFAULT_VALID_CD_TEST_STRING = "withdraw 12345678 " + Double.toString(account.balance());
	}

	// Command Layout: "deposit (existingID) (withdrawAmount)"

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

	// ---ID banking.Account Type Tests---

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
		defaultCertificateOfDepositAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_CD_TEST_STRING));
	}

	// ---Withdraw Amount Tests---

	@Test
	public void invalid_if_withdraw_amount_is_negative() {
		defaultGeneralAccountTestSetUp();
		assertFalse(commandValidator.validate("withdraw 12345678 -0.01"));
	}

	@Test
	public void valid_if_withdraw_amount_is_0() {
		defaultGeneralAccountTestSetUp();
		assertTrue(commandValidator.validate("withdraw 12345678 0"));
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

	@Test
	public void invalid_if_withdraw_amount_from_a_cd_account_is_under_balance() {
		defaultCertificateOfDepositAccountTestSetUp();
		assertFalse(commandValidator.validate("withdraw 12345678 " + Double.toString(account.balance() - .01)));
	}

	@Test
	public void valid_if_withdraw_amount_from_a_cd_account_is_the_balance() {
		defaultCertificateOfDepositAccountTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_CD_TEST_STRING));
	}

	@Test
	public void valid_if_withdraw_amount_from_a_cd_account_is_over_balance() {
		defaultCertificateOfDepositAccountTestSetUp();
		assertTrue(commandValidator.validate("withdraw 12345678 " + Double.toString(account.balance() + .01)));
	}

	// ---Withdraw Time Tests---

	@Test
	public void invalid_if_attempt_to_withdraw_twice_from_savings_account_in_the_same_month() {
		defaultSavingsAccountTestSetUp();
		account.withdraw(500);
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void valid_if_attempt_to_withdraw_twice_from_savings_account_in_different_months() {
		defaultSavingsAccountTestSetUp();
		commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING);
		account.withdraw(500);
		commandProcessor.process("pass 1");
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_attempt_to_do_a_full_withdraw_from_cd_before_12_months_have_passed_since_creation() {
		account = new CertificateOfDeposit(12345678, 1200.50, .5);
		bank.addAccount(account);
		commandProcessor.process("pass 11");
		assertFalse(commandValidator.validate("withdraw 12345678 " + Double.toString(account.balance())));
	}

	@Test
	public void valid_if_attempt_to_do_a_full_withdraw_from_cd_12_months_have_passed_since_creation() {
		defaultCertificateOfDepositAccountTestSetUp();
		System.out.println(commandValidator.validate(DEFAULT_VALID_CD_TEST_STRING));
		assertTrue(commandValidator.validate(DEFAULT_VALID_CD_TEST_STRING));
	}

	@Test
	public void valid_if_attempt_to_do_a_full_withdraw_from_cd_after_12_months_have_passed_since_creation() {
		defaultCertificateOfDepositAccountTestSetUp();
		commandProcessor.process("pass 1");
		assertTrue(commandValidator.validate("withdraw 12345678 " + Double.toString(account.balance())));
	}
}
