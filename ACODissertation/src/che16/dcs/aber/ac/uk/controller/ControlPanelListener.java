package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;

public class ControlPanelListener implements ActionListener {


	private AntColonyOptimisation model;

	public ControlPanelListener(AntColonyOptimisation model){
		this.model = model;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if(source.equalsIgnoreCase("start")){
			model.start();
		}
		else if(source.equalsIgnoreCase("reset values")){
			model.reset();
		}
		else if(source.equalsIgnoreCase("stop")){
			model.stop();
		}
	}

}
