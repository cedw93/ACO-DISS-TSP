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
			ArrayList<Ant> ants = (ArrayList<Ant>)aco.getWorld().getAnts();
			antsWorking = aco.getNoOfAgents();
			if(reset){
				//for the next iteration re-init the ants and go again
				aco.getWorld().initAnts();
				ants = (ArrayList<Ant>)aco.getWorld().getAnts();
				reset = false;
			}
			while(antsWorking > 0){
				for(Ant ant: ants){
					if(!ant.getFinished()){
						ant.move();			
					}else{
						//if an ant is finished, decrease the counter
						antsWorking--;
					}
				}

				aco.getWorld().decayPhero();
			}
			reset = true;
		}
		aco.notifyCanvas();
		Thread.sleep(500);
		System.out.println("Best distance: " + aco.getWorld().getBestDistance());
		System.out.println("Best route: " + aco.getWorld().getBestRoute());

		aco.setFinished(true);
		aco.notifyCanvas();
		return null;
	}
}
