package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import che16.dcs.aber.ac.uk.utils.Globals;

/**
 * This Class represents the necessary components of a Travelling Salesman Problem representation. 
 * This Class also maintains and manipulates the collection of {@link Ant ants} and {@link City cities} as well as
 * Control the access to the pheromonne matrix.
 * @author Christopher Edwards
 *
 */
public class World {

	private AntColonyOptimisation aco;
	private int numberOfAnts, numberOfCities, numberOfUphill, method, eliteAntsCount;
	private List<City> cities;
	private double[][] distanceMatrix, invertedMatrix;
	private Pheromone[][] pheromone;
	private List<Ant> ants;
	private List<EliteAntData> eliteAnts;
	private double bestDistance;
	private LinkedList<Integer> bestRoute;


	/**
	 * Constructor with specified parameter values
	 * @param aco the current {@link AntColonyOptimisation} instance
	 * @param numberOfAnts the number of {@link Ant ants}
	 * @param noOfCities the number of {@link City cities}
	 * @param numberOfUphill the number of uphil paths to be generated
	 * @param method the method used for this algorithms execution
	 */
	public World(AntColonyOptimisation aco, int numberOfAnts, int noOfCities, int numberOfUphill, int method){
		this.aco = aco;
		this.numberOfAnts = numberOfAnts;
		this.numberOfCities = noOfCities;
		this.bestDistance = -1;
		this.bestRoute = null;
		this.method = method;
		this.numberOfUphill = numberOfUphill;
		//initCities MUST come first as matrix sizes are based off the number of cities
		initCities();
		initDistanceMatrix();
		initInvertedMatrix();
		initPheromones();
		initAnts();
		initUphill();


	}

	/**
	 * Constructor with specified parameter values and predefined {@link City} locations
	 * @param aco the current {@link AntColonyOptimisation} instance
	 * @param numberOfAnts the number of {@link Ant ants}
	 * @param noOfCities the number of {@link City cities}
	 * @param tempCities the collection of predefined {@link City} locations
	 * @param numberOfUphill the number of uphill paths to be generated
	 * @param method the method used for this algorithms execution
	 */

	public World(AntColonyOptimisation aco, int numberOfAnts, int noOfCities, ArrayList<City> tempCities, int numberOfUphill, int method) {
		this.aco = aco;
		this.numberOfAnts = numberOfAnts;
		this.numberOfCities = noOfCities;
		this.bestDistance = -1;
		this.bestRoute = null;
		this.numberOfUphill = numberOfUphill;
		this.method = method;
		//initCities MUST come first as matrix sizes are based off the number of cities
		initCitiesFromList(tempCities);
		initDistanceMatrix();
		initInvertedMatrix();
		initPheromones();
		//this must come after everything has been initialised
		initAnts();
		initUphill();

	}

	/**
	 * Instantiated the collection of {@link City cities} with predefined locations
	 * @param tempCities the predefined collection of {@link City cities}
	 */
	public void initCitiesFromList(ArrayList<City> tempCities) {
		cities = new ArrayList<City>();
		cities.addAll(tempCities);

	}

	/**
	 * Instantiate the collection of {@link Ant ants}. The starting location of suchs ants
	 * will be randomised to any valid {@link City} index.
	 */
	public void initAnts(){
		Random r = new Random();
		ants = new ArrayList<Ant>(numberOfAnts);
		for(int i = 0; i < numberOfAnts; i++){
			int index = r.nextInt(cities.size());
			for(City c: cities){
				if(index == c.getIndex()){
					c.adjustAntsHere(1);
					break;
				}
			}
			ants.add(new Ant(this, index));
		}

	}

	/**
	 * Instantiate the collection of {@link City cities} with randomised {@link City} coordinates.
	 */
	
