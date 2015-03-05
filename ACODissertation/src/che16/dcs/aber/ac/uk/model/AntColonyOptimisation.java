package che16.dcs.aber.ac.uk.model;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import javax.swing.SwingWorker;
import javax.swing.Timer;

public class AntColonyOptimisation extends Observable{

	private World world;
	private double alpha, beta, q, decayRate, initialPheromone, bestDistance;
	private int noOfAgents;
	private Ant bestAnt;

	public AntColonyOptimisation() {
		alpha = 1.0d;
		beta = 2.0d;
		//q in constant, often 1, so use one for now
		q = 1.0d;
		decayRate = 0.2d;
		initialPheromone = 0.8d;
		bestAnt = null;
		noOfAgents = 20;
		bestDistance = Double.POSITIVE_INFINITY;
		world = new World(this, noOfAgents);

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




	public World getWorld() {
		return world;
	}

	public double getBestDistance() {
		return bestDistance;
	}

	public Ant getBestAnt(){
		return bestAnt;
	}

	public void setBestAnt(Ant best){
		this.bestAnt = best;
	}


	private class Worker extends SwingWorker<Void, Void>{
		private AntColonyOptimisation aco;
		private int antsWorking;

		public Worker(AntColonyOptimisation aco){
			this.aco = aco;
		}
		@Override
		protected Void doInBackground() throws Exception {

			ArrayList<Ant> ants = (ArrayList<Ant>)world.getAnts();	
			antsWorking = aco.getNoOfAgents();
			while(antsWorking > 0){
				for(Ant ant: ants){
					if(!ant.getFinished()){
						ant.move();

						if(aco.getBestAnt() == null || ant.getTotalDistance() < aco.getBestAnt().getTotalDistance()){
							//	System.out.println("aco.getBestAnt().getTotalDistance() == " + aco.getBestAnt().getTotalDistance());
							//System.out.println("ant.getTotalDistance() == " + ant.getTotalDistance());
							aco.setBestAnt(ant);
						}
					}else{
						antsWorking--;
					}
				}
				setChanged();
				notifyObservers(aco);
				clearChanged();
				Thread.sleep(100);
			}
			
			System.out.println("Best distance: " + aco.bestAnt.getTotalDistance());
			System.out.println(aco.bestAnt.getRoute());
			return null;
		}

	}

}
