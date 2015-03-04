package che16.dcs.aber.ac.uk.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.SwingWorker;
import javax.swing.Timer;

public class AntColonyOptimisation extends Observable{

	private World world;
	private double alpha, beta, q, decayRate, initialPheromone;
	private int noOfAgents;

	public AntColonyOptimisation() {
		alpha = 1.0d;
		beta = 2.0d;
		//q in constant, often 1, so use one for now
		q = 1.0d;
		decayRate = 0.2d;
		initialPheromone = 0.8d;
		noOfAgents = 20;
		world = new World(this, 40, 30, noOfAgents);

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

		new Worker(this).execute();
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


	private class Worker extends SwingWorker<Void, Void>{
		private AntColonyOptimisation aco;

		public Worker(AntColonyOptimisation aco){
			this.aco = aco;
		}
		@Override
		protected Void doInBackground() throws Exception {

			ArrayList<Ant> ants = (ArrayList)world.getAnts();			
			for(Ant ant: ants){
				ant.move();
				setChanged();
				notifyObservers(aco);
				clearChanged();
				Thread.sleep(10);
			}

			return null;

		}


	}


	public World getWorld() {
		return world;
	}


}
