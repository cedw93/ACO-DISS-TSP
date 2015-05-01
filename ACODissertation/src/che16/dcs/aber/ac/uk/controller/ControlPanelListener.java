package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.utils.Globals;
import che16.dcs.aber.ac.uk.view.DisplayFrame;

/**
 * This Class is a dedicated ActionListener for the {@link che16.dcs.aber.ac.uk.view.ControlPanel} instance.
 *  
 * @author Christopher Edwards
 *
 */
public class ControlPanelListener implements ActionListener {

	private AntColonyOptimisation model;
	private static DisplayFrame view;
	private static boolean lockedUI;

	/**
	 * The ControlPanelListener constructor.
	 * 
	 * @param model the current instance of the {@link che16.dcs.aber.ac.uk.model.AntColonyOptimisation} Class
	 * @param view the current instance of the {@link che16.dcs.aber.ac.uk.view.DisplayFrame} Class
	 */
		
	public ControlPanelListener(AntColonyOptimisation model, DisplayFrame view){
		this.model = model;
		this.view = view;
		lockedUI = false;
	}

	/**
	 * Inherited method as a result of the implementing {@link java.awt.event.ActionListener} interface.
	 * 
	 * This method converts the source of the interaction into a String which is then compared
	 * to the element identifiers present in the user interface, if there is a match then the
	 * correct action is performed. 
	 * 
	 * Any additional ControlPanel elements that need ActionLisetner functionality 
	 * must be also be implemented in this method.
	 * 
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if(source.equalsIgnoreCase("start")){
			//nice a messy method. Get the values from the test fields, we need to check if they are valid!
			try{
				double alpha = Double.parseDouble(view.getControlContainer().getControlPanel().getAlphaField().getText());
				double decayRate= Double.parseDouble(view.getControlContainer().getControlPanel().getDecayField().getText());
				double beta = Double.parseDouble(view.getControlContainer().getControlPanel().getBetaField().getText());
				double initialPhero = Double.parseDouble(view.getControlContainer().getControlPanel().getInitPheroField().getText());
				int agents = Integer.parseInt(view.getControlContainer().getControlPanel().getAgentField().getText());
				int cities = Integer.parseInt(view.getControlContainer().getControlPanel().getGoalNodesField().getText());
				int iterations = Integer.parseInt(view.getControlContainer().getControlPanel().getIterationField().getText());
				int uphillPaths = Integer.parseInt(view.getControlContainer().getControlPanel().getUphillField().getText());
				//update the model
				if(model.validate(alpha, beta, decayRate, initialPhero, agents, cities, iterations, uphillPaths)){
					model.setValues(alpha, beta, decayRate, initialPhero, agents, cities, iterations, uphillPaths);
					//check to see the loaded file has had its number of cities or ants modified
					if(model.getLoaded()){

						if(agents != model.getWorld().getAnts().size()){
							model.getWorld().updateAnts(agents);
						}

						if(cities > model.getWorld().getCities().size()){
							model.getWorld().addToCities((cities - model.getWorld().getCities().size()));
						}else if((cities < model.getWorld().getCities().size())){
							model.getWorld().removeCities((model.getWorld().getCities().size() - cities));
						}
					}
					model.start();
				}
			}catch(NumberFormatException ex){
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "One or more value(s) you have specified are of incorrect format.\nPlease adhere to the following formats:\nuphill paths: whole number\niterations: whole number\nalpha: decimal\nbeta: decimal"
							+ "\ndecay rate: decimal\nnumber of agents: whole number\nnumber of cities: whole number\ninitial pheromone: decimal",
							"Unparsable Values",	JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		else if(source.equalsIgnoreCase("step")){

			//nice a messy method. Get the values from the test fields, we need to check if they are valid!
			//TODO: CHECK THE CONTENTS OF THIS FOR VALIDITY
			if(!lockedUI){
				try{
					double alpha = Double.parseDouble(view.getControlContainer().getControlPanel().getAlphaField().getText());
					double decayRate= Double.parseDouble(view.getControlContainer().getControlPanel().getDecayField().getText());
					double beta = Double.parseDouble(view.getControlContainer().getControlPanel().getBetaField().getText());
					double initialPhero = Double.parseDouble(view.getControlContainer().getControlPanel().getInitPheroField().getText());
					int agents = Integer.parseInt(view.getControlContainer().getControlPanel().getAgentField().getText());
					int cities = Integer.parseInt(view.getControlContainer().getControlPanel().getGoalNodesField().getText());
					int iterations = Integer.parseInt(view.getControlContainer().getControlPanel().getIterationField().getText());
					int uphillPaths = Integer.parseInt(view.getControlContainer().getControlPanel().getUphillField().getText());
					//update the model
					if(model.validate(alpha, beta, decayRate, initialPhero, agents, cities, iterations, uphillPaths)){
						model.setValues(alpha, beta, decayRate, initialPhero, agents, cities, iterations, uphillPaths);
						//check to see the loaded file has had its number of cities or ants modified
						if(model.getLoaded()){

							if(agents != model.getWorld().getAnts().size()){
								model.getWorld().updateAnts(agents);
							}

							if(cities > model.getWorld().getCities().size()){
								model.getWorld().addToCities((cities - model.getWorld().getCities().size()));
							}else if((cities < model.getWorld().getCities().size())){
								model.getWorld().removeCities((model.getWorld().getCities().size() - cities));
							}
						}
						model.step();
						//lock the UI to prevent world modification mid step
						lockUI();
					}

				}catch(NumberFormatException ex){
					if(Globals.getMode() == 0){
						JOptionPane.showMessageDialog(null, "One or more value(s) you have specified are of incorrect format.\nPlease adhere to the following formats:\nuphill paths: whole number\niterations: whole number\nalpha: decimal\nbeta: decimal"
								+ "\ndecay rate: decimal\nnumber of agents: whole number\nnumber of cities: whole number\ninitial pheromone: decimal",
								"Unparsable Values",	JOptionPane.ERROR_MESSAGE);
					}
				}
			}else{
				model.step();
			}
		}

		else if(source.equalsIgnoreCase("reset values")){
			view.getControlContainer().getControlPanel().getAlphaField().setText("2.5");
			view.getControlContainer().getControlPanel().getBetaField().setText("2.5");
			view.getControlContainer().getControlPanel().getIterationField().setText("1");
			view.getControlContainer().getControlPanel().getAgentField().setText("15");
			view.getControlContainer().getControlPanel().getGoalNodesField().setText("10");
			view.getControlContainer().getControlPanel().getInitPheroField().setText("0.5");
			view.getControlContainer().getControlPanel().getDecayField().setText("0.2");
			view.getControlContainer().getControlPanel().getUphillField().setText("5");
		}

		else if(source.equalsIgnoreCase("stop")){
			model.stop();
			unlockUI();
		}
	}

	/**
	 * This method is used to lock the current user interface elements such that they can no
	 * longer be modified. This should only be used when the user in is step-based iteration.
	 */
	
