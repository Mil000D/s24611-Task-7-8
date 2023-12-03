package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		//  Current test checks if the getAmount() method returns
		//  the expected amount for different instances of Money.

		assertEquals(Integer.valueOf(20000), SEK200.getAmount());
		assertEquals(Integer.valueOf(10000), SEK100.getAmount());
		assertEquals(Integer.valueOf(0), SEK0.getAmount());
		assertEquals(Integer.valueOf(-10000), SEKn100.getAmount());
		assertEquals(Integer.valueOf(1000), EUR10.getAmount());
		assertEquals(Integer.valueOf(2000), EUR20.getAmount());
		assertEquals(Integer.valueOf(0), EUR0.getAmount());
	}

	@Test
	public void testGetCurrency() {
		// Test checks if the getCurrency() method returns
		// the expected currency for different instances of Money.

		assertEquals(SEK, SEK100.getCurrency());
		assertEquals(EUR, EUR10.getCurrency());
		assertEquals(SEK, SEK0.getCurrency());
		assertEquals(SEK, SEKn100.getCurrency());
		assertEquals(EUR, EUR0.getCurrency());
	}

	@Test
	public void testToString() {
		// Assertions below check if the toString() method returns
		// the expected string representation for different instances of Money.

		assertEquals("200.00 SEK", SEK200.toString());
		assertEquals("100.00 SEK", SEK100.toString());
		assertEquals("0.00 SEK", SEK0.toString());
		assertEquals("-100.00 SEK", SEKn100.toString());
		assertEquals("10.00 EUR", EUR10.toString());
		assertEquals("20.00 EUR", EUR20.toString());
		assertEquals("0.00 EUR", EUR0.toString());
	}

	@Test
	public void testGlobalValue() {
		// Test checks if the universalValue() method returns
		// the expected global value for different instances of Money.

		assertEquals(Integer.valueOf(1500), SEK100.universalValue());
		assertEquals(Integer.valueOf(1500), EUR10.universalValue());
		assertEquals(Integer.valueOf(3000), SEK200.universalValue());
		assertEquals(Integer.valueOf(3000), EUR20.universalValue());
		assertEquals(Integer.valueOf(0), SEK0.universalValue());
		assertEquals(Integer.valueOf(0), EUR0.universalValue());
		assertEquals(Integer.valueOf(-1500), SEKn100.universalValue());
	}

	@Test
	public void testEqualsMoney() {
		Money SEK100Copy = new Money(10000, SEK);
   		// Assertions underneath check if the equals() method
		// returns the expected result when comparing different instances of Money,
		// in this case that the equality is true.

		assertTrue(SEK100.equals(SEK100Copy));
		assertTrue(SEK100.equals(EUR10));
		assertTrue(SEK200.equals(EUR20));
		assertTrue(EUR10.equals(SEK100));
		assertTrue(EUR20.equals(SEK200));
	}

	@Test
	public void testAdd() {
		// This test checks if the add() method returns
		// the expected result when adding different instances of Money.

		Money result1 = SEK100.add(EUR10);
		Money result2 = EUR10.add(SEK100);
		Money result3 = SEK100.add(SEK200);
		Money result4 = EUR20.add(SEK200);
		Money result5 = SEK0.add(EUR20);

		assertEquals(Integer.valueOf(20000), result1.getAmount());
		assertEquals(SEK, result1.getCurrency());

		assertEquals(Integer.valueOf(2000), result2.getAmount());
		assertEquals(EUR, result2.getCurrency());

		assertEquals(Integer.valueOf(30000), result3.getAmount());
		assertEquals(SEK, result3.getCurrency());

		assertEquals(Integer.valueOf(4000), result4.getAmount());
		assertEquals(EUR, result4.getCurrency());

		assertEquals(Integer.valueOf(20000), result5.getAmount());
		assertEquals(SEK, result5.getCurrency());
	}

	@Test
	public void testSub() {
		// Test checks if the sub() method returns
		// the expected result when subtracting different instances of Money.

		Money result1 = SEK100.sub(EUR10);
		Money result2 = SEK100.sub(SEK200);
		Money result3 = SEK0.sub(EUR20);
		Money result4 = EUR10.sub(SEK200);
		Money result5 = EUR20.sub(SEK200);

		assertEquals(Integer.valueOf(0), result1.getAmount());
		assertEquals(SEK, result1.getCurrency());

		assertEquals(Integer.valueOf(-10000), result2.getAmount());
		assertEquals(SEK, result2.getCurrency());

		assertEquals(Integer.valueOf(-20000), result3.getAmount());
		assertEquals(SEK, result3.getCurrency());

		assertEquals(Integer.valueOf(-1000), result4.getAmount());
		assertEquals(EUR, result4.getCurrency());

		assertEquals(Integer.valueOf(0), result5.getAmount());
		assertEquals(EUR, result4.getCurrency());
	}

	@Test
	public void testIsZero() {
		// It checks if the isZero() method returns
		// the expected result for different instances of Money.

		assertTrue(SEK0.isZero());
		assertFalse(SEK100.isZero());
		assertFalse(EUR10.isZero());
	}

	@Test
	public void testNegate() {
		// Test checks if the negate() method returns the expected
		// result for different instances of Money.

		Money result1 = SEK100.negate();
		Money result2 = SEKn100.negate();
		Money result3 = EUR20.negate();

		assertEquals(Integer.valueOf(-10000), result1.getAmount());
		assertEquals(SEK, result1.getCurrency());

		assertEquals(Integer.valueOf(10000), result2.getAmount());
		assertEquals(SEK, result2.getCurrency());

		assertEquals(Integer.valueOf(-2000), result3.getAmount());
		assertEquals(EUR, result3.getCurrency());
	}

	@Test
	public void testCompareTo() {
		// Assertions below test if the compareTo() method returns
		// the expected result when comparing different instances of Money
		// here not only the equality but also inequality is being checked.

        assertEquals(0, SEK100.compareTo(EUR10));
		assertEquals(0, SEK200.compareTo(EUR20));
		assertEquals(0, EUR10.compareTo(SEK100));
		assertEquals(0, EUR20.compareTo(SEK200));

		assertTrue(EUR10.compareTo(SEK200) < 0);
		assertTrue(SEK100.compareTo(EUR20) < 0);
		assertTrue(SEK200.compareTo(EUR10) > 0);
		assertTrue(SEK0.compareTo(SEK100) < 0);
		assertTrue(SEK100.compareTo(EUR0) > 0);
	}
}
