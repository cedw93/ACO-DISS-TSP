package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;

import javax.swing.SwingWorker;


public class Worker extends SwingWorker<Void, Void>{
	private AntColonyOptimisation aco;
	private int antsWorking;

	public Worker(AntColonyOptimisation aco){
		this.aco = aco;
	}
	@Override
	protected Void doInBackground() throws Exception {
		ArrayList<Ant> ants = (ArrayList<Ant>)aco.getWorld().getAnts();
		antsWorking = aco.getNoOfAgents();
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
		aco.notifyCanvas();
		Thread.sleep(500);
		System.out.println("Best distance: " + aco.getWorld().getBestDistance());
		System.out.println("Best route: " + aco.getWorld().getBestRoute());

		aco.setFinished(true);
		System.out.println("aco finished: " + aco.getFinished());
		//world.printPheroMatrix();
		aco.notifyCanvas();
		return null;
	}
}
