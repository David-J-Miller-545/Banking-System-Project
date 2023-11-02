import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {

	private CreateValidator createValidator;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createValidator = new CreateValidator(bank);
	}

	@Test
	public void validator_reads_a_set_number_arguments() {
		assertEquals(4, createValidator.readCommandArguments("create savings 12345678 0.6").length);
	}

	@Test
	public void validator_accepts_create_keyword() {
		assertTrue(createValidator.validate("create savings 12345678 0.6"));
	}

	@Test
	public void command_invalid_if_first_argument_is_not_create() {
		assertFalse(createValidator.validate("dumbo savings 12345678 0.6"));
	}

	@Test
	public void validator_accepts_savings_account() {
		assertTrue(createValidator.validate("create savings 12345678 0.6"));
	}

	@Test
	public void validator_accepts_checking_account() {
		assertTrue(createValidator.validate("create checking 12345678 0.6"));
	}

	@Test
	public void validator_accepts_cd_account() {
		assertTrue(createValidator.validate("create cd 12345678 0.6 10000"));
	}

	@Test
	public void validator_refuses_input_other_than_a_valid_account_for_the_second_argument() {
		assertFalse(createValidator.validate("create notAnAccount 12345678 0.6"));
	}

	@Test
	public void validator_is_case_insensitive() {
		assertTrue(createValidator.validate("CrEaTe SAvIngS 12345678 0.6"));
	}

	@Test
	public void validator_accepts_an_8_digit_id() {
		assertTrue(createValidator.validate("create savings 12345678 0.6"));
	}

	@Test
	public void validator_refuses_anything_but_an_8_digit_id() {
		assertFalse(createValidator.validate("create savings 123456789 0.6"));
	}

	@Test
	public void validator_refuses_an_existing_id() {
		Account account = new Checking(12345678, 0);
		bank.addAccount(account);
		assertFalse(createValidator.validate("create savings 12345678 0.6"));
	}

	@Test
	public void validator_accepts_a_positve_float_between_1_and_10_for_apr() {
		assertTrue(createValidator.validate("create savings 12345678 0.6"));
	}

	@Test
	public void validator_accepts_4_arguments_for_creating_a_checking_account() {
		assertTrue(createValidator.validate("create checking 12345678 0.6"));
	}

	@Test
	public void validator_refuses_more_or_less_than_4_arguments_for_creating_a_checking_account() {
		assertFalse(createValidator.validate("create checking 12345678 0.6 Nope"));
	}

	@Test
	public void validator_accepts_5_arguments_for_creating_a_cd_account() {
		assertTrue(createValidator.validate("create cd 12345678 0.6 1000"));
	}

	@Test
	public void validator_refuses_more_or_less_than_5_arguments_for_creating_a_cd_account() {
		assertFalse(createValidator.validate("create savings 12345678 0.6 1000 Nope"));
	}

}
