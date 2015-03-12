package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		}
		else if(source.equalsIgnoreCase("reset values")){
			model.reset();
		}

		else if(source.equalsIgnoreCase("stop")){
			model.stop();
		}
	}

}
