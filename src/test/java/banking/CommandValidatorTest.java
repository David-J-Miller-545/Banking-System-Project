package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	private CommandValidator commandValidator;
	private Bank bank;
	private Account account;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		account = new Checking(12345678, 8);
		bank.addAccount(account);
	}

	@Test
	public void invalid_if_first_argument_is_unknown_keyword() {
		assertFalse(commandValidator.validate("dumbo savings 00000001 0.6"));
	}

	@Test
	public void validator_is_case_insensitive() {
		assertTrue(commandValidator.validate("CrEaTe SAvIngS 00000001 0.6"));
	}

	@Test
	public void valid_if_first_argument_is_create_keyword() {
		assertTrue(commandValidator.validate("create savings 00000001 0.6"));
	}

	@Test
	public void valid_if_first_argument_is_deposit_keyword() {
		assertTrue(commandValidator.validate("deposit 12345678 400"));
	}

	@Test
	public void valid_if_first_argument_is_pass_keyword() {
		assertTrue(commandValidator.validate("pass 1"));
	}

	@Test
	public void valid_if_first_argument_is_withdraw_keyword() {
		assertTrue(commandValidator.validate("withdraw 12345678 400"));
	}

	@Test
	public void valid_if_first_argument_is_transfer_keyword() {
		Account account2 = new Savings(23456789, 7);
		bank.addAccount(account2);
		account.deposit(200);
		assertTrue(commandValidator.validate("transfer 12345678 23456789 200"));
	}
}
