package che16.dcs.aber.ac.uk.controller;

import java.util.Observable;
import java.util.Observer;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.view.DisplayFrame;

public class Control implements Observer{

	private DisplayFrame view;
	private AntColonyOptimisation model;
	private ControlPanelListener panelListener;

	public Control(AntColonyOptimisation model, DisplayFrame view) {

		this.view = view;
		this.model = model;

		model.addObserver(this);
		view.getCanvasContainer().getCanvas().setModel(model);
		panelListener = new ControlPanelListener(model, view);

		//doing it this way as it is the least 'messy' way to do so
		view.setControlPanelListener(panelListener);

		//lets render the panel, done here rather than view to ensure the view is completely initiated
		view.render();

	}

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
