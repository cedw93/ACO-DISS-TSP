package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

import javax.swing.SwingWorker;

public class AntColonyOptimisation extends Observable{

	private World world;
	private double alpha, beta, q, decayRate, initialPheromone, bestDistance;
	private int noOfAgents;
	private boolean finished;
	private LinkedList<Integer> bestRoute;

	public AntColonyOptimisation() {
		alpha =0.2d;
		beta = 2.0d;
		//q in constant, often 1, so use one for now
		q = 1.0d;
		decayRate = 0.2d;
		initialPheromone = 0.8d;
		noOfAgents = 20;
		bestDistance = -1;
		bestRoute = new LinkedList<Integer>();
		world = new World(this, noOfAgents);
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

	public boolean getFinished(){
		return finished;
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
			int i = 0;
			boolean needReset = false;
			while(i < 1){
				//if one iteration is done, then reset the ants
				if(needReset){
					System.out.println("RESTTING!!!!! 10 secs to go!");
					Thread.sleep(10000);
					world.resetAnts();
					System.out.println("OFF WE GO!");
					needReset = false;
				}
				antsWorking = aco.getNoOfAgents();
				while(antsWorking > 0){
					for(Ant ant: ants){
						if(!ant.getFinished()){
							ant.move();			
						}else{
							//if an ant is finished, decrease the counter and check if this ant is best
							//System.out.println("Ant died!");
							antsWorking--;
							if(aco.getBestDistance() == -1.0d || ant.getTotalDistance() < aco.bestDistance){
								aco.setBestDistance(ant.getTotalDistance());
								aco.setBestRoute(ant.getRoute());
							}
						}
						world.decayPhero();
					}
					setChanged();
					notifyObservers(aco);
					clearChanged();
					Thread.sleep(500);
					if(antsWorking == 0){
						System.out.println("Best distance: " + aco.bestDistance);
						System.out.println("Best route: " + aco.bestRoute);

						needReset = true;
					}
				}
				i++;
			}
			aco.finished = true;
			//world.printPheroMatrix();
			aco.notifyCanvas();
			return null;
		}

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

	public void setBestRoute(LinkedList<Integer> route) {
		this.bestRoute = route;

	}

	public void setBestDistance(double totalDistance) {
		this.bestDistance = totalDistance;

	}

	public LinkedList<Integer> getBestRoute() {
		return bestRoute;
	}

}
