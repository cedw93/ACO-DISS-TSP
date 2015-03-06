package che16.dcs.aber.ac.uk.model;

public class Pheromone {

	private double pheromoneValue;

	private double newPhero;

	public Pheromone(double phero){
		this.pheromoneValue = phero;
		newPhero = 0.0d;
	}

	public double getPheromoneValue() {
		return pheromoneValue;
	}

	public void setPheromoneValue(double pheromoneValue) {
		this.pheromoneValue = pheromoneValue;
	}

	public double getNewPhero() {
		return newPhero;
	}


	public void addToNewPhero(double pheromoneDeposit) {
		this.newPhero += pheromoneDeposit;		
	}

	public void resetNewPhero() {
		this.newPhero = 0.0d;

	}

}
