package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;

import javax.swing.SwingWorker;

/**
 * This Class is used to execute the algorithm in a concurrent manner and is an extension
 * of the {@link javax.swing.SwingWorker<T,V> SwingWorker} Class.  
 * @author Christopher Edwards
 *
 */

public class Worker extends SwingWorker<Void, Void>{
	private AntColonyOptimisation aco;
	private int antsWorking, iterations;
	private boolean reset;

	/**
	 * Constructor with defined parameters.
	 * @param aco the current {@link AntColonyOptimisation} instance
	 * @param interations the total number of iterations
	 */
	public Worker(AntColonyOptimisation aco, int interations){
		this.aco = aco;
		this.iterations = interations;
	}

	@Override
	protected Void doInBackground() throws Exception {
		/*
		 * Execute the algorithm in this method to prevent the UI from freezing up.
		 */
		for(int i = 0; i < iterations; i++){
			aco.setCurrentIteration(i);
			if(aco.getRunning()){
				ArrayList<Ant> ants = (ArrayList<Ant>)aco.getWorld().getAnts();
				antsWorking = aco.getNoOfAgents();
				//if the previous iteration is complete, reset the ants 
				if(reset){
					//for the next iteration re-init the ants and go again
					for(City c: aco.getWorld().getCities()){
						c.resetAntCount();
					}
					aco.getWorld().initAnts();
					ants = (ArrayList<Ant>)aco.getWorld().getAnts();
					reset = false;

				}
				while(antsWorking > 0){
					for(Ant ant: ants){
						//check to see if the user has stopped the execution
						if(!aco.getRunning()){
							return null;
						}
						//if the ant isnt finished, move it
						if(!ant.getFinished()){
							ant.move();	
							aco.reduceWorking();
						}else{
							//if an ant is finished, decrease the counter
							antsWorking--;
						}
					}

					aco.getWorld().decayPhero();
				}
				reset = true;
			}
		}
		aco.notifyCanvas();
		Thread.sleep(500);
		aco.setFinished(true);
		for(City c: aco.getWorld().getCities()){
			c.resetAntCount();
		}
		//once we have finished, set the fact that the next world wont be from a file unless reloaded
		aco.setLoaded(false);
		aco.setRunning(false);
		aco.notifyCanvas();
		return null;
	}

}
