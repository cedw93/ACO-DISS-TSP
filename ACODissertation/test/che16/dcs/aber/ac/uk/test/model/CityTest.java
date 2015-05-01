package che16.dcs.aber.ac.uk.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import che16.dcs.aber.ac.uk.model.City;
/*
 * 
 * *****************************NOTE****************************************
 * THE TEST CASES IN THIS CLASS ARE FAIRLY TRIVIAL AND THE NAME ARE DESCRIPTIVE 
 * OF THE CONTAINED TESTS. NO INDIVIDUAL JAVADOC IS NEEDED
 * 
 * 
 */

public class CityTest {

	private City city;

	@Before
	public void setUp() throws Exception {

		city = new City(10,15, 0);

	}

	@Test
	public void testCityIsCorrectlyInstantiated(){
		assertNotNull("city should not be null", city);

		assertEquals("City X is not set correcttly", 10, city.getX());
		assertEquals("City Y is not set correcttly", 15, city.getY());
		assertEquals("City index is not set correcttly", 0, city.getIndex());

	}

	@Test
	public void testAdjustAntsHereTakesAnyIntegerValue(){

		assertEquals("ants here is incorrect", 0, city.getAntsHere());

		city.adjustAntsHere(-1);
		assertEquals("ants here is incorrect", -1, city.getAntsHere());

		city.adjustAntsHere(+5);
		assertEquals("ants here is incorrect", 4, city.getAntsHere());

		city.adjustAntsHere(-2);
		assertEquals("ants here is incorrect", 2, city.getAntsHere());

		city.adjustAntsHere(0);
		assertEquals("ants here is incorrect", 2, city.getAntsHere());

	}

}
