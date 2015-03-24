package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import che16.dcs.aber.ac.uk.utils.MathsHelper;

public class World {

	private AntColonyOptimisation aco;
	private int numberOfAnts, numberOfCities, numberOfUphill;
	private List<City> cities;
	private double[][] distanceMatrix, invertedMatrix;
	private Pheromone[][] pheromone;
	private List<Ant> ants;
	private double bestDistance;
	private LinkedList<Integer> bestRoute;

	public World(AntColonyOptimisation aco, int numberOfAnts, int noOfCities, int numberOfUphill){
		this.aco = aco;
		this.numberOfAnts = numberOfAnts;
		this.numberOfCities = noOfCities;
		this.bestDistance = -1;
		this.bestRoute = null;
		this.numberOfUphill = numberOfUphill;
		//initCities MUST come first as matrix sizes are based off the number of cities
		initCities();
		initDistanceMatrix();
		initInvertedMatrix();
		initPheromones();
		initAnts();
		initUphill();
		//this must come after everything has been initialised


	}

	public World(AntColonyOptimisation aco, int numberOfAnts, int noOfCities, ArrayList<City> tempCities, int numberOfUphill) {
		this.aco = aco;
		this.numberOfAnts = numberOfAnts;
		this.numberOfCities = noOfCities;
		this.bestDistance = -1;
		this.bestRoute = null;
		this.numberOfUphill = numberOfUphill;
		//initCities MUST come first as matrix sizes are based off the number of cities
		initCitiesFromList(tempCities);
		initDistanceMatrix();
		initInvertedMatrix();
		initPheromones();
		//this must come after everything has been initialised
		initAnts();
		initUphill();

	}

	private void initCitiesFromList(ArrayList<City> tempCities) {
		cities = new ArrayList<City>();
		cities.addAll(tempCities);

	}

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

	public void addCity(City city){
		cities.add(city);
	}

	public List<City> getCities(){
		return cities;
	}

	public Pheromone[][] getPheromone(){

		return pheromone;

	}

	public void initDistanceMatrix(){
		distanceMatrix = new double[cities.size()][cities.size()];
		/*
		 * loop through i which represents the node which you are at, then loop through every node and calculate distance form node i
		 * e.g when i = 0 you are 'at' node zero, and distanceMatrix[0][j] will hold the euclidean distance between node 0 and node j
		 */
		for(int i = 0; i < distanceMatrix.length; i++){
			for(int j = 0; j < distanceMatrix[0].length; j++){
				distanceMatrix[i][j] = MathsHelper.calculateEuclidianDistance(cities.get(i).getX(), cities.get(i).getY(), cities.get(j).getX(), cities.get(j).getY());
			}
		}
	}

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

	private double invertValue(double value) {
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

	public void initPheromones(){
		//same dimensions as the distance matrix as we want to model the paths between each city.
		pheromone = new Pheromone[distanceMatrix.length][distanceMatrix[0].length];
		for(int i = 0; i < pheromone.length; i++){
			for(int j = 0; j < pheromone[0].length; j++){
				pheromone[i][j] = new Pheromone(aco.getInitialPheromone());
			}
		}
		//DEBUG PRINTING
		/*for(int i = 0; i < pheromone.length; i++){
			for(int j = 0; j < pheromone[0].length; j++){
				System.out.print(pheromone[i][j] + " ");
			}
			System.out.println();
		}
		 */

	}

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

	//private as it should only ever be called from updatePheromone method
	private double calculatePheromones(double current, double newPheromone) {
		//we dont need to store the result in a temporary variable, just return the equation in place
		return ((1 - aco.getDecayRate()) * current + newPheromone);

	}

	public double getAlpha() {
		return aco.getAlpha();
	}

	public double getBeta() {
		return aco.getBeta();
	}

	public double[][] getInvertedMatrix() {
		return invertedMatrix;
	}

	public double[][] getDistanceMatrix() {
		return distanceMatrix;
	}

	public List<Ant> getAnts() {
		return ants;
	}

	public double getQ() {
		return aco.getQ();
	}

	public void updateModel() {
		aco.notifyCanvas();

	}

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

	public void setBestDistance(double best){
		this.bestDistance = best;
	}

	public double getBestDistance(){
		return bestDistance;
	}

	public LinkedList<Integer> getBestRoute() {
		return bestRoute;
	}

	public void setBestRoute(LinkedList<Integer> best) {
		this.bestRoute = best;
	}

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

	public void updateAnts(int ants) {
		this.numberOfAnts = ants;
		initAnts();

	}

	public boolean getRunning() {
		return aco.getRunning();
	}

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
				//saves times and resources, if above results are met then exit we are done here
				return;
			}
		}
	}

	private void initUphill(){
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

}
