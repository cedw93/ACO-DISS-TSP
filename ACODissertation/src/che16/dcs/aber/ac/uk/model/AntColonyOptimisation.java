package che16.dcs.aber.ac.uk.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Scanner;

public class AntColonyOptimisation extends Observable{

	private World world;
	private int boundaryX, boundaryY, width, height, iterations;
	private double alpha, beta, q, decayRate, initialPheromone;
	private int noOfAgents, noOfCities;
	private boolean finished, loaded;
	private Worker worker;

	//default constructor this will load first - when the user hasn't specified any values yet
	public AntColonyOptimisation() {
		//alpha = 0.5d;
		//beta = 2.0d;
		width = 40;
		height = 30;
		//these boundaries are used when deciding a cities random location.
		//the canvas is 40x30 in dimension, however the boundaries are 1 less than this to stop cities being half rendered out of view
		boundaryX = 39;
		boundaryY = 29;
		//q in constant, often 1, so use one for now
		q = 1.0d;
		//decayRate = 0.2d;
		//initialPheromone = 0.8d;
		//noOfAgents = 1;
		//noOfCities = 10;
		//iterations = 1;
		loaded = false;
		finished = false;

	}

	//algorithm with user defined values
	public void setValues(double alpha, double beta, double decayRate, double initalPhero, int agents, int cities, int iterations) {
		this.alpha = alpha;
		this.beta = beta;
		this.decayRate = decayRate;
		this.initialPheromone = initalPhero;
		this.noOfAgents = agents;
		this.noOfCities = cities;
		this.iterations = iterations;
		finished = false;

	}

	public void reset(){

		this.setChanged();
		this.notifyObservers(this);
		this.clearChanged();

	}

	public void stop(){

		this.setChanged();
		this.notifyObservers(this);
		this.clearChanged();

	}



	public void start() {
		//TODO: make sure this is only possible if not already running
		this.finished = false;
		//if the world isn't loaded from a file
		if(loaded){
			//reset the value if it is loaded so the next instance works fine
			loaded = false;
			if(world == null){
				System.out.println("World is null, problem with file reading");
				return;
			}
		}else{
			world = new World(this, noOfAgents, noOfCities);
		}

		worker = new Worker(this, iterations);
		worker.execute();
	}


	public double calculatePheromones(double current, double newPheromone) {

		return ((1 - decayRate) * current + newPheromone);
	}

	public double getAlpha() {
		return alpha;
	}

	public double getBeta() {
		return beta;
	}

	public double getQ() {
		return q;
	}

	public double getInitialPheromone() {
		return initialPheromone;
	}

	public double getDecayRate() {
		return decayRate;
	}

	public int getNoOfAgents() {
		return noOfAgents;
	}

	public World getWorld() {
		return world;
	}

	public boolean getFinished(){
		return finished;
	}


	public void notifyCanvas() {
		setChanged();
		notifyObservers(this);
		clearChanged();
		try{
			Thread.sleep(100);
		}catch(Exception e){

		}
	}

	public int getBoundaryX() {
		return boundaryX;
	}

	public int getBoundaryY() {
		return boundaryY;
	}

	public int getWidth() {
		return width;
	}

	public int getHieght() {
		return height;
	}

	public void setFinished(boolean b) {
		this.finished = b;

	}

	public void load(){
		this.world = loadWorldFromFile("testfile.tsp");
		notifyCanvas();

	}

	public void setLoaded(boolean b) {
		this.loaded = b;

	}

	public World loadWorldFromFile(String fileName) {
		this.loaded = true;
		double alpha, beta, decayRate, initPhero;
		int agents, cities, iterations, x , y;
		ArrayList<City> tempCities = new ArrayList<City>();
		String[] coords = new String[2];
		try {  
			File config  = new File (new File(fileName).getAbsolutePath());
			Scanner s = new Scanner(config);

			alpha = Double.parseDouble(s.nextLine());
			beta = Double.parseDouble(s.nextLine());
			decayRate = Double.parseDouble(s.nextLine());
			initPhero = Double.parseDouble(s.nextLine());
			agents = Integer.parseInt(s.nextLine());
			if(s.nextLine().contains("cities")){
				cities = Integer.parseInt(s.nextLine());
				for(int i = 0; i < cities; i++){
					coords = s.nextLine().split(" ");
					x = Integer.parseInt(coords[0]);
					y = Integer.parseInt(coords[1]);
					if(x > boundaryX || x == 0){
						System.out.println("LOADING ERROR X IS TOO LARGE OR IS 0. X == " + x);
						s.close();
						return null;
					}
					if(y > boundaryY || y == 0){
						System.out.println("LOADING ERROR Y IS TOO LARGE OR IS 0. Y == " + y);
						s.close();
						return null;
					}
					tempCities.add(new City(x, y, i));	
				}
			}
			iterations = Integer.parseInt(s.nextLine());
			//System.out.println("alpha: " + alpha + " beta: " + beta + " decay: " + decayRate + " initPhero: " + initPhero);
			//System.out.println("agents: " + agents + " number cities: " + tempCities.size() + " iterations: " + iterations);

			//loading is only complete if it gets to here and the above checks pass
			if(s.nextLine().contains("EOF")){
				s.close();
				this.setValues(alpha, beta, decayRate, initPhero, agents, tempCities.size(), iterations);
				return new World(this, agents, tempCities.size(), tempCities);
			}
			s.close();
		} 
		//catch the exception
		catch(FileNotFoundException e) {
			e.printStackTrace(); 
			return null;
		}catch(InputMismatchException e){
			e.printStackTrace();
			return null;
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return null;

	}

	public int getIterations() {
		return iterations;
	}

	public boolean validate(double alpha, double beta, double decayRate, double initialPhero, int agents, int cities, int iterations) {
		if(alpha > 5.0d){
			System.out.println("ALPHA IS TOO HIGH");
			return false;
		}

		if(beta > 5.0d){
			System.out.println("BETA IS TOO HIGH");
			return false;
		}

		if(decayRate > 1.0d || decayRate < 0.0d){
			System.out.println("DECAY RATE MUST BE BETWEEN 0 AND 1");
			return false;
		}

		if(initialPhero > 1.0d || initialPhero < 0.0d){
			System.out.println("DECAY RATE MUST BE BETWEEN 0 AND 1");
			return false;
		}

		if(agents > 100 || initialPhero < 0){
			System.out.println("Agents must be between 0 AND 100");
			return false;
		}

		if(cities > 30 || cities < 3){
			System.out.println("cities must be between 3 and 30");
			return false;
		}

		if(iterations < 1){
			System.out.println("You must have at least 1 iteration");
			return false;
		}

		return true;
	}

	public boolean getLoaded(){
		return loaded;
	}

}
