package che16.dcs.aber.ac.uk.model;

import java.io.File;
import java.io.FileNotFoundException;
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
		alpha = 0.5d;
		beta = 2.0d;
		width = 40;
		height = 30;
		//these boundaries are used when deciding a cities random location.
		//the canvas is 40x30 in dimension, however the boundaries are 1 less than this to stop cities being half rendered out of view
		boundaryX = 39;
		boundaryY = 29;
		//q in constant, often 1, so use one for now
		q = 1.0d;
		decayRate = 0.2d;
		initialPheromone = 0.8d;
		noOfAgents = 1;
		noOfCities = 10;
		iterations = 1;
		//world = new World(this, noOfAgents, noOfCities);
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
		world = new World(this, noOfAgents, noOfCities);
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
		if(!loaded){
			world = new World(this, noOfAgents, noOfCities);
			setValues(0.2, 2.0, 0.2, 0.8, 100, 15, 1); 
		}
		else{
			//reset the value if it is loaded so the next instance works fine
			loaded = false;
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
			Thread.sleep(10);
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
		String line;
		double alpha, beta, decayRate, initPhero;
		int agents, cities, interations;
		try {  
			File config  = new File (new File("testfile.tsp").getAbsolutePath());
			Scanner s = new Scanner(config);
			
			alpha = Double.parseDouble(s.nextLine());
			beta = Double.parseDouble(s.nextLine());
			decayRate = Double.parseDouble(s.nextLine());
			initPhero = Double.parseDouble(s.nextLine());
			
			System.out.println("alpha: " + alpha + " beta: " + beta + " decay: " + decayRate + " initPhero: " + initPhero);

		} 
		//catch the exception
		catch(FileNotFoundException e) {
			e.printStackTrace(); 
			return;
		}catch(InputMismatchException e){
			e.printStackTrace();
			return;
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		//update the view;
		this.loaded = true;
	}

	public void setLoaded(boolean b) {
		this.loaded = b;

	}

}
