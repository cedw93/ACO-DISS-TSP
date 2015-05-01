package che16.dcs.aber.ac.uk.test.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import che16.dcs.aber.ac.uk.model.City;
import che16.dcs.aber.ac.uk.utils.Globals;
/*
 * 
 * *****************************NOTE****************************************
 * THE TEST CASES IN THIS CLASS ARE FAIRLY TRIVIAL AND THE NAME ARE DESCRIPTIVE 
 * OF THE CONTAINED TESTS. NO INDIVIDUAL JAVADOC IS NEEDED
 * 
 * 
 */

public class GlobalsTest {

	private City c1, c2;

	@Before
	public void setUp() throws Exception {
		c1 = new City(10, 15, 0);
		c2 = new City(5, 17, 1);

	}

	@Test
	public void testEuclidianDistanceCalculation() {

		//the results were calculated using an online calculator then compared to my own results
		assertEquals(5.385165, Globals.calculateEuclidianDistance(c1.getX(), c1.getY(), c2.getX(),c2.getY()), 0.001);
		City c3 = new City(9, 11, 3);

		assertEquals(4.123106, Globals.calculateEuclidianDistance(c1.getX(), c1.getY(), c3.getX(),c3.getY()), 0.001);
		assertEquals(7.211103, Globals.calculateEuclidianDistance(c3.getX(), c3.getY(), c2.getX(),c2.getY()), 0.001);
	}

	@Test
	public void testLinearInterpolationReturnsCorrectValues(){

		assertEquals(12.5, Globals.linearInterpolateX(10, 15, 0.5), 0.001);
		assertEquals(12, Globals.linearInterpolateX(10, 20, 0.2), 0.001);
		assertEquals(18, Globals.linearInterpolateX(10, 20, 0.8), 0.001);
		assertEquals(1.1, Globals.linearInterpolateX(1, 1.5, 0.2), 0.001);

	}

}
