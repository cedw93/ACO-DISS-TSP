package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;

import javax.swing.SwingWorker;


public class Worker extends SwingWorker<Void, Void>{
	private AntColonyOptimisation aco;
	private int antsWorking, iterations;
	private boolean reset;

	public Worker(AntColonyOptimisation aco, int interations){
		this.aco = aco;
		this.iterations = interations;
	}

	@Override
	protected Void doInBackground() throws Exception {

		for(int i = 0; i < iterations; i++){
			aco.setCurrentIteration(i);
			if(aco.getRunning()){
				ArrayList<Ant> ants = (ArrayList<Ant>)aco.getWorld().getAnts();
				antsWorking = aco.getNoOfAgents();
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
						if(!aco.getRunning()){
							return null;
						}
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