	private void initCities(){
		//we do not set the number of cities as the max for the cities list as clicking will allow the addition of new cities
		cities = new ArrayList<City>();
		Random r = new Random();
		for(int i = 0; i < numberOfCities; i++){
			// the (+1) is to stop cities having the index '0' which would cause them to hald render out of view
			int x = r.nextInt(aco.getBoundaryX()) + 1;
			int y = r.nextInt(aco.getBoundaryY()) + 1;
			//make sure 2 cities can't spawn on top of each other
			for(City city: cities){
				if(x == city.getX() && y == city.getY()){
					x = r.nextInt(aco.getBoundaryX()) + 1;
					y = r.nextInt(aco.getBoundaryY()) + 1;
				}
			}
			cities.add(new City(x,y,i));	
		}

	}

	/**
	 * Initialise the data structures responsible for storing elite ant data.
	 * @param count the number of elite ants
	 */
	public void initEliteAnts(int count){
		this.eliteAntsCount= count;
		eliteAnts = new ArrayList<EliteAntData>(count);

	}

	/**
	 * Add a {@link City} to the current {@link City cities} collection
	 * @param city the City to add to the collection
	 */
	public void addCity(City city){
		cities.add(city);
	}

	/**
	 * 
	 * @return the {@link City cities} collection
	 */
	public List<City> getCities(){
		return cities;
	}

	/**
	 * 
	 * @return the pheromone matrix
	 */
	public Pheromone[][] getPheromone(){

		return pheromone;

	}

	/**
	 * Initialise the distanceMatrix based upon the size of the {@link City cities} collection. This distance metric used
	 * is the Euclidean distance.
	 */
	
	public void initDistanceMatrix(){
		distanceMatrix = new double[cities.size()][cities.size()];
		/*
		 * loop through i which represents the node which you are at, then loop through every node and calculate distance form node i
		 * e.g when i = 0 you are 'at' node zero, and distanceMatrix[0][j] will hold the euclidean distance between node 0 and node j
		 */
		for(int i = 0; i < distanceMatrix.length; i++){
			for(int j = 0; j < distanceMatrix[0].length; j++){
				distanceMatrix[i][j] = Globals.calculateEuclidianDistance(cities.get(i).getX(), cities.get(i).getY(), cities.get(j).getX(), cities.get(j).getY());
			}
		}
	}
	
	/**
	 * Initialise the InvertedDistanceMatrix based upon the size of the {@link City cities} collection.
	 */
		
	public void initInvertedMatrix(){
		invertedMatrix = new double[distanceMatrix.length][distanceMatrix[0].length];
		//this function literally takes the distanceMatrix and inverts the values (1/distanceMatrix[i][j]
		//this allows access to the inverted distance from any [index][y] to any [index][n] for use in the probability function
		//adapted from: https://code.google.com/p/antcolonyopt/source/checkout by Thomas Jungblut	
		for(int i = 0; i < distanceMatrix.length; i++){
			for(int j = 0; j < distanceMatrix[0].length; j++){
				invertedMatrix[i][j] = invertValue(distanceMatrix[i][j]);
			}
		}

	}

	/**
	 * Inverted a specified double value. 
	 * @param value the double value to invert
	 * @return 1.0 / the specified value
	 */
	public double invertValue(double value) {
		// takes a double value and returns the inverse of that (1/value)
		// however if the value is 0 then then return 0, you cannot do 1/0 
		if(value == 0.0d){
			return 0.0d;
		}

		//use 1.0d to ensure 1 is represented as a double
		return (1.0d/value);
	}

	public int getDistanceMatrixLength() {
		return distanceMatrix.length;
	}

	/**
	 * Instantiate the pheromone matrix based on the size of the {@link City cities} collection.
	 */
	public void initPheromones(){
		//same dimensions as the distance matrix as we want to model the paths between each city.
		pheromone = new Pheromone[distanceMatrix.length][distanceMatrix[0].length];
		for(int i = 0; i < pheromone.length; i++){
			for(int j = 0; j < pheromone[0].length; j++){
				pheromone[i][j] = new Pheromone(aco.getInitialPheromone());
			}
		}
	}

