package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {
	private final String DEFAULT_VALID_GENERAL_TEST_STRING = "transfer 12345678 23456789 400";
	private Account toAccount;
	private Account fromAccount;
	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	public void defaultGeneralAccountsTestSetUp() {
		fromAccount = new Checking(12345678, .6);
		toAccount = new Checking(23456789, .7);
		fromAccount.deposit(750);
		bank.addAccount(fromAccount);
		bank.addAccount(toAccount);
	}

	// Command Layout: "transfer (existingFromID) (existingToID) (withdrawAmount)

	// ---Existing ID Tests---

	@Test
	public void valid_if_first_given_8_digit_id_belongs_to_an_account_within_the_bank() {
		defaultGeneralAccountsTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_first_given_8_digit_id_does_not_belong_to_an_account_within_the_bank() {
		toAccount = new Checking(23456789, .7);
		bank.addAccount(toAccount);
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_given_a_num_number_for_first_id() {
		defaultGeneralAccountsTestSetUp();
		assertFalse(commandValidator.validate("transfer aBcDeFgH 23456789 400"));
	}

	@Test
	public void valid_if_second_given_8_digit_id_belongs_to_an_account_within_the_bank() {
		defaultGeneralAccountsTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_second_given_8_digit_id_does_not_belong_to_an_account_within_the_bank() {
		fromAccount = new Checking(12345678, .6);
		bank.addAccount(fromAccount);
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_given_a_num_number_for_second_id() {
		defaultGeneralAccountsTestSetUp();
		assertFalse(commandValidator.validate("transfer 12345678 AbCdEfGh 400"));
	}

	// ---Argument Count Tests---

	@Test
	public void valid_if_command_contains_4_arguments() {
		defaultGeneralAccountsTestSetUp();
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_given_more_than_4_arguments() {
		defaultGeneralAccountsTestSetUp();
		assertFalse(commandValidator.validate("transfer 12345678 23456789 500 Nope"));
	}

	@Test
	public void invalid_if_given_less_than_4_arguments() {
		defaultGeneralAccountsTestSetUp();
		assertFalse(commandValidator.validate("transfer 12345678 23456789"));
	}

	// ---ID banking.Account Type Tests---

	@Test
	public void invalid_if_self_to_self_transfer() {
		fromAccount = new Checking(12345678, .6);
		fromAccount.deposit(750);
		bank.addAccount(fromAccount);
		assertFalse(commandValidator.validate("transfer 12345678 12345678 400"));
	}

	@Test
	public void valid_if_checking_to_checking_transfer() {
		fromAccount = new Checking(12345678, .6);
		toAccount = new Checking(23456789, .7);
		fromAccount.deposit(750);
		bank.addAccount(fromAccount);
		bank.addAccount(toAccount);
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void valid_if_checking_to_savings_transfer() {
		fromAccount = new Checking(12345678, .6);
		toAccount = new Savings(23456789, .7);
		fromAccount.deposit(750);
		bank.addAccount(fromAccount);
		bank.addAccount(toAccount);
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void valid_if_savings_to_savings_transfer() {
		fromAccount = new Savings(12345678, .6);
		toAccount = new Savings(23456789, .7);
		fromAccount.deposit(750);
		bank.addAccount(fromAccount);
		bank.addAccount(toAccount);
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void valid_if_savings_to_checking_transfer() {
		fromAccount = new Savings(12345678, .6);
		toAccount = new Checking(23456789, .7);
		fromAccount.deposit(750);
		bank.addAccount(fromAccount);
		bank.addAccount(toAccount);
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_first_given_id_belongs_to_a_cd_account() {
		fromAccount = new CertificateOfDeposit(12345678, 1000, .6);
		toAccount = new Checking(23456789, .7);
		fromAccount.deposit(750);
		bank.addAccount(fromAccount);
		bank.addAccount(toAccount);
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void invalid_if_second_given_id_belongs_to_a_cd_account() {
		fromAccount = new Checking(12345678, .7);
		toAccount = new CertificateOfDeposit(23456789, 1000, .6);
		fromAccount.deposit(750);
		bank.addAccount(fromAccount);
		bank.addAccount(toAccount);
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	// ---Transfer Amount Tests---

	@Test
	public void invalid_if_given_a_num_number_for_withdraw_amount() {
		defaultGeneralAccountsTestSetUp();
		assertFalse(commandValidator.validate("transfer 12345678 23456789 AbC"));
	}

	@Test
	public void invalid_if_transfer_amount_is_negative() {
		defaultGeneralAccountsTestSetUp();
		assertFalse(commandValidator.validate("transfer 12345678 23456789 -0.01"));
	}

	@Test
	public void valid_if_transfer_amount_is_0() {
		defaultGeneralAccountsTestSetUp();
		assertTrue(commandValidator.validate("transfer 12345678 23456789 0"));
	}

	// ---Follows Withdraw Rules Tests---

	@Test
	public void invalid_if_withdraw_command_for_similar_case_is_invalid() {
		defaultGeneralAccountsTestSetUp();
		assertEquals(commandValidator.validate("withdraw 12345678 400.01"),
				commandValidator.validate("transfer 12345678 23456789 400.01"));
	}

	@Test
	public void valid_if_withdraw_command_for_similar_case_is_valid() {
		defaultGeneralAccountsTestSetUp();
		assertEquals(commandValidator.validate("withdraw 12345678 400"),
				commandValidator.validate("transfer 12345678 23456789 400"));
	}

	// ---Follows Deposit Rules Tests---

	@Test
	public void invalid_if_deposit_command_for_similar_case_is_invalid() {
		defaultGeneralAccountsTestSetUp();
		assertEquals(commandValidator.validate("deposit 34567899 400"),
				commandValidator.validate("transfer 12345678 34567899 400"));
	}

	@Test
	public void valid_if_deposit_command_for_similar_case_is_valid() {
		defaultGeneralAccountsTestSetUp();
		assertEquals(commandValidator.validate("deposit 23456789 400"),
				commandValidator.validate("transfer 12345678 23456789 400"));
	}

}
