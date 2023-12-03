package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;

	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
		assertEquals("Nordea", Nordea.getName());
		assertEquals("DanskeBank", DanskeBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(SEK, Nordea.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		// I was getting java.lang.AssertionError
		// at org.junit.Assert.fail(Assert.java:87)
		// at org.junit.Assert.assertTrue(Assert.java:42)
		// at org.junit.Assert.assertTrue(Assert.java:53)
		// at b_Money.BankTest.testOpenAccount(BankTest.java:42)

		SweBank.openAccount("Alice");
		assertTrue(SweBank.getAccountList().containsKey("Alice"));

		Nordea.openAccount("Charlie");
		assertTrue(Nordea.getAccountList().containsKey("Charlie"));

		DanskeBank.openAccount("Eve");
		assertTrue(DanskeBank.getAccountList().containsKey("Eve"));
	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
		SweBank.deposit("Ulrika", new Money(100000, SEK));
		assertEquals(Integer.valueOf(100000), SweBank.getBalance("Ulrika"));

		Nordea.deposit("Bob", new Money(50000, SEK));
		assertEquals(Integer.valueOf(50000), Nordea.getBalance("Bob"));

		DanskeBank.deposit("Gertrud", new Money(20000, DKK));
		assertEquals(Integer.valueOf(20000), DanskeBank.getBalance("Gertrud"));
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
		// I was getting incorrect values because of the bug in Bank class
		// in withdraw method

		SweBank.deposit("Ulrika", new Money(30000, SEK));
		SweBank.withdraw("Ulrika", new Money(30000, SEK));
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));

		Nordea.withdraw("Bob", new Money(20000, SEK));
		assertEquals(Integer.valueOf(-20000), Nordea.getBalance("Bob"));

		DanskeBank.withdraw("Gertrud", new Money(5000, DKK));
		assertEquals(Integer.valueOf(-5000), DanskeBank.getBalance("Gertrud"));
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(0), Nordea.getBalance("Bob"));
		assertEquals(Integer.valueOf(0), DanskeBank.getBalance("Gertrud"));

		SweBank.deposit("Ulrika", new Money(20000, SEK));
		assertEquals(Integer.valueOf(20000), SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		SweBank.transfer("Ulrika", Nordea, "Bob", new Money(30000, SEK));
		assertEquals(Integer.valueOf(-30000), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(30000), Nordea.getBalance("Bob"));

		Nordea.transfer("Bob", DanskeBank, "Gertrud", new Money(5000, SEK));
		assertEquals(Integer.valueOf(25000), Nordea.getBalance("Bob"));
		assertEquals(Integer.valueOf(3750), DanskeBank.getBalance("Gertrud"));

		// The bug I discovered while testing was connected to transfer() method in Bank class

		SweBank.transfer("Bob", "Ulrika", new Money(30000, SEK));
		assertEquals(Integer.valueOf(-30000), SweBank.getBalance("Bob"));
		assertEquals(Integer.valueOf(0), SweBank.getBalance("Ulrika"));
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {

		assertFalse(SweBank.getAccountList().get("Ulrika").timedPaymentExists("payment1"));
		SweBank.addTimedPayment("Ulrika", "payment1", 1, 0, new Money(10000, SEK), Nordea, "Bob");
		assertTrue(SweBank.getAccountList().get("Ulrika").timedPaymentExists("payment1"));

		SweBank.tick();
		SweBank.tick();

		assertEquals(Integer.valueOf(-20000), SweBank.getBalance("Ulrika"));
		assertEquals(Integer.valueOf(20000), Nordea.getBalance("Bob"));

		SweBank.addTimedPayment("Ulrika", "payment2", 1, 0, new Money(10000, SEK), Nordea, "Bob");
		assertTrue(SweBank.getAccountList().get("Ulrika").timedPaymentExists("payment2"));
		SweBank.removeTimedPayment("Ulrika", "payment2");
		assertFalse(SweBank.getAccountList().get("Ulrika").timedPaymentExists("payment2"));
	}
}
