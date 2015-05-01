package che16.dcs.aber.ac.uk.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import che16.dcs.aber.ac.uk.model.World.EliteAntData;

/**
 * This Class is used to represent and contain the behaviours required by an ant which will
 * deployed in the current {@link World} instance in order to solve the current problem.
 * 
 * @author Christopher Edwards
 *
 */

public class Ant{

	/*
	 * The Ant Class is an adaptation of the Agent Class provided by Thomas Jungblut: https://code.google.com/p/antcolonyopt/
	 * Blog: http://codingwiththomas.blogspot.co.uk/2011/08/ant-colony-optimization-for-tsp.html
	 */

	private double totalDistanceWalked;
	private World world;
	private boolean finished, isMoving;
	private LinkedList<Integer> route;
	private int current, next, start;
	//track which cities have been visited using simple true or false
	private boolean[] visited;
	private int[] movementTracker;
	//the number of cities which have not been visited on this tour
	private int unvisited;

	private final Random random = new Random(System.nanoTime());

	/**
	 * Ant Constructor.
	 * 
	 * @param world current {@link World} instance
	 * @param startLocation the starting location for this ant in terms of {@link City} index
	 */
	public Ant(World world, int startLocation){
		this.world = world;
		this.start = startLocation;
		this.current = startLocation;
		this.finished = false;
		this.isMoving = false;
		movementTracker = new int[2];
		//this will store the path the current ant has taken through all n cities
		route = new LinkedList<Integer>();
		visited = new boolean[world.getCities().size()];
		//the agent has visited it's start location so reflect that in the visited array
		visited[startLocation] = true;
		//we have visited one city thus set the total number of cities unvisited to (n - 1) where n is the number of cities
		unvisited = visited.length - 1;


	}

	/**
	 * This method returns the probability for this Ant to traverse edge x,y.
	 * 
	 * @param x the x coordinate for a given edge
	 * @param y the y coordinate for a given edge
	 * @param totalSum the current sum of every solutions probability
	 * @return the probability of edge x,y being traversed
	 */
	//probability function
	private double calculateTotalProbability(int x, int y, double totalSum){

		double result = ((Math.pow(world.getPheromone()[x][y].getPheromoneValue(), world.getAlpha())) * (Math.pow(world.getInvertedMatrix()[x][y], world.getBeta()))) ;
		return result / totalSum;
	}

	/**
	 * This method returns the individual probability for a given edge x,y. The sum of the returned value
	 * for every solution can then be used in the {@link #calculateTotalProbability(int, int, double)} method
	 * 
	 * @param x the x coordinate for a given edge
	 * @param y the y coordinate for a given edge
	 * @return the result of the probability function
	 */

	private double calculateIndividualProbability(int x, int y){
		//could just return the equation rather than having the placeholder variable 'result'
		double result = ((Math.pow(world.getPheromone()[x][y].getPheromoneValue(), world.getAlpha())) * (Math.pow(world.getInvertedMatrix()[x][y], world.getBeta()))) ;
		return result;
	}



	/*
	 * Below is a movement function provided by Thomas Junglbut as part of his TSP ACO implementation
	 * Blog dicussing the code can be found here: http://codingwiththomas.blogspot.co.uk/2011/08/ant-colony-optimization-for-tsp.html
	 * and a github repository can be found here: http://code.google.com/p/antcolonyopt/
	 * code is used with rights and ownership with respect to the original owner
	 */

	/**
	 * This method is used to return this Ants next movement based on the probability of each edge 
	 * and this Ants current location.
	 * 
	 * @param y the Ants current location in terms of {@link City} index
	 * @return the {@link City} index of the Ants next movement
	 */

