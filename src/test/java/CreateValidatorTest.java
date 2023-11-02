import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {

	private CreateValidator createValidator;

	@BeforeEach
	public void setUp() {
		createValidator = new CreateValidator();
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

	}

	@Test
	public void validator_accepts_checking_account() {

	}

	@Test
	public void validator_accepts_cd_account() {

	}

}
