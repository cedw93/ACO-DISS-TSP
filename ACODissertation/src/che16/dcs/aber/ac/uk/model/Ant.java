package che16.dcs.aber.ac.uk.model;

import java.util.Arrays;

public class Ant{

	/*
	 * The Ant Class is an adaptation of the Agent Class provided by Thomas Jungblut: https://code.google.com/p/antcolonyopt/
	 * Blog: http://codingwiththomas.blogspot.co.uk/2011/08/ant-colony-optimization-for-tsp.html
	 */

	private double totalDistanceWalked;
	private World world;
	private boolean finished;
	private int[] route;
	private int current;
	//track which cities have been visited using simple true or false
	private boolean[] visited;
	//the number of cities which have not been visited on this tour
	private int unvisited;

	public Ant(World world, int startLocation){
		this.world = world;
		this.current = startLocation;
		this.finished = false;
		//this will store the path the current ant has taken through all n cities
		route = new int[world.getCities().size()];
		visited = new boolean[world.getCities().size()];
		//the agent has visited it's start location so reflect that in the visited array
		visited[startLocation] = true;
		//we have visited one city thus set the total number of cities unvisited to (n - 1) where n is the number of cities
		unvisited = visited.length - 1;
		//System.out.println(this.current);
		//move();

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

		//create a location to store the probability for all next locations
		//this can then be easily accessed to return the next index for the ant's move
		double[] probabilityMatrix = new double[visited.length];

		//we need to calculate the sum of all possible solutions for use in the probability function
		double totalSum = 0.0d;
		for(int i = 0; i < visited.length; i++){
			//extract this to its own function rather than cluttering the loop with the probability equation (excluding the division)
			totalSum += calculateIndividualProbability(current, i);
		}

		//TODO: make this return an actual value of use.
		return 2;
	}

	public boolean move(){
		//if there are still some nodes unvisited then the ant is not finished
		if(unvisited > 0){

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



}
