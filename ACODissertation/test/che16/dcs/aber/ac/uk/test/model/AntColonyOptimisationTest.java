package che16.dcs.aber.ac.uk.test.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.utils.Globals;

/*
 * 
 * *****************************NOTE****************************************
 * THE TEST CASES IN THIS CLASS ARE FAIRLY TRIVIAL AND THE NAME ARE DESCRIPTIVE 
 * OF THE CONTAINED TESTS. NO INDIVIDUAL JAVADOC IS NEEDED
 * 
 * 
 */

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

	//<---------------------------------------- START PARAMETER TESTING ---------------------------------------->

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
	public void testValidationRulesCatchLowBetaValueBoundaryCase(){
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

	//start decay rate testing

	@Test
	public void testValidationRulesCatchHighDecayRateaValue(){
		assertFalse("validaite should return false decay rate is too high", aco.validate(2.5, 2.5, 4, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false decay rate is too high", aco.validate(2.5, 2.5, 9, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false decay rate is too high", aco.validate(2.6, 2.5, 2, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchLowDecayRateaValue(){
		assertFalse("validaite should return false decay rate is too low", aco.validate(2.5, 2.5, -1, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false decay rate is too low", aco.validate(2.5, 2.5, -2, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false decay rate is too low", aco.validate(2.6, 2.5, -10, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchDecayRateValueBoundaryCase(){
		assertFalse("validaite should return false decay rate is too high", aco.validate(2.5, 2.5, 1.0, 0.2, 10, 15, 3, 3));
		assertTrue("validaite should return true decay rate is legal", aco.validate(2.5, 2.5, 0.99999, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false decay rate is too low", aco.validate(2.5, 2.5, -0.0000001, 0.2, 10, 15, 3, 3));
		assertFalse("validaite should return false decay rate is too low", aco.validate(2.5, 2.5, 0.0, 0.2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesAllowLegalDecayRateValues(){

		double[] values = new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.1232, 0.73562, 0.999999999};
		for(int i = 0; i < values.length; i++){
			assertTrue("validaite should return true decary rate is legal", aco.validate(2, 2.5, values[i], 0.2, 10, 15, 3, 3));
		}
	}

	//end decay rate testing

	//start iteration testing

	@Test
	public void testValidationRulesCatchLowIterationValue(){
		assertFalse("validaite should return false iterations are too low", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, -1, 3));
		assertFalse("validaite should return false iterations are too low", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, -5, 3));
		assertFalse("validaite should return false iterations are too low", aco.validate(2.6, 2.5, 0.2, 0.2, 10, 15, -7, 3));
	}

	public void testValidationRulesCatchIterationValueBoundaryCase(){
		assertFalse("validaite should return false iterations are zero", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 0, 3));
		assertTrue("validaite should return true iterations is legal", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 1, 3));
	}

	@Test
	public void testValidationRulesAllowLegalIterationValues(){

		int[] values = new int[]{1,2,3,4,5,6,7,8,9,10,20,30,10,33,42,17,11,20};
		for(int i = 0; i < values.length; i++){
			assertTrue("validaite should return true iteration value is legal", aco.validate(2, 2.5, 0.2, 0.2, 10, 15, values[i], 3));
		}
	}

	//end iteration testing

	//start uphill paths testing#

	@Test
	public void testValidationRulesCatchHighUphillValue(){
		assertFalse("validaite should return false uphill value is too low", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 3, 17));
		assertFalse("validaite should return false uphill value is too low", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 3, 25));
		assertFalse("validaite should return false uphill value is too low", aco.validate(2.6, 2.5, 0.2, 0.2, 10, 15, 3, 19));
	}

	@Test
	public void testValidationRulesCatchLowUphillValue(){
		assertFalse("validaite should return false uphill value is too low", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 3, -1));
		assertFalse("validaite should return false uphill value is too low", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 3, -3));
		assertFalse("validaite should return false uphill value is too low", aco.validate(2.6, 2.5, 0.2, 0.2, 10, 15, 3, -5));
	}

	public void testValidationRulesCatchUphillValueBoundaryCase(){
		assertFalse("validaite should return false uphill value is too low", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 3, -1));
		assertTrue("validaite should return true uphill value is legal", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 3, 15));
		assertFalse("validaite should return false uphill value is too high", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 3, 16));
		assertTrue("validaite should return true uphill value is legal", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 15, 3, 0));
	}

	@Test
	public void testValidationRulesAllowLegalUphillValues(){

		int[] values = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14};
		for(int i = 0; i < values.length; i++){
			assertTrue("validaite should return true uphill is legal", aco.validate(2, 2.5, 0.2, 0.2, 10, 15, values[i], 3));
		}
	}

	//end uphill paths testing

	//start number of agents testing

	@Test
	public void testValidationRulesAllowLegalAgentsValues(){

		int[] values = new int[]{25, 13, 19, 48, 29, 23, 11, 8, 2, 33, 40, 39, 6};
		for(int i = 0; i < values.length; i++){
			assertTrue("validaite should return true number of agents is legal", aco.validate(2, 2.5, 0.2, 0.2, values[i], 15, 3, 3));
		}
	}

	@Test
	public void testValidationRulesShouldNotAllowIllegalAgentsValues(){

		int[] values = new int[]{-1, -12, 55, 100, 87, 69, 52, 99, -72, -12, -5, 58, 60};
		for(int i = 0; i < values.length; i++){
			assertFalse("validaite should return true number of agents is illegal", aco.validate(2, 2.5, 0.2, 0.2, values[i], 15, 3, 3));
		}
	}

	public void testValidationRulesCatchAgentsValueBoundaryCase(){
		assertFalse("validaite should return false agents value is too low", aco.validate(2.5, 2.5, 0.2, 0.2, 0, 15, 3, 3));
		assertTrue("validaite should return true agents value is legal", aco.validate(2.5, 2.5, 0.2, 0.2, 1, 10, 3, 3));
		assertFalse("validaite should return false agents value is too high", aco.validate(2.5, 2.5, 0.2, 0.2, 51, 15, 3, 3));
		assertTrue("validaite should return true agents value is legal", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 50, 3, 3));
	}

	//end number of agents testing

	//start number of cities testing

	@Test
	public void testValidationRulesAllowLegalCitiesValues(){

		int[] values = new int[]{4, 17, 11, 20, 10, 9, 19, 23, 7, 16, 5, 9};
		for(int i = 0; i < values.length; i++){
			assertTrue("validaite should return true number of cities is legal", aco.validate(2, 2.5, 0.2, 0.2, 10, values[i], 3, 3));
		}
	}

	@Test
	public void testValidationRulesShouldNotAllowIllegalCitiesValues(){

		int[] values = new int[]{2, 1, -5, 29, 90, 35, 45, -10, -23, 70, 38};
		for(int i = 0; i < values.length; i++){
			assertFalse("validaite should return true number of cities is illegal", aco.validate(2, 2.5, 0.2, 0.2, 10, values[i], 3, 3));
		}
	}

	public void testValidationRulesCatchCitiesValueBoundaryCase(){
		assertFalse("validaite should return false cities value is too low", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 2, 3, 3));
		assertTrue("validaite should return true cities value is legal", aco.validate(2.5, 2.5, 0.2, 0.2, 1, 10, 3, 3));
		assertFalse("validaite should return false cities value is too high", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 26, 3, 3));
		assertTrue("validaite should return true cities value is legal", aco.validate(2.5, 2.5, 0.2, 0.2, 10, 25, 3, 3));
	}

	//end number of cities testing

	//start initial phero testing

	@Test
	public void testValidationRulesCatchHighInitialPheroValue(){
		assertFalse("validaite should return false initial phero is too high", aco.validate(2.5, 2.5, 0.2, 8, 10, 15, 3, 3));
		assertFalse("validaite should return false initial phero is too high", aco.validate(2.5, 2.5, 0.2, 4, 10, 15, 3, 3));
		assertFalse("validaite should return false initial phero is too high", aco.validate(2.6, 2.5, 0.2, 2, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchLowInitialPheroValue(){
		assertFalse("validaite should return false initial phero is too low", aco.validate(2.5, 2.5, 0.2, -6, 10, 15, 3, 3));
		assertFalse("validaite should return false initial phero is too low", aco.validate(2.5, 2.5, 0.2, -2, 10, 15, 3, 3));
		assertFalse("validaite should return false initial phero is too low", aco.validate(2.6, 2.5, 0.2, -4, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesCatchDecayInitialPheroBoundaryCase(){
		assertFalse("validaite should return false initial phero is too high", aco.validate(2.5, 2.5, 0.2, 1, 10, 15, 3, 3));
		assertTrue("validaite should return true initial phero is legal", aco.validate(2.5, 2.5, 0.2, 0.999999, 10, 15, 3, 3));
		assertFalse("validaite should return false initial phero is too low", aco.validate(2.5, 2.5, 0.2, 0, 10, 15, 3, 3));
		assertTrue("validaite should return true initial phero is legal", aco.validate(2.5, 2.5, 0.2, 0.0001, 10, 15, 3, 3));
	}

	@Test
	public void testValidationRulesAllowLegalInitialPheroValues(){

		double[] values = new double[]{0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 0.1232, 0.73562, 0.999999999};
		for(int i = 0; i < values.length; i++){
			assertTrue("validaite should return true initial phero is legal", aco.validate(2, 2.5, values[i], 0.2, 10, 15, 3, 3));
		}
	}

	@Test
	public void testValidationRulesCatchDecayEliteAgentBoundaryCase(){
		aco.setValues(2, 2.5, 0.2, 0.2, 30, 15, 3, 3);
		assertFalse("The number of elite ants is illegal", aco.checkEliteValueIsValid(-1));
		assertTrue("The number of elite ants is legal", aco.checkEliteValueIsValid(0));
		assertTrue("The number of elite ants is legal", aco.checkEliteValueIsValid(30));
		assertFalse("The number of elite ants is illegal", aco.checkEliteValueIsValid(31));
	}
	
	@Test
	public void testValidationRulesAllowLegalEliteAgentValues(){
		aco.setValues(2, 2.5, 0.2, 0.2, 30, 15, 3, 3);
		int[] values = new int[]{12, 10, 1, 5, 2, 29, 14, 9, 22, 13, 26};
		for(int i = 0; i < values.length; i++){
			assertTrue("The number of elite ants is legal", aco.checkEliteValueIsValid(values[i]));
		}
	}

	//end intial phero testing

	//<---------------------------------------- END PARAMETER TESTING ---------------------------------------->

	@Test
	public void testLoadFromFileSuccess(){
		assertNotNull(aco.loadWorldFromFile("largerProblem.tsp"));
	}

	@Test
	public void testLoadFromFileFailureCityXTooLarge(){
		assertNull(aco.loadWorldFromFile("cityXtooLarge.tsp"));
	}

	@Test
	public void testLoadFromFileFailureCityXTooSmall(){
		assertNull(aco.loadWorldFromFile("cityXtooSmall.tsp"));
	}

	@Test
	public void testLoadFromFileFailureCityYTooLarge(){
		assertNull(aco.loadWorldFromFile("cityYtooLarge.tsp"));
	}

	@Test
	public void testLoadFromFileFailureCityYTooSmall(){
		assertNull(aco.loadWorldFromFile("cityYtooSmall.tsp"));
	}

	//NOTE: we don't need to test loading values for other values as they are handled in the validate method which has been tested already

	@Test
	public void testLoadFromFileFailureMissFormattedFile(){
		assertNull(aco.loadWorldFromFile("badformat.tsp"));
	}

	@Test
	public void testLoadFromFileFailureMissNumberFormatException(){
		assertNull(aco.loadWorldFromFile("nfe.tsp"));
	}

}
