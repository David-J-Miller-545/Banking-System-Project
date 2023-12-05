package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandFunctionTest {
	private CommandFunction commandFunction;
	private Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
	}

	@Test
	public void validator_reads_a_set_number_arguments() {
		commandFunction = new CommandValidator(bank);
		assertEquals(4, commandFunction.readCommandArguments("(arg1) (arg2) (arg3) (arg4)").length);
	}

	@Test
	public void processor_reads_a_set_number_arguments() {
		commandFunction = new CommandProcessor(bank);
		assertEquals(4, commandFunction.readCommandArguments("(arg1) (arg2) (arg3) (arg4)").length);
	}
}