	// TODO really needs improvement
	public final int getNextProbableNode(int y) {
		//This is an adapted version of a similar method provided by Thomas Jungblut found here: https://code.google.com/p/antcolonyopt/
		//create a location to store the probability for all next locations
		//this can then be easily accessed to return the next index for the ant's move
		//only do this if there is in fact locations to move to
		if (unvisited > 0) {
			int danglingUnvisited = -1;
			final double[] weights = new double[visited.length];

			double columnSum = 0.0d;
			for (int i = 0; i < visited.length; i++) {
				columnSum += calculateIndividualProbability(y, i);
			}



			//once we have the value for sum (which the sum off all solutions evaluation)
			double sum = 0.0d;
			for (int x = 0; x < visited.length; x++) {
				if (!visited[x]) {
					weights[x] = calculateTotalProbability(x, y, columnSum);
					sum += weights[x];
					danglingUnvisited = x;
				}
			}


			//if sum is 0 then return, as this will be used in a division it cannot be zero
			if (sum == 0.0d)
				return danglingUnvisited;


			/*  We need to give each index of the probability weighting based on the result of calculateToalProbability
			 *  this result is then divided by the total sum of all probabilities (usually 1)
			 *  The result of this division is then put in the correct index in the matrix in order to get the probability weighting
			 *
			 */
			double pSum = 0.0d;
			for (int i = 0; i < visited.length; i++) {
				pSum += weights[i] / sum;
				weights[i] = pSum;
			}


			//nextDouble returns a value between 0 and 1, so this can be used to effectively select a 'random' index based on the weighted probability
			//provided virtually as is by Thomas Jungblut: https://code.google.com/p/antcolonyopt/
			//this is what makes the ant walk 'randomly'
			final double r = random.nextDouble();
			for (int i = 0; i < visited.length; i++) {
				if (!visited[i]) {
					if (r <= weights[i]) {

						return i;
					}
				}
			}

		}
		return -1;
	}

	/**
	 * This method is used to move the Ant until it has no further {@link City} locations to visit.
	 * This method also tracks the Ants current location, last movements and modified the pheromone
	 * concentration for the edges this Ant traverses.
	 * 
	 * If the ElitistAnt algorithm is being used, this method with also control and maintain the 
	 * current collection of elite agents to ensure correct behaviours are being modelled.
	 * 
	 */

	public void move(){

		int lastNode = start;
		int next = start;
		while ((next = getNextProbableNode(lastNode)) != -1){
			//stop if the user said so!
			if(!world.getRunning()){
				return;
			}
			movementTracker[0] = lastNode;
			movementTracker[1] = next;
			world.adjustAntsAtCity(movementTracker[0], movementTracker[1]);
			addToRoute(lastNode);
			totalDistanceWalked += world.getDistanceMatrix()[lastNode][next];
			double pheromoneDeposit = (world.getQ() / totalDistanceWalked);
			//add to the new pheromone amount being deposited on this location
			world.getPheromone()[lastNode][next].addToNewPhero((pheromoneDeposit));
			visited[next] = true;
			lastNode = next;
			current = next;
			unvisited--;
			//tell the view where the ant has moved to
			world.updateModel();
		}
		finished = true;
		//when an ant is done 'remove it from the cities count
		//as we only need to know its last know location pass -1 as the destination index
		world.adjustAntsAtCity(lastNode, -1);
		addToRoute(lastNode);
		/*
		 * See if the ant is the current best, if world.getBestDistance() == -1 then this ant is the first to finish
		 * and is therefore the best be default, all other operations after the first to finish should be evaluated against the
		 * right hand condition. This calculated if this ant has travelled less distance than the current best, if it has then this is the best ant.
		 */

		if((world.getBestDistance() == -1.0d) || (this.totalDistanceWalked < world.getBestDistance())){
			if(world.getMethod() == 1){
				if(world.getEliteAnts().size() +1 < world.getEliteCount()){
					world.getEliteAnts().add(world.new EliteAntData(this.totalDistanceWalked, route));
				}else{
					EliteAntData worst = null;
					for(EliteAntData data: world.getEliteAnts()){
						if(worst == null){
							worst = data;
						}else{
							if(data.getDistance() < worst.getDistance()){
								worst = data;
							}
						}
					}
					world.getEliteAnts().remove(worst);
					world.getEliteAnts().add(world.new EliteAntData(this.totalDistanceWalked, route));
				}
			}

			world.setBestDistance(totalDistanceWalked);
			world.setBestRoute(route);
		}
		if(world.getMethod() == 1){
			world.depositBest();
		}
		world.decayPhero();
	}

	/**
	 * 
	 * @return the Ants current {@link City} index
	 */
	public int getCurrentIndex() {
		return current;
	}

	/**
	 * 
	 * @return the Ants finished status
	 */
	public boolean getFinished() {
		return finished;
	}

