import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	private CommandStorage commandStorage;

	@BeforeEach
	public void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	public void command_storage_can_store_a_command_and_give_it_back() {
		commandStorage.store("1 2 3 4");
		assertEquals("1 2 3 4", commandStorage.get().get(0));
	}

	@Test
	public void command_can_store_2_commands() {
		commandStorage.store("1 2 3 4");
		commandStorage.store("a b c d");
		assertEquals(2, commandStorage.get().size());
	}

	@Test
	public void command_storage_can_get_an_entire_list_of_invalid_commands() {
		commandStorage.store("1 2 3 4");
		commandStorage.store("a b c d");
		commandStorage.store("d e f g");

		// Assert List
		ArrayList<String> testCommands = new ArrayList<String>();
		testCommands.add("1 2 3 4");
		testCommands.add("a b c d");
		testCommands.add("d e f g");
		assertTrue(commandStorage.get().equals(testCommands));
	}
}
