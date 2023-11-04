import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {
	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	public void validator_reads_a_set_number_arguments() {
		assertEquals(4, commandValidator.readCommandArguments("(arg1) (arg2) (arg3) (arg4)").length);
	}

	@Test
	public void invalid_if_first_argument_is_unknown_keyword() {
		assertFalse(commandValidator.validate("dumbo savings 12345678 0.6"));
	}

	@Test
	public void validator_is_case_insensitive() {
		assertTrue(commandValidator.validate("CrEaTe SAvIngS 12345678 0.6"));
	}
}
