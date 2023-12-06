package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	private CommandStorage commandStorage;
	private Bank bank;
	private Account account;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	public void singleAccountSetUp() {
		account = new Checking(12345678, 5);
		bank.addAccount(account);
	}

	public void doubleAccountSetUp() {
		singleAccountSetUp();
		Account account2 = new Savings(23456789, 6);
		bank.addAccount(account2);
	}

	@Test
	public void command_storage_can_store_an_invalid_command_and_give_it_back() {
		commandStorage.storeInvalid("1 2 3 4");
		assertEquals("1 2 3 4", commandStorage.get().get(0));
	}

	@Test
	public void command_can_store_2_invalid_commands() {
		commandStorage.storeInvalid("1 2 3 4");
		commandStorage.storeInvalid("a b c d");
		assertEquals(2, commandStorage.get().size());
	}

	@Test
	public void command_storage_can_get_an_entire_list_of_invalid_commands() {
		commandStorage.storeInvalid("1 2 3 4");
		commandStorage.storeInvalid("a b c d");
		commandStorage.storeInvalid("d e f g");

		// Assert List
		ArrayList<String> testCommands = new ArrayList<String>();
		testCommands.add("1 2 3 4");
		testCommands.add("a b c d");
		testCommands.add("d e f g");
		assertTrue(commandStorage.get().equals(testCommands));
	}

	@Test
	public void command_storage_can_return_status_of_an_account_that_has_been_created() {
		singleAccountSetUp();

		assertEquals("Checking 12345678 0.00 5.00", commandStorage.get().get(0));
	}

	@Test
	public void command_storage_can_return_the_current_status_of_an_account() {
		singleAccountSetUp();
		bank.depositInAccount(300, account.id());

		assertEquals("Checking 12345678 300.00 5.00", commandStorage.get().get(0));
	}

	@Test
	public void command_storage_doesnt_return_accounts_that_have_been_removed() {
		singleAccountSetUp();
		bank.removeAccount(account.id());

		assertEquals(0, commandStorage.get().size());
	}

	@Test
	public void command_storage_can_store_a_valid_account_transaction_and_give_it_back() {
		singleAccountSetUp();
		commandStorage.storeHistory("Deposit 12345678 0");

		assertEquals("Deposit 12345678 0", commandStorage.get().get(1)); // index 1 because 0 is the status
	}

	@Test
	public void command_storage_can_store_two_valid_account_transactions() {
		singleAccountSetUp();
		commandStorage.storeHistory("Deposit 12345678 0");
		commandStorage.storeHistory("Withdraw 12345678 0");

		assertEquals(3, commandStorage.get().size()); // 2 commands + 1 status
	}

	@Test
	public void command_storage_doesnt_return_history_from_an_account_that_have_been_removed() {
		singleAccountSetUp();
		commandStorage.storeHistory("Deposit 12345678 0");
		bank.removeAccount(account.id());

		assertEquals(0, commandStorage.get().size());
	}

	@Test
	public void command_storage_formats_id_to_be_8_digits() {
		account = new Checking(1, 5);
		bank.addAccount(account);

		assertEquals("Checking 00000001 0.00 5.00", commandStorage.get().get(0));
	}

	@Test
	public void command_storage_can_return_status_of_two_accounts() {
		doubleAccountSetUp();

		// Assert List
		ArrayList<String> testOutput = new ArrayList<String>();
		testOutput.add("Checking 12345678 0.00 5.00");
		testOutput.add("Savings 23456789 0.00 6.00");
		assertTrue(commandStorage.get().equals(testOutput));
	}

	@Test
	void command_storage_can_return_two_accounts_transaction_histories() {
		doubleAccountSetUp();
		commandStorage.storeHistory("Deposit 12345678 0");
		commandStorage.storeHistory("Withdraw 23456789 0");

		// Assert List
		ArrayList<String> testOutput = new ArrayList<String>();
		testOutput.add("Checking 12345678 0.00 5.00");
		testOutput.add("Deposit 12345678 0");
		testOutput.add("Savings 23456789 0.00 6.00");
		testOutput.add("Withdraw 23456789 0");
		assertTrue(commandStorage.get().equals(testOutput));
	}

	@Test
	public void command_storage_can_return_a_transfer_transaction_under_both_accounts() {
		doubleAccountSetUp();
		commandStorage.storeHistory("TrAnSfEr 12345678 23456789 0");

		// Assert List
		ArrayList<String> testOutput = new ArrayList<String>();
		testOutput.add("Checking 12345678 0.00 5.00");
		testOutput.add("TrAnSfEr 12345678 23456789 0");
		testOutput.add("Savings 23456789 0.00 6.00");
		testOutput.add("TrAnSfEr 12345678 23456789 0");
		assertTrue(commandStorage.get().equals(testOutput));
	}

	@Test
	public void command_storage_can_return_a_multiple_accounts_transaction_histories_in_order() {
		doubleAccountSetUp();
		commandStorage.storeHistory("TrAnSfEr 12345678 23456789 0");
		commandStorage.storeHistory("Deposit 12345678 0");
		commandStorage.storeHistory("Withdraw 23456789 0");

		// Assert List
		ArrayList<String> testOutput = new ArrayList<String>();
		testOutput.add("Checking 12345678 0.00 5.00");
		testOutput.add("TrAnSfEr 12345678 23456789 0");
		testOutput.add("Deposit 12345678 0");
		testOutput.add("Savings 23456789 0.00 6.00");
		testOutput.add("TrAnSfEr 12345678 23456789 0");
		testOutput.add("Withdraw 23456789 0");

		assertTrue(commandStorage.get().equals(testOutput));
	}

	@Test
	public void command_storage_rebuilds_output_at_the_beginning_of_get() {
		doubleAccountSetUp();
		commandStorage.storeHistory("TrAnSfEr 12345678 23456789 0");
		commandStorage.storeHistory("Deposit 12345678 0");
		commandStorage.storeHistory("Withdraw 23456789 0");

		commandStorage.get();
		commandStorage.storeHistory("Withdraw 12345678 0");

		// Assert List
		ArrayList<String> testOutput = new ArrayList<String>();
		testOutput.add("Checking 12345678 0.00 5.00");
		testOutput.add("TrAnSfEr 12345678 23456789 0");
		testOutput.add("Deposit 12345678 0");
		testOutput.add("Withdraw 12345678 0");
		testOutput.add("Savings 23456789 0.00 6.00");
		testOutput.add("TrAnSfEr 12345678 23456789 0");
		testOutput.add("Withdraw 23456789 0");

		assertTrue(commandStorage.get().equals(testOutput));
	}

}
