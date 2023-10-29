import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	private final double TEST_TRANSACTION = 2.5;
	private Bank bank;
	private Account account;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		account = new Checking(1000001, 0);

	}

	@Test
	public void bank_has_zero_accounts_when_created() {
		assertEquals(0, bank.numAccounts());
	}

	@Test
	public void bank_has_one_account_when_one_is_added_to_it() {
		bank.addAccount(account);

		assertEquals(1, bank.numAccounts());
	}

	@Test
	public void bank_has_two_accounts_when_two_are_added_to_it() {
		Account account1 = new Checking(10000002, 0);
		bank.addAccount(account);
		bank.addAccount(account1);

		assertEquals(2, bank.numAccounts());
	}

	@Test
	public void bank_can_retrieve_the_correct_account() {
		bank.addAccount(account);

		assertEquals(account, bank.getAccount(account.id()));
	}

	@Test
	public void correct_account_gets_money_when_deposit_by_id_through_bank() {
		bank.addAccount(account);
		bank.depositInAccount(TEST_TRANSACTION, account.id());

		assertEquals(TEST_TRANSACTION, account.balance());
	}

	@Test
	public void correct_account_loses_money_when_withdrawn_by_id_through_bank() {
		bank.addAccount(account);
		account.deposit(TEST_TRANSACTION * 2);
		bank.withdrawFromAccount(TEST_TRANSACTION, account.id());

		assertEquals(TEST_TRANSACTION, account.balance());
	}

	@Test
	public void deposit_by_id_twice_through_bank_works() {
		bank.addAccount(account);
		bank.depositInAccount(TEST_TRANSACTION, account.id());
		bank.depositInAccount(TEST_TRANSACTION, account.id());

		assertEquals(TEST_TRANSACTION * 2, account.balance());
	}

	@Test
	public void withdraw_by_id_twice_through_bank_works() {
		bank.addAccount(account);
		account.deposit(TEST_TRANSACTION * 3);
		bank.withdrawFromAccount(TEST_TRANSACTION, account.id());
		bank.withdrawFromAccount(TEST_TRANSACTION, account.id());

		assertEquals(TEST_TRANSACTION, account.balance());
	}

}
