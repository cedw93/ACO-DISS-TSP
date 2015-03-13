package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.view.DisplayFrame;

public class ControlPanelListener implements ActionListener {


	private AntColonyOptimisation model;
	private DisplayFrame view;

	public ControlPanelListener(AntColonyOptimisation model, DisplayFrame view){
		this.model = model;
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if(source.equalsIgnoreCase("start")){
			//nice a messy method. Get the values from the test fields, we need to check if they are valid!
			//TODO: CHECK THE CONTENTS OF THIS FOR VALIDITY
			try{
				double alpha = Double.parseDouble(view.getControlContainer().getControlPanel().getAlphaField().getText());
				double decayRate= Double.parseDouble(view.getControlContainer().getControlPanel().getDecayField().getText());
				double beta = Double.parseDouble(view.getControlContainer().getControlPanel().getBetaField().getText());
				double initialPhero = Double.parseDouble(view.getControlContainer().getControlPanel().getInitPheroField().getText());
				int agents = Integer.parseInt(view.getControlContainer().getControlPanel().getAgentField().getText());
				int cities = Integer.parseInt(view.getControlContainer().getControlPanel().getGoalNodesField().getText());
				int iterations = Integer.parseInt(view.getControlContainer().getControlPanel().getIterationField().getText());

				//update the model
				if(model.validate(alpha, beta, decayRate, initialPhero, agents, cities, iterations)){
					model.setValues(alpha, beta, decayRate, initialPhero, agents, cities, iterations);
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
				JOptionPane.showMessageDialog(null, "One or more value(s) you have specified are of incorrect format.\nPlease adhere to the following formats:\niterations: whole number\nalpha: decimal\nbeta: decimal"
						+ "\ndecay rate: decimal\nnumber of agents: whole number\nnumber of cities: whole number\ninitial pheromone: decimal",
						"Unparsable Values",	JOptionPane.ERROR_MESSAGE);
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
		}

		else if(source.equalsIgnoreCase("stop")){
			model.stop();
		}
	}

}
