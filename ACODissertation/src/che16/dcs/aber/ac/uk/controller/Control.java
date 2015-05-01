package che16.dcs.aber.ac.uk.controller;

import java.util.Observable;
import java.util.Observer;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.view.DisplayFrame;

/**
 * The Control Class represents a logical module which is used to oversee
 * and govern inter-package interactions between the View and Model.
 * 
 * @author Christopher Edwards
 *
 */

public class Control implements Observer{

	private DisplayFrame view;
	private AntColonyOptimisation model;
	private ControlPanelListener panelListener;

	/**
	 * The Control constructor.
	 * 
	 * @param model the current instance of the {@link che16.dcs.aber.ac.uk.model.AntColonyOptimisation} Class
	 * @param view the current instance of the {@link che16.dcs.aber.ac.uk.view.DisplayFrame} Class
	 */
	
	public Control(AntColonyOptimisation model, DisplayFrame view) {

		this.view = view;
		this.model = model;
		//set the observer
		model.addObserver(this);
		view.getCanvasContainer().getCanvas().setModel(model);
		panelListener = new ControlPanelListener(model, view);

		//doing it this way as it is the least 'messy' way to do so
		view.setControlPanelListener(panelListener);

		//lets render the panel, done here to ensure the view is completely initiated
		view.render();

	}

	/**
	 * Listens to updates from the Observable objects.
	 * 
	 * Any additional Objects which implement the Observable interface must be added to this methods
	 * control sequence. This method is only called when the observable objects issue notifications
	 * to their observers.
	 * 
	 * @param observable the Observable Object
	 * @param object the Object which sent the notification request
	 * @see java.util.Observable
	 */

	@Override
	public void update(Observable observable, Object object) {
		/*
		 * Check to see if the source of the update is the current Model or not
		 * if it is then render the canvas.			
		 */
		if(object != null){
			if(object.equals(model)){
				view.getCanvasContainer().getCanvas().render();
			}
		}
	}


}
