package che16.dcs.aber.ac.uk.model;

/**
 * This Class is used to model an edge between two {@link City} locations in the graph. This
 * Class will contain information relevant to the pheormone concentration on such edge.
 * @author Christopher Edwards
 *
 */

public class Pheromone {

	private double pheromoneValue;

	private double newPhero;

	/**
	 * Create a new Pheromone Object with a specified initial pheromone value
	 * @param phero the initial pheromone concentration
	 */
	public Pheromone(double phero){
		this.pheromoneValue = phero;
		newPhero = 0.0d;
	}

	/**
	 * 
	 * @return the pheormone concentration
	 */
	public double getPheromoneValue() {
		return pheromoneValue;
	}

	/**
	 * 
	 * @param pheromoneValue the pheromone concentration
	 */
	public void setPheromoneValue(double pheromoneValue) {
		this.pheromoneValue = pheromoneValue;
	}

	/**
	 * 
	 * @return the new pheromone to be desposited
	 */
	public double getNewPhero() {
		return newPhero;
	}

	/**
	 * Increment the amount of new pheromone to be deposited by a specified amount.
	 * @param pheromoneDeposit the amount of new pheromone ot add
	 */
	public void addToNewPhero(double pheromoneDeposit) {
		this.newPhero += pheromoneDeposit;		
	}

	/**
	 * Reset the amount of new pheromone to add to zero.
	 */
	public void resetNewPhero() {
		this.newPhero = 0.0d;

	}

}
