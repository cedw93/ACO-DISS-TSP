package che16.dcs.aber.ac.uk.controller;

import javax.swing.SwingUtilities;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.view.DisplayFrame;

public class Driver {

	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AntColonyOptimisation model = new AntColonyOptimisation();
				MenuListener menuListener = new MenuListener(model);
				DisplayFrame view = new DisplayFrame(model, menuListener);
				menuListener.setView(view);
				new Control(model, view);
			}
		});

	}

}
