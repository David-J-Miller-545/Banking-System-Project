package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {
	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void valid_if_command_contains_2_arguments() {
		assertTrue(commandValidator.validate("pass 1"));
	}

	@Test
	public void invalid_if_given_more_than_2_arguments() {
		assertFalse(commandValidator.validate("pass 1 Nope"));
	}

	@Test
	public void invalid_if_given_less_than_2_arguments() {
		assertFalse(commandValidator.validate("pass"));
	}

	@Test
	public void invalid_if_number_of_months_is_string() {
		assertFalse(commandValidator.validate("pass time"));
	}

	@Test
	public void invalid_if_number_of_months_is_a_number_that_is_not_an_integer() {
		assertFalse(commandValidator.validate("pass 2.2"));
	}

	@Test
	public void invalid_if_number_of_months_is_an_integer_represented_by_a_decimal() {
		assertFalse(commandValidator.validate("pass 6.0"));
	}

	@Test
	public void invalid_if_number_of_months_is_less_than_1() {
		assertFalse(commandValidator.validate("pass 0"));
	}

	@Test
	public void valid_if_number_of_months_is_1() {
		assertTrue(commandValidator.validate("pass 1"));
	}

	@Test
	public void valid_if_number_of_months_is_between_1_and_60() {
		assertTrue(commandValidator.validate("pass 30"));
	}

	@Test
	public void valid_if_number_of_months_is_60() {
		assertTrue(commandValidator.validate("pass 60"));
	}

	@Test
	public void invalid_if_number_of_months_is_more_than_60() {
		assertFalse(commandValidator.validate("pass 61"));
	}
}
