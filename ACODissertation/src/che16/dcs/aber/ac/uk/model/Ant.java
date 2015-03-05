package che16.dcs.aber.ac.uk.model;

import java.util.LinkedList;
import java.util.Random;

public class Ant{

	/*
	 * The Ant Class is an adaptation of the Agent Class provided by Thomas Jungblut: https://code.google.com/p/antcolonyopt/
	 * Blog: http://codingwiththomas.blogspot.co.uk/2011/08/ant-colony-optimization-for-tsp.html
	 */

	private double totalDistanceWalked;
	private World world;
	private boolean finished;
	private LinkedList<Integer> route;
	private int current, next;
	//track which cities have been visited using simple true or false
	private boolean[] visited;
	//the number of cities which have not been visited on this tour
	private int unvisited;

	public Ant(World world, int startLocation){
		this.world = world;
		this.current = startLocation;
		this.finished = false;
		//this will store the path the current ant has taken through all n cities
		route = new LinkedList<Integer>();
		visited = new boolean[world.getCities().size()];
		//the agent has visited it's start location so reflect that in the visited array
		visited[startLocation] = true;
		//we have visited one city thus set the total number of cities unvisited to (n - 1) where n is the number of cities
		unvisited = visited.length - 1;
		//System.out.println(this.current);
		addToRoute(current);
		//	move();

	}

	//probability function
	private double calculateTotalProbability(int x, int y, double totalSum){

		double result = ((Math.pow(world.getPheromone()[x][y], world.getAlpha())) * (Math.pow(world.getInvertedMatrix()[x][y], world.getBeta()))) ;
		return result / totalSum;
	}

	private double calculateIndividualProbability(int x, int y){
		//could just return the equation rather than having the placeholder variable 'result'
		double result = ((Math.pow(world.getPheromone()[x][y], world.getAlpha())) * (Math.pow(world.getInvertedMatrix()[x][y], world.getBeta()))) ;
		return result;
	}

	private int getNextCity(){
		//This is an adapted version of a similar method provided by Thomas Jungblut found here: https://code.google.com/p/antcolonyopt/
		//create a location to store the probability for all next locations
		//this can then be easily accessed to return the next index for the ant's move
		double[] probability = new double[visited.length];

		//we need to calculate the sum of all possible solutions for use in the probability function
		double totalSum = 0.0d;
		double sum = 0.0d;
		double probabilitySum = 0.0d;

		next = -1;

		for(int i = 0; i < visited.length; i++){
			//extract this to its own function rather than cluttering the loop with the probability equation (excluding the division)
			totalSum += calculateIndividualProbability(current, i);
		}

		//once we have the value for totalSum (which the sum off all solutions evaluation)
		for(int i = 0; i < visited.length; i++){
			//if we haven't visited index i (visited[i] == false)
			if(!visited[i]){
				probability[i] = calculateTotalProbability(i, current, totalSum);
				//sum will be used in the weighted probability calculations
				sum += probability[i];
				//store the index in case there is a problem with the sum, if sum is zero this index will be returned
				next = i;
			}
		}

		//if sum is 0 there return, as this will be used in a division it cannot be zero
		if(sum == 0.0d){
			return next;
		}

		/* We need to give each index of the probability weighting based on the result of calculateToalProbability
		 *  this result is then divided by the total sum of all probabilities (usually 1)
		 *  The result of this division is then put in the correct index in the matrix in order to get the probability weighting
		 */
		for(int i = 0; i < visited.length; i++){
			probabilitySum += (probability[i] / sum);
			probability[i] = probabilitySum;

		}

		//nextDouble returns a value between 0 and 1, so this can be used to effectively select a 'random' index based on the weighted probability
		//provided virtually as is by Thomas Jungblut: https://code.google.com/p/antcolonyopt/
		//this is what makes the ant walk 'randomly'
		double r = new Random().nextDouble();
		for (int i = 0; i < visited.length; i++) {
			if (!visited[i]) {
				if (r <= probability[i]) {
					next = i;
				}
			}
		}

		return next;
	}

	public boolean move(){
		//if there are still some nodes unvisited then the ant is not finished
		if(unvisited > 0){
			//check to see if there are no problems with getting next city index.
			getNextCity();
			if(next > -1){
				totalDistanceWalked += world.getDistanceMatrix()[current][next];
				double pheromoneDeposit = (world.getQ() / totalDistanceWalked);
				world.updatePheromone(current, next, pheromoneDeposit);

				this.current = next;
				//vist a new location so decrease the amount of cities left to visit
				visited[current] = true;
				addToRoute(current);
				unvisited--;

			}
		}else{
			//if there are no more unvisited indexes then the ant must be finished
			this.finished = true;
		}

		//return if the ant has finished or not, this can be used to terminate an ant
		return finished;
	}

	public int getCurrentIndex() {
		return current;
	}

	public boolean getFinished() {
		return finished;
	}

	public void addToRoute(int index){
		route.add(index);
		//System.out.println("Current route: " + route);

	}

	public double getTotalDistance() {
		return totalDistanceWalked;
	}

	public LinkedList<Integer> getRoute() {
		return route;
	}



}