	/**
	 * Update the pheromone for edge x,y given the specified value
	 * @param x the x coordinate for the edge
	 * @param y the y coordinate for the edge
	 * @param newPheromone the amount of pheromone to add to the edge
	 */
	public void updatePheromone(int x, int y, double newPheromone) {
		double phero = calculatePheromones(pheromone[x][y].getPheromoneValue(), newPheromone);
		//if phero is not negative then update the current concentration
		//if phero is negative then just set it as 0, you can't have negative phero on an edge
		if (phero >= 0.0d) {
			pheromone[x][y].setPheromoneValue(phero);
		} else {
			pheromone[x][y].setPheromoneValue(0.0d);
		}

	}

	/**
	 * Calculate the pheromone value given the specified value. This factors in the current pheromone levels
	 * and decayRate.
	 * @param current the current pheromone concentration
	 * @param newPheromone the amount of new pheromone to add
	 * @return the update pheromone concentration after deposit and decay operations
	 */
	public double calculatePheromones(double current, double newPheromone) {
		//we dont need to store the result in a temporary variable, just return the equation in place
		return ((1 - aco.getDecayRate()) * current + newPheromone);

	}

	/**
	 * 
	 * @return the alpha parameter value
	 */
	public double getAlpha() {
		return aco.getAlpha();
	}
	
	/**
	 * 
	 * @return the beta parameter value
	 */

	public double getBeta() {
		return aco.getBeta();
	}

	/**
	 * 
	 * @return the inverted distance matrix
	 */
	public double[][] getInvertedMatrix() {
		return invertedMatrix;
	}

	/**
	 * 
	 * @return the distance matrix
	 */
	public double[][] getDistanceMatrix() {
		return distanceMatrix;
	}

	/**
	 * 
	 * @return the collection of {@link Ant ants}
	 */
	public List<Ant> getAnts() {
		return ants;
	}

	/**
	 * 
	 * @return the Q parameter value
	 */
	public double getQ() {
		return aco.getQ();
	}

	public void updateModel() {
		aco.notifyCanvas();

	}

	//debugging print
	public void printPheroMatrix() {
		System.out.println("Phero Matrix is: ");
		for(int i = 0; i < pheromone.length; i++){
			for(int j = 0; j < pheromone[0].length; j++){
				System.out.print(pheromone[i][j].getPheromoneValue() + " | ");
			}
			System.out.println();
		}
	}

	public void decayPhero() {
		for(int i = 0; i < pheromone.length; i++){
			for(int j = 0; j < pheromone[0].length; j++){
				updatePheromone(i, j, pheromone[i][j].getNewPhero());
				//reset the new phero values
				pheromone[i][j].resetNewPhero();
			}
		}
	}

	/**
	 * 
	 * @param best the best distance
	 */
	public void setBestDistance(double best){
		this.bestDistance = best;
	}

	/**
	 * 
	 * @return the best distance
	 */
	public double getBestDistance(){
		return bestDistance;
	}

	/**
	 * 
	 * @return the current best route
	 */
	public LinkedList<Integer> getBestRoute() {
		return bestRoute;
	}

	/**
	 * 
	 * @param best the best route
	 */
	public void setBestRoute(LinkedList<Integer> best) {
		this.bestRoute = best;
	}

	/**
	 * This method enables modification to the size of the {@link City cities} collection
	 * after instantiation. This method enables a specified number of additional {@link City} locations.
	 * @param diff the number of City locations to add
	 */
	public void addToCities(int diff) {
		Random r = new Random();
		for(int i = 0; i < diff; i++){
			int x = r.nextInt(aco.getBoundaryX()) + 1;
			int y = r.nextInt(aco.getBoundaryY()) + 1;
			//to ensure indexes is correct, it will current number of cities + new index (e.g. 5 + i)
			cities.add(new City(x,y,(numberOfCities) + i));
		}
		//because of the change we need to re-init the matrices
		initDistanceMatrix();
		initInvertedMatrix();
		initPheromones();
		initAnts();

		aco.notifyCanvas();

	}

