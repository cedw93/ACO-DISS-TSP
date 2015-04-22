package che16.dcs.aber.ac.uk.test.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.model.City;
import che16.dcs.aber.ac.uk.model.World;
import che16.dcs.aber.ac.uk.utils.Globals;

public class WorldTest {

	private World world;
	private AntColonyOptimisation aco;

	@Before
	public void setUp() throws Exception {
		aco = new AntColonyOptimisation();
		aco.setValues(2.5, 2.5, 0.2, 0.8, 10, 15, 2, 4);
		world = new World(aco, 15, 10, 8, 0);
		Globals.setTestMode();
	}

	@Test
	public void testInitAntsHasCorrectSize() {
		assertEquals("the number of agents is incorrect", 15, world.getAnts().size());

		world = new World(aco, 25, 10, 8, 1);
		assertEquals("the number of agents is incorrect", 25, world.getAnts().size());

		world = new World(aco, 5, 10, 8, 1);
		assertEquals("the number of agents is incorrect", 5, world.getAnts().size());
	}

	@Test
	public void testInitCitiesHasCorrectSize() {
		assertEquals("the number of cities is incorrect", 10, world.getCities().size());

		world = new World(aco, 15, 5, 8, 1);
		assertEquals("the number of cities is incorrect", 5, world.getCities().size());

		world = new World(aco, 15, 20, 8, 1);
		assertEquals("the number of cities is incorrect", 20, world.getCities().size());
	}

	@Test
	public void testInitDistanceMatrixHasCorrectSize() {
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getDistanceMatrix().length);
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getDistanceMatrix()[0].length);
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getDistanceMatrix()[world.getCities().size() - 1].length);
	}

	@Test
	public void testInitInvertedDistanceMatrixHasCorrectSize() {
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getInvertedMatrix().length);
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getInvertedMatrix()[0].length);
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getInvertedMatrix()[ world.getCities().size() - 1].length);
	}

	@Test
	public void testInitPheromoneMatrixHasCorrectSize() {
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getPheromone().length);
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getPheromone()[0].length);
		assertEquals("the size of the matrix is incorrect", world.getCities().size(), world.getPheromone()[world.getCities().size() - 1].length);
	}

	@Test
	public void testInitWorldWithGivenCities() {
		ArrayList<City> tempCities = new ArrayList<City>();

		tempCities.add(new City(10, 5, 0));
		tempCities.add(new City(5, 10, 1));
		tempCities.add(new City(7, 2, 2));
		tempCities.add(new City(11, 9, 3));

		world = new World(aco, 10, tempCities.size(), tempCities, 7, 1);

		assertEquals("the number of cities is incorrect", tempCities.size(), world.getCities().size());
		//test the cities are as expected
		assertEquals("the city X is invalid",10, world.getCities().get(0).getX());
		assertEquals("the city Y is invalid",5, world.getCities().get(0).getY());

		assertEquals("the city X is invalid",5, world.getCities().get(1).getX());
		assertEquals("the city Y is invalid",10, world.getCities().get(1).getY());

		assertEquals("the city X is invalid",7, world.getCities().get(2).getX());
		assertEquals("the city Y is invalid",2, world.getCities().get(2).getY());

		assertEquals("the city X is invalid",11, world.getCities().get(3).getX());
		assertEquals("the city Y is invalid",9, world.getCities().get(3).getY());

	}

	@Test
	public void testDistanceMatrixValuesAreAsExepcted(){

		ArrayList<City> tempCities = new ArrayList<City>();

		tempCities.add(new City(10, 5, 0));
		tempCities.add(new City(5, 10, 1));
		tempCities.add(new City(7, 2, 2));
		tempCities.add(new City(11, 9, 3));

		world = new World(aco, 10, tempCities.size(), tempCities, 7, 1);

		for(int i = 0; i < world.getCities().size(); i++){
			for(int j = 0; j < world.getCities().size(); j++){
				//0.0001 is enough degree of accuracy
				assertEquals(Globals.calculateEuclidianDistance(world.getCities().get(i).getX(), world.getCities().get(i).getY(),
						world.getCities().get(j).getX(), world.getCities().get(j).getY()), world.getDistanceMatrix()[i][j], 0.0001);
			}
		}
	}

	@Test
	public void testInvertedDistanceMatrixValuesAreAsExepcted(){

		ArrayList<City> tempCities = new ArrayList<City>();

		tempCities.add(new City(10, 5, 0));
		tempCities.add(new City(5, 10, 1));
		tempCities.add(new City(7, 2, 2));
		tempCities.add(new City(11, 9, 3));

		world = new World(aco, 10, tempCities.size(), tempCities, 7, 1);

		for(int i = 0; i < world.getDistanceMatrix().length; i++){
			for(int j = 0; j < world.getDistanceMatrix()[0].length; j++){
				//0.0001 is enough degree of accuracy
				assertEquals(world.invertValue(world.getDistanceMatrix()[i][j]), world.getInvertedMatrix()[i][j], 0.0001);
			}
		}
	}

	@Test
	public void testPheromoneMatrixInitialValuesAreAsExepcted(){

		for(int i = 0; i < world.getPheromone().length; i++){
			for(int j = 0; j < world.getPheromone()[0].length; j++){
				//0.0001 is enough degree of accuracy
				assertEquals(world.getPheromone()[i][j].getPheromoneValue(), aco.getInitialPheromone(), 0.0001);
			}
		}
	}

	@Test
	public void testCalculatePheromoneReturnsExpectedValues(){
		double current = 1.5;
		double newPheromone = 0.5;
		assertEquals(1.7, world.calculatePheromones(current, newPheromone), 0.0001);

		current = 0.005;
		newPheromone = 0.011;
		assertEquals(0.015, world.calculatePheromones(current, newPheromone), 0.0001);

		current = 0.7;
		newPheromone = 0.0;
		assertEquals(0.56, world.calculatePheromones(current, newPheromone), 0.0001);

		current = 0;
		newPheromone = 0.0098;
		assertEquals(0.0098, world.calculatePheromones(current, newPheromone), 0.0001);

	}

	@Test
	public void testAdjustAntsAtACityCorrectlyUpdatesValues(){
		int before = world.getCities().get(0).getAntsHere();
		world.adjustAntsAtCity(0, 2);
		int after =  world.getCities().get(0).getAntsHere();
		assertEquals("The the number of ants at each city is incorrect", before - 1, after);

		before = world.getCities().get(2).getAntsHere();
		world.adjustAntsAtCity(0, 2);
		after =  world.getCities().get(2).getAntsHere();

		assertEquals("The the number of ants at each city is incorrect", before + 1, after);

		before = world.getCities().get(1).getAntsHere();
		world.adjustAntsAtCity(1, 3);
		after =  world.getCities().get(1).getAntsHere();
		assertEquals("The the number of ants at each city is incorrect", before - 1, after);

		before = world.getCities().get(3).getAntsHere();
		world.adjustAntsAtCity(1, 3);
		after =  world.getCities().get(3).getAntsHere();

		assertEquals("The the number of ants at each city is incorrect", before + 1, after);
	}

}