	/**
	 * Adds a {@link City} index to the current route
	 * @param index the {@link City} index to add
	 */
	public void addToRoute(int index){
		route.add(index);
		//System.out.println("Current route: " + route);

	}

	/**
	 * 
	 * @return the totalDistance walked
	 */
	public double getTotalDistance() {
		return totalDistanceWalked;
	}

	/**
	 * 
	 * @return the route taken
	 */
	public LinkedList<Integer> getRoute() {
		return route;
	}

	/**
	 * 
	 * @return the last 2 {@link City} which the Ant visited
	 */
	public int[] getMovementTracker(){
		return movementTracker;
	}

	/**
	 * 
	 * @return the status of the isMoving variable
	 */
	public boolean isMoving(){
		return isMoving;
	}

	@Override
	public String toString(){
		return "Ant: \n start: " + start + "\ncurrent: " + current + "\nfinished: " + finished + "\nroute: " + route + "\nvisited: " + Arrays.toString(visited) + "\nunvisited: " + unvisited;
	}

	/**
	 * 
	 * @param status the isMoving status
	 */
	public void setMoving(boolean status) {
		this.isMoving = status;

	}

	/**
	 * 
	 * @return the summary of visited and unvisited {@link City} indexes
	 */
	public boolean[] getVisited(){
		return visited;
	}

	/**
	 * This method is used to move the Ant on a step-by-step basis.
	 * This method also tracks the Ants current location, last movements and modified the pheromone
	 * concentration for the edges this Ant traverses.
	 * 
	 * This method is only used when the application is in step-mode, otherwise the {@link #move()} method is used.
	 * 
	 * If the ElitistAnt algorithm is being used, this method with also control and maintain the 
	 * current collection of elite agents to ensure correct behaviours are being modelled.
	 * 
	 */

	
	public void step() {

		int lastNode = current;
		int next = start;
		if ((next = getNextProbableNode(lastNode)) != -1){
			//stop if the user said so!
			if(!world.getRunning()){
				return;
			}
			movementTracker[0] = lastNode;
			movementTracker[1] = next;
			world.adjustAntsAtCity(movementTracker[0], movementTracker[1]);
			addToRoute(lastNode);
			totalDistanceWalked += world.getDistanceMatrix()[lastNode][next];
			double pheromoneDeposit = (world.getQ() / totalDistanceWalked);
			//add to the new pheromone amount being deposited on this location
			world.getPheromone()[lastNode][next].addToNewPhero((pheromoneDeposit));
			visited[next] = true;
			lastNode = next;
			current = next;
			unvisited--;
			//tell the view where the ant has moved to
			world.updateModel();
		}
		if(unvisited == 0){
			//when an ant is done 'remove it from the cities count
			//as we only need to know its last know location pass -1 as the destination index
			world.adjustAntsAtCity(lastNode, -1);
			current = lastNode;
			addToRoute(lastNode);


			/*
			 * See if the ant is the current best, if world.getBestDistance() == -1 then this ant is the first to finish
			 * and is therefore the best be default, all other operations after the first to finish should be evaluated against the
			 * right hand condition. This calculated if this ant has travelled less distance than the current best, if it has then this is the best ant.
			 */

			if((world.getBestDistance() == -1.0d) || (this.totalDistanceWalked < world.getBestDistance())){
				if(world.getMethod() == 1){
					if(world.getEliteAnts().size() +1 < world.getEliteCount()){
						world.getEliteAnts().add(world.new EliteAntData(this.totalDistanceWalked, route));
					}else{
						EliteAntData worst = null;
						for(EliteAntData data: world.getEliteAnts()){
							if(worst == null){
								worst = data;
							}else{
								if(data.getDistance() < worst.getDistance()){
									worst = data;
								}
							}
						}
						world.getEliteAnts().remove(worst);
						world.getEliteAnts().add(world.new EliteAntData(this.totalDistanceWalked, route));
					}
				}

				world.setBestDistance(totalDistanceWalked);
				world.setBestRoute(route);
			}
			if(world.getMethod() == 1){
				world.depositBest();
			}
			world.decayPhero();
		}
	}

	/**
	 * 
	 * @return the number of unvisited {@link City} locations
	 */
	public int getUnvisted() {
		return unvisited;
	}

	/**
	 * 
	 * @param b the finished status
	 */
	
	public void setFinished(boolean b) {
		this.finished = b;

	}

}
