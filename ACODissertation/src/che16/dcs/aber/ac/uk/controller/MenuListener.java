package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;

public class  MenuListener implements ActionListener {


	private AntColonyOptimisation model;

	public MenuListener(AntColonyOptimisation model){
		this.model = model;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if(source.equalsIgnoreCase("save")){
			System.out.println("SAVE");
		}

		else if(source.equalsIgnoreCase("load")){
			System.out.println("LOAD");

		}

	}

}

