import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	private final String DEFAULT_VALID_GENERAL_TEST_STRING = "deposit 12345678 500";

	DepositValidator depositValidator;
	Bank bank;
	Account account;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		depositValidator = new DepositValidator(bank);
	}

	@Test
	public void validator_accepts_deposit_keyword() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		assertTrue(depositValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void validator_accepts_an_8_digit_id_if_it_belongs_to_an_account_within_the_bank() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		assertTrue(depositValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void validator_refuses_an_8_digit_id_if_it_does_not_belong_to_an_account_within_the_bank() {
		assertFalse(depositValidator.validate(DEFAULT_VALID_GENERAL_TEST_STRING));
	}

	@Test
	public void validator_refuses_negative_deposit_amount() {
		account = new Checking(12345678, .5);
		bank.addAccount(account);
		assertFalse(depositValidator.validate("deposit 12345678 -0.01"));
	}

}
