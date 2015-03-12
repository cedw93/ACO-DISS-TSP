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
			model.start();
			
		}
		else if(source.equalsIgnoreCase("reset values")){
			model.reset();
			view.getControlContainer().getControlPanel().getIterationField().setText("TESTING 1,2");;
		}
		else if(source.equalsIgnoreCase("stop")){
			model.stop();
		}
	}

}