	public void lockUI(){
		//iterate through each element and disable it
		view.getControlContainer().getControlPanel().getAlphaField().setEnabled(false);
		view.getControlContainer().getControlPanel().getBetaField().setEnabled(false);
		view.getControlContainer().getControlPanel().getIterationField().setEnabled(false);
		view.getControlContainer().getControlPanel().getAgentField().setEnabled(false);
		view.getControlContainer().getControlPanel().getGoalNodesField().setEnabled(false);
		view.getControlContainer().getControlPanel().getInitPheroField().setEnabled(false);
		view.getControlContainer().getControlPanel().getDecayField().setEnabled(false);
		view.getControlContainer().getControlPanel().getUphillField().setEnabled(false);
		lockedUI = true;
	}

	/**
	 * This method is used to unlock the current user interface. Once the interface has been
	 * locked using the {@link #lockUI()} method. This method ensures that every user interface
	 * element is in fact in and editable state.
	 */
	
	public static void unlockUI(){
		view.getControlContainer().getControlPanel().getAlphaField().setEnabled(true);
		view.getControlContainer().getControlPanel().getBetaField().setEnabled(true);
		view.getControlContainer().getControlPanel().getIterationField().setEnabled(true);
		view.getControlContainer().getControlPanel().getAgentField().setEnabled(true);
		view.getControlContainer().getControlPanel().getGoalNodesField().setEnabled(true);
		view.getControlContainer().getControlPanel().getInitPheroField().setEnabled(true);
		view.getControlContainer().getControlPanel().getDecayField().setEnabled(true);
		view.getControlContainer().getControlPanel().getUphillField().setEnabled(true);
		lockedUI = false;
	}

}
