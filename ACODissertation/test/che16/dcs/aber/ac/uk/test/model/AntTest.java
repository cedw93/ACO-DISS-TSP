package che16.dcs.aber.ac.uk.test.model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import che16.dcs.aber.ac.uk.model.Ant;
import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.model.World;
import che16.dcs.aber.ac.uk.utils.Globals;

public class AntTest {

	private AntColonyOptimisation aco;
	private World world;
	private Ant ant;

	@Before
	public void setUp() throws Exception {
		aco = new AntColonyOptimisation();
		aco.setValues(2.5, 2.5, 0.2, 0.8, 10, 15, 2, 4);
		world = new World(aco, 15, 10, 8, 1);
		ant = new Ant(world, 1);
		Globals.setTestMode();
	}

	@Test
	public void testProbabiliyIsCalculatedCorrectly(){

		double value = 1.1275;

		double result = ((Math.pow(value, world.getAlpha())) * (Math.pow(value, world.getBeta())));
		assertEquals(1.82221,result, 0.0001);

		value = 3.1275;

		result = ((Math.pow(value, world.getAlpha())) * (Math.pow(value, world.getBeta())));
		assertEquals(299.2172,result, 0.0001);

		value = 2.1983;

		result = ((Math.pow(value, world.getAlpha())) * (Math.pow(value, world.getBeta())));
		assertEquals(51.337509,result, 0.0001);

		value = 0.0213;
		//test one with lower values 10^-9 is the result
		result = ((Math.pow(value, world.getAlpha())) * (Math.pow(value, world.getBeta())));
		assertEquals(0.00000000438427,result, 0.000000001);

	}

	@Test
	public void testTotalProbabiliyIsCalculatedToEqualOne(){

		double value = 1.1275;

		double result = ((Math.pow(value, world.getAlpha())) * (Math.pow(value, world.getBeta())));
		assertEquals(1.82221,result, 0.0001);

		value = 3.1275;

		double result2 = ((Math.pow(value, world.getAlpha())) * (Math.pow(value, world.getBeta())));
		assertEquals(299.2172,result2, 0.0001);

		value = 2.1983;

		double result3 = ((Math.pow(value, world.getAlpha())) * (Math.pow(value, world.getBeta())));
		assertEquals(51.337509,result3, 0.0001);

		value = 0.0213;
		//test one with lower values 10^-9 is the result
		double result4 = ((Math.pow(value, world.getAlpha())) * (Math.pow(value, world.getBeta())));
		assertEquals(0.00000000438427,result4, 0.000000001);

		double totalSum = result + result2 + result3 + result4;

		assertEquals(352.376919, totalSum, 0.0001);

		double p1 = result/totalSum;
		double p2 = result2/totalSum;
		double p3 = result3/totalSum;
		double p4 = result4/totalSum;

		//check that total probability sums up to 1.0
		assertEquals(1.0, p1 + p2 + p3 + p4, 0.0001);

	}

	@Test
	public void testEliteAntsIsCorrectlyInstaniated(){
		//we dont test for illegal values here, that is handled by the user prompt
		world.initEliteAnts(5);
		assertEquals("elite ants is not the correct size", 5 ,world.getEliteCount());

		world.initEliteAnts(2);
		assertEquals("elite ants is not the correct size", 2 ,world.getEliteCount());

		world.initEliteAnts(9);
		assertEquals("elite ants is not the correct size", 9 ,world.getEliteCount());
	}

	@Test
	public void testGetNextNodeDoesntReturnVisitedNodes(){
		for(int i = 0; i < world.getCities().size() - 2; i++){
			ant.getVisited()[i] = true;
		}

		int index = ant.getNextProbableNode(3);
		assertFalse(ant.getVisited()[index]);
		ant.getVisited()[index] = true;

		index = ant.getNextProbableNode(index);
		assertFalse(ant.getVisited()[index]);
		ant.getVisited()[index] = true;

		index = ant.getNextProbableNode(index);

		//there should be nowhere to move so return -1
		assertEquals("there should be nowhere to move so next node should return -1", -1, index);
	}

}
