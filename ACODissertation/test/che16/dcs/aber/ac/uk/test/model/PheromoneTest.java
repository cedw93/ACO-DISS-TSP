package che16.dcs.aber.ac.uk.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.model.Pheromone;

public class PheromoneTest {

	private Pheromone phero;
	private AntColonyOptimisation aco;
	
	@Before
	public void setUp() throws Exception {
		aco = new AntColonyOptimisation();
		phero = new Pheromone(aco.getInitialPheromone());
	}

	@Test
	public void testInitialPheromoneIsSetCorrectly() {
		assertEquals(aco.getInitialPheromone(), phero.getPheromoneValue(), 0.0001);
		assertEquals(0.0, phero.getNewPhero(), 0.001);
	}
	
	@Test
	public void testAdjustingNewPheroCorrectlyAddsPhero(){
		assertEquals(0.0, phero.getNewPhero(), 0.001);
		phero.addToNewPhero(0.5);
		
		assertEquals(0.5, phero.getNewPhero(), 0.001);
		phero.addToNewPhero(1.22);
		
		assertEquals(1.72, phero.getNewPhero(), 0.001);
		phero.addToNewPhero(0.012);
		
		assertEquals(1.732, phero.getNewPhero(), 0.001);
	}

}
