package che16.dcs.aber.ac.uk.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.utils.Globals;


public class AntColonyOptimisationTest {

	private AntColonyOptimisation aco;

	@Before
	public void setUp() throws Exception {

		this.aco = new AntColonyOptimisation();
		Globals.setTestMode();
	}

	@Test
	public void testAntColonyOptimisationInstantiation() {
		assertNotNull("ACO is null", aco);
	}

	//start alpha testing
	
	@Test
	public void testValidationRulesCatchLowAlphaValue(){
		assertFalse("validaite should return false alpha is too low", aco.validate(-10, 2.5, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false alpha is too low", aco.validate(-7, 2.5, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false alpha is too low", aco.validate(-6, 2.5, 0.2, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchLowAlphaValueBoundaryCase(){
		assertFalse("validaite should return false alpha is too low", aco.validate(-5.01, 2.5, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false alpha is too low", aco.validate(-5.001, 2.5, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false alpha is too low", aco.validate(-5.0001, 2.5, 0.2, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchHighAlphaValueBoundaryCase(){
		assertFalse("validaite should return false alpha is too high", aco.validate(5.01, 2.5, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false alpha is too high", aco.validate(5.001, 2.5, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false alpha is too high", aco.validate(5.0001, 2.5, 0.2, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchHighAlphaValue(){
		assertFalse("validaite should return false alpha is too high", aco.validate(6, 2.5, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false alpha is too high", aco.validate(7, 2.5, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false alpha is too high", aco.validate(10, 2.5, 0.2, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesAllowLegalAlphaValues(){

		double[] values = new double[]{2.5, -2.1, 4.999999999, -4.999999999, 4, -2, -1.65, 5, -5, 3, -3.21};
		for(int i = 0; i < values.length; i++){
			assertTrue("validaite should return true alpha is legal", aco.validate(values[i], 2.5, 0.2, 0.2, 10, 15, 3, 3));
		}
	}

	@Test
	public void testValidationRulesAllowZeroAlphaValue(){
		assertTrue("validaite should return true zero is valid", aco.validate(0, 2.5, 0.2, 0.2, 10, 15, 3, 3));
	}
	
	//end alpha testing
	
	//start of beta testing
	
	@Test
	public void testValidationRulesCatchLowBetaValue(){
		assertFalse("validaite should return false beta is too low", aco.validate(2, -10, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false beta is too low", aco.validate(2, -7, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false beta is too low", aco.validate(2, -6, 0.2, 0.2, 10, 15, 3, 3));
	}
	
	@Test
	public void testValidationRulesCatchLowBetaaValueBoundaryCase(){
		assertFalse("validaite should return false beta is too low", aco.validate(2.5, -5.01, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false beta is too low", aco.validate(2.5, -5.001, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false beta is too low", aco.validate(2.5, -5.0001, 0.2, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchHighBetaValueBoundaryCase(){
		assertFalse("validaite should return false beta is too high", aco.validate(2.5, 5.01, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false beta is too high", aco.validate(2.5, 5.001, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false beta is too high", aco.validate(2.5, 5.0001, 0.2, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchHighBetaValue(){
		assertFalse("validaite should return false beta is too high", aco.validate(2.5, 6, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false beta is too high", aco.validate(2.5, 7, 0.2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false beta is too high", aco.validate(2.6, 10, 0.2, 0.2, 10, 15, 3, 3));
	}
	
	@Test
	public void testValidationRulesAllowLegalBetaValues(){

		double[] values = new double[]{2.5, -2.1, 4.999999999, -4.999999999, 4, -2, -1.65, 5, -5, 3, -3.21};
		for(int i = 0; i < values.length; i++){
			assertTrue("validaite should return true Beta is legal", aco.validate(2, values[i], 0.2, 0.2, 10, 15, 3, 3));
		}
	}
	
	@Test
	public void testValidationRulesAllowZeroBetaValue(){
		assertTrue("validaite should return true zero is valid", aco.validate(2.5, 0, 0.2, 0.2, 10, 15, 3, 3));
	}
	
	//end beta testing

}
