package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.view.ControlPanel;
import che16.dcs.aber.ac.uk.view.DisplayFrame;

public class  MenuListener implements ActionListener {


	private AntColonyOptimisation model;
	private DisplayFrame view;

	public MenuListener(AntColonyOptimisation model){
		this.model = model;

	}

	public void setView(DisplayFrame view){
		this.view = view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if(source.equalsIgnoreCase("save")){
			System.out.println("SAVE");
		}

		else if(source.equalsIgnoreCase("load")){
			model.load();
			//set the text field values to the same as what was loaded in
			view.getControlContainer().getControlPanel().getIterationField().setText(Integer.toString(model.getIterations()));;
			view.getControlContainer().getControlPanel().getAgentField().setText(Integer.toString(model.getNoOfAgents()));;
			view.getControlContainer().getControlPanel().getAlphaField().setText(Double.toString(model.getAlpha()));;
			view.getControlContainer().getControlPanel().getBetaField().setText(Double.toString(model.getIterations()));;
			view.getControlContainer().getControlPanel().getGoalNodesField().setText(Integer.toString(model.getWorld().getCities().size()));;
			view.getControlContainer().getControlPanel().getInitPheroField().setText(Double.toString(model.getInitialPheromone()));;
			view.getControlContainer().getControlPanel().getDecayField().setText(Double.toString(model.getDecayRate()));;

		}

	}

}

