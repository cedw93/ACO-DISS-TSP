package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.SwingWorker;

public class AntColonyOptimisation extends Observable{

	private World world;
	private int boundaryX, boundaryY, width, height, iterations;
	private double alpha, beta, q, decayRate, initialPheromone;
	private int noOfAgents, noOfCities;
	private boolean finished;
	private Worker worker;

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

}
