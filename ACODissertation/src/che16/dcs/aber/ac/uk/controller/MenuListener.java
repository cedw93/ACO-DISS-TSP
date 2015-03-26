package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
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
			if(model.getWorld() == null){
				JOptionPane.showMessageDialog(null, "There is nothing to save. Try loading a file or creating your own world!",
						"Nothing to save!",	JOptionPane.WARNING_MESSAGE);
				return;
			}
			//don't save if the algorithm is running
			if(!model.getRunning()){
				String fileName = chooseSaveFile();
				if(fileName != ""){
					model.save(fileName);
				}else{
					JOptionPane.showMessageDialog(null, "You did not select a file, please try again.",
							"No file selected",	JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Algorithm is running, you cannot save untill this is stopped or finishes naturally.",
						"Saving error",	JOptionPane.ERROR_MESSAGE);
			}
		}

		else if(source.equalsIgnoreCase("load")){
			if(!model.getRunning()){
				String fileName = chooseLoadFile();
				if(fileName != ""){
					model.load(fileName);

					//set the text field values to the same as what was loaded in
					view.getControlContainer().getControlPanel().getIterationField().setText(Integer.toString(model.getIterations()));
					view.getControlContainer().getControlPanel().getAgentField().setText(Integer.toString(model.getNoOfAgents()));
					view.getControlContainer().getControlPanel().getAlphaField().setText(Double.toString(model.getAlpha()));
					view.getControlContainer().getControlPanel().getBetaField().setText(Double.toString(model.getIterations()));
					view.getControlContainer().getControlPanel().getGoalNodesField().setText(Integer.toString(model.getWorld().getCities().size()));
					view.getControlContainer().getControlPanel().getInitPheroField().setText(Double.toString(model.getInitialPheromone()));
					view.getControlContainer().getControlPanel().getDecayField().setText(Double.toString(model.getDecayRate()));
					view.getControlContainer().getControlPanel().getUphillField().setText(Integer.toString(model.getUphillPaths()));
				}else{
					JOptionPane.showMessageDialog(null, "You did not select a file, please try again.",
							"No file selected",	JOptionPane.ERROR_MESSAGE);
				}
			}else{
				JOptionPane.showMessageDialog(null, "You cannot load a problem whilst the algorithm is running. Stop the current algorithm or let it finish, then try again.",
						"Algorithm is running",	JOptionPane.ERROR_MESSAGE);
			}

		}	

		else if(source.equalsIgnoreCase("Slowest - 1000ms")){
			model.setSpeed(1000L);
		}
		else if(source.equalsIgnoreCase("Medium - 500ms")){
			model.setSpeed(500L);
		}
		else if(source.equalsIgnoreCase("Fast - 100ms")){
			model.setSpeed(100L);
		}
		else if(source.equalsIgnoreCase("Fastest - 10ms")){
			model.setSpeed(10L);
		}

		else if(source.equalsIgnoreCase("city detail")){
			view.getCityDetailView().setVisible(true);

		}

		else if(source.equalsIgnoreCase("equations")){
			view.getEquationFrame().setVisible(true);

		}

		else if(source.equalsIgnoreCase("uphill routes")){
			view.getUphillFrame().setVisible(true);

		}

		else if(source.equalsIgnoreCase("Disable Uphill Routes")){
			model.uphillActive(false);
			view.getControlContainer().getControlPanel().getUphillField().setEnabled(false);

		}

		else if(source.equalsIgnoreCase("enable Uphill Routes")){
			model.uphillActive(true);
			view.getControlContainer().getControlPanel().getUphillField().setEnabled(true);

		}

		else if(source.equalsIgnoreCase("basic system")){
			model.setMethod(0);
		}

		else if(source.equalsIgnoreCase("elitist ant system")){
			model.setMethod(1);
		}


	}

	public String chooseLoadFile(){
		/*
		 * Locking method adapted from: http://stackoverflow.com/a/8926260/4142444 user 'dogbane'
		 */
		String result = "";
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TSP and TXT file", "tsp", "txt");
		final File lock = new File(System.getProperty("user.home"));
		JFileChooser chooser = new JFileChooser(lock);
		chooser.setFileFilter(filter);
		chooser.setFileView(new FileView() {
			@Override
			public Boolean isTraversable(File file) {
				return lock.equals(file);
			}
		});
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			result =  chooser.getSelectedFile().getName();
		}

		return result;
	}

	public String chooseSaveFile(){
		/*
		 * Locking method adapted from: http://stackoverflow.com/a/8926260/4142444 user 'dogbane'
		 */
		String result = "";
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TSP and TXT file", "tsp", "txt");
		final File lock = new File(System.getProperty("user.home"));
		JFileChooser chooser = new JFileChooser(lock);
		chooser.setFileFilter(filter);
		chooser.setFileView(new FileView() {
			@Override
			public Boolean isTraversable(File file) {
				return lock.equals(file);
			}
		});
		int returnVal = chooser.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			result =  chooser.getSelectedFile().getName();
		}

		return result;
	}

}