	/**
	 * This method enables modification to the size of the {@link City cities} collection
	 * after instantiation. This methods enables a specified number of {@link City} locations to be removed.
	 * @param diff the number of City locations to add
	 */
	
	public void removeCities(int diff) {
		for(int i = 0; i < diff; i++){
			//to ensure indexes is correct, it will current number of cities + new index (e.g. 5 + i)
			cities.remove(cities.size()-1);
		}
		//because of the change we need to re-init the matrices
		initDistanceMatrix();
		initInvertedMatrix();
		initPheromones();
		initAnts();

		aco.notifyCanvas();

	}

	/**
	 * 
	 * @param ants the number of {@link Ant ants}
	 */
	public void updateAnts(int ants) {
		this.numberOfAnts = ants;
		initAnts();

	}

	/**
	 * 
	 * @return the running status
	 */
	public boolean getRunning() {
		return aco.getRunning();
	}

	/**
	 * Used to adjust the number of {@link Ant ants} at a {@link City} index
	 * @param startIndex the starting City index of the Ant
	 * @param finishedIndex the finishing City index of the Ant
	 */
	public void adjustAntsAtCity(int startIndex, int finishedIndex){
		boolean start = false;
		boolean end = false;
		for(City c: cities){
			if(c.getIndex() == startIndex){
				c.adjustAntsHere(-1);
				start = true;
			}
			if(c.getIndex() == finishedIndex){
				c.adjustAntsHere(1);
				end = true;
			}

			if(start && end){
				//rather than going through the whole list of cities, exit if we have found the cities of interest
				return;
			}
		}
	}

	/**
	 * This method generates the correct number of uphill paths. These paths are randomly generated 
	 * and cost twice the initial path cost.
	 */
	private void initUphill(){
		if(!(aco.getUphillAtive())){
			return;
		}
		Random r = new Random();
		while(numberOfUphill > 0){
			int index = r.nextInt(cities.size());
			City temp = cities.get(index);

			index = r.nextInt(cities.size());
			if(!(temp.getUphilRoutes().contains(index)) && (index != temp.getIndex())){
				temp.addToUphil(index);
				numberOfUphill--;
				distanceMatrix[temp.getIndex()][index] = (distanceMatrix[temp.getIndex()][index] * 2);
				invertedMatrix[temp.getIndex()][index] = 1/distanceMatrix[temp.getIndex()][index];
			}
		}
	}

	public int getMethod(){
		return method;
	}

	/**
	 * This method is used to deposit pheromone on all the edges used the the current collection
	 * of elite routes.
	 */
	public void depositBest() {
		//research suggests 1/4 * number of cities, or number of cities is the best e value
		//could also use e = number of cities
		LinkedList<Integer> best;
		for(EliteAntData data: eliteAnts){
			System.out.println(data.getEliteRoute());
			best = data.getEliteRoute();
			double e = (1/4) * numberOfCities;
			for(int i = 0; i < best.size(); i++){
				if(i + 1 < best.size()){
					pheromone[best.get(i)][best.get(i+1)].addToNewPhero(pheromone[best.get(i)][best.get(i+1)].getNewPhero() + e);
				}
			}
		}
	}

	/**
	 * 
	 * @return the number of elite ants
	 */
	public int getEliteCount() {
		return eliteAntsCount;
	}

	/**
	 * 
	 * @return the elite ants
	 */
	public List<EliteAntData> getEliteAnts() {
		return eliteAnts;
	}

	/**
	 * This Class is used to store the data relevant to an elite ant. Rather than storing 
	 * an {@link Ant} object in full only the key values are stored using an Object of this type.
	 * @author Christopher Edwards
	 *
	 */

	class EliteAntData{

		private double distance;
		private LinkedList<Integer> eliteRoute;

		public EliteAntData(double distance, LinkedList<Integer> eliteRoute){
			this.distance = distance;
			this.eliteRoute = eliteRoute;
		}

		public double getDistance(){
			return distance;
		}

		public LinkedList<Integer> getEliteRoute(){
			return eliteRoute;
		}
	}


}
