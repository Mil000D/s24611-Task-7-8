package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		// This test checks if the getName() method
		// returns the expected name for each currency.

		assertEquals("SEK", SEK.getName());
		assertEquals("DKK", DKK.getName());
		assertEquals("EUR", EUR.getName());
	}

	@Test
	public void testGetRate() {
		// Assertions below check if the getRate() method
		// returns the expected exchange rate for each currency.

		assertEquals(Double.valueOf(0.15), SEK.getRate());
		assertEquals(Double.valueOf(0.20), DKK.getRate());
		assertEquals(Double.valueOf(1.5), EUR.getRate());
	}

	@Test
	public void testSetRate() {
		// This test sets new exchange rates using the setRate() method
		// and then checks if the getRate() method returns the updated exchange rate for each currency.

		SEK.setRate(0.25);
		DKK.setRate(0.30);
		EUR.setRate(2.0);

		assertEquals(Double.valueOf(0.25), SEK.getRate());
		assertEquals(Double.valueOf(0.30), DKK.getRate());
		assertEquals(Double.valueOf(2.0), EUR.getRate());
	}

	@Test
	public void testUniversalValue() {
		// Assertions beneath check if the universalValue() method correctly
		// converts amounts to the universal value for each currency.

		assertEquals(Integer.valueOf(150), SEK.universalValue(1000));
		assertEquals(Integer.valueOf(200), DKK.universalValue(1000));
		assertEquals(Integer.valueOf(1500), EUR.universalValue(1000));
		assertEquals(Integer.valueOf(300), SEK.universalValue(2000));
		assertEquals(Integer.valueOf(400), DKK.universalValue(2000));
		assertEquals(Integer.valueOf(3000), EUR.universalValue(2000));
	}

	@Test
	public void testValueInThisCurrency() {
		// Assertions underneath check if the valueInThisCurrency() method correctly
		// converts amounts from another currency to the currency under test.

		assertEquals(Integer.valueOf(1500), SEK.valueInThisCurrency(150, EUR));
		assertEquals(Integer.valueOf(400), SEK.valueInThisCurrency(300, DKK));
		assertEquals(Integer.valueOf(1500), DKK.valueInThisCurrency(200, EUR));
		assertEquals(Integer.valueOf(450), DKK.valueInThisCurrency(600, SEK));
		assertEquals(Integer.valueOf(150), EUR.valueInThisCurrency(1500, SEK));
		assertEquals(Integer.valueOf(200), EUR.valueInThisCurrency(1500, DKK));
	}
}
