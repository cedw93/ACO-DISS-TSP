package che16.dcs.aber.ac.uk.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import che16.dcs.aber.ac.uk.utils.MathsHelper;

public class World {

	private AntColonyOptimisation aco;
	private int width, height;
	private List<City> cities;
	private double[][] pheromone, distanceMatrix, invertedMatrix;
	private List<Ant> ants;


	public World(AntColonyOptimisation aco, int width, int height, int numberOfAnts){
		this.aco = aco;
		this.width = width;
		this.height = height;
		cities = new ArrayList<City>();
		//TODO: AUTOMATE THIS INDEXING AND ADDING
		cities.add(new City(20,20,0));
		cities.add(new City(10,12,1));
		cities.add(new City(25,2,2));
		cities.add(new City(3,22,3));
		initDistanceMatrix();
		initInvertedMatrix();
		initPheromones();
		//this must come after everything has been initialised
		ants = new ArrayList<Ant>(numberOfAnts);
		ants.add(new Ant(this, 0));

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void addCity(City city){
		cities.add(city);
	}

	public List<City> getCities(){
		return cities;
	}

	public double[][] getPheromone(){

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
				//System.out.println("[i][j] == [" + i + "][" + j + "]");
				//System.out.println("Euclidean == " + distanceMatrix[i][j]);

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
				//System.out.println("InvertedMatrix[" + i + "][" + j + "] == " + invertedMatrix[i][j]);
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
		pheromone = new double[distanceMatrix.length][distanceMatrix[0].length];
		for(int i = 0; i < pheromone.length; i++){
			for(int j = 0; j < pheromone[0].length; j++){
				pheromone[i][j] = aco.getInitialPheromone();
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
		double phero = calculatePheromones(pheromone[x][y], newPheromone);
		//if phero is not negative then update the current concentration
		//if phero is negative then just set it as 0, you can't have negative phero
		if (phero >= 0.0d) {
			pheromone[x][y] = phero;
		} else {
			pheromone[x][y] = 0;
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

}
