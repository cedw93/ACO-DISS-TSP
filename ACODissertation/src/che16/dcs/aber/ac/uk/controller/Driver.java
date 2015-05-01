package che16.dcs.aber.ac.uk.controller;

import javax.swing.SwingUtilities;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.view.DisplayFrame;

/**
 * 
 * The Driver Class severs as the application entry point.
 * 
 * @author Christopher Edwards
 *
 */
public class Driver {

	/**
	 * This method instantiates the necessary application elements. This method also 
	 * performs the associations needed in order for the application to instantiate correctly.
	 * 
	 * The user interface will be instantiated on the correct thread using the
	 * {@link javax.swing.SwingUtilities#invokeLater(Runnable)} method to ensure correct protocols
	 * obeyed.
	 * 
	 * @param args command line arguments, current unused
	 */
	
	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AntColonyOptimisation model = new AntColonyOptimisation();
				MenuListener menuListener = new MenuListener(model);
				DisplayFrame view = new DisplayFrame(model, menuListener);
				view.getCanvasContainer().setDisplayFrame(view);
				menuListener.setView(view);
				new Control(model, view);
			}
		});

	}

}
