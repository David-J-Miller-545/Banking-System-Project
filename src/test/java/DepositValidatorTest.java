import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	private final String DEFAULT_VALID_GENERAL_TEST_STRING = "deposit 12345678 500";

	CommandValidator commandValidator;
	Bank bank;
	Account account;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void validator_accepts_deposit_keyword() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void validator_accepts_an_8_digit_id_if_it_belongs_to_an_account_within_the_bank() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void validator_refuses_an_8_digit_id_if_it_does_not_belong_to_an_account_within_the_bank() {
		assertFalse(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void validator_refuses_negative_deposit_amount() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		assertFalse(commandValidator.validate("deposit 12345678 -0.01"));
	}

	@Test
	public void validator_accepts_checking_account_type() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void validator_accepts_savings_account_type() {
		account = new Savings(12345678, .5);
		bank.addAccount(account);
		assertTrue(commandValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void validator_refuses_cd_account_type() {
		account = new CertificateOfDeposit(12345679, 1200.50, .5);
		bank.addAccount(account);
		assertFalse(commandValidator.validate("deposit 12345679 500"));
	}

	@Test
	public void validator_refuses_savings_deposits_over_2500() {
		account = new Savings(12345678, .5);
		bank.addAccount(account);
		assertFalse(commandValidator.validate("deposit 12345678 2500.01"));
	}

	@Test
	public void validator_refuses_checking_deposits_over_1000() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		assertFalse(commandValidator.validate("deposit 12345678 1000.01"));
	}

}
