package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;

	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}

	@Test
	public void testAddRemoveTimedPayment() {
		// I was getting exception :
		// java.lang.NullPointerException: Cannot invoke "b_Money.Account.deposit(b_Money.Money)" because "account" is null
		// After fixing Bank class everything works

		assertFalse(testAccount.timedPaymentExists("payment1"));
		testAccount.addTimedPayment("payment1", 2, 1, new Money(100, SEK), SweBank, "Alice");
		assertTrue(testAccount.timedPaymentExists("payment1"));
		testAccount.removeTimedPayment("payment1");
		assertFalse(testAccount.timedPaymentExists("payment1"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		testAccount.addTimedPayment("payment1", 1, 0, new Money(500000, SEK), SweBank, "Alice");

		testAccount.tick();
		assertEquals(new Money(9500000, SEK), testAccount.getBalance());

		testAccount.tick();
		assertEquals(new Money(9000000, SEK), testAccount.getBalance());

		testAccount.tick();
		assertEquals(new Money(8500000, SEK), testAccount.getBalance());
	}

	@Test
	public void testAddWithdraw() {
		testAccount.deposit(new Money(5000000, SEK));
		assertEquals(new Money(15000000, SEK), testAccount.getBalance());

		testAccount.withdraw(new Money(5000000, SEK));
		assertEquals(new Money(10000000, SEK), testAccount.getBalance());
	}
	
	@Test
	public void testGetBalance() {
		assertEquals(new Money(10000000, SEK), testAccount.getBalance());
		testAccount.deposit(new Money(50000000, SEK));
		assertEquals(new Money(60000000, SEK), testAccount.getBalance());
	}
}
