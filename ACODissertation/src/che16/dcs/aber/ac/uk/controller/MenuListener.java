package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.utils.Globals;
import che16.dcs.aber.ac.uk.view.DisplayFrame;

/**
 * This Class is a dedicated ActionListener for {@link javax.swing.JMenuBar} contained in the
 * {@link che16.dcs.aber.ac.uk.view.DisplayFrame} instance.
 * @author Christopher Edwards
 *
 */

public class  MenuListener implements ActionListener {


	private AntColonyOptimisation model;
	private DisplayFrame view;

	/**
	 * Constructor for the MenuListener
	 * 
	 * @param model the current instance of the {@link che16.dcs.aber.ac.uk.model.AntColonyOptimisation} Class
	 */
	public MenuListener(AntColonyOptimisation model){
		this.model = model;

	}

	/**
	 * Sets the current instance of the 
	 * @param view the current instance of the {@link che16.dcs.aber.ac.uk.view.DisplayFrame} Class
	 */
	
	public void setView(DisplayFrame view){
		this.view = view;
	}

	/**
	 * Inherited method as a result of the implementing {@link java.awt.event.ActionListener} interface.
	 * 
	 * This method converts the source of the interaction into a String which is then compared
	 * to the element identifiers present in the user interface, if there is a match then the
	 * correct action is performed. 
	 * 
	 * Any additional JMenuBar items must be also be implemented in this method.
	 * 
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String source = e.getActionCommand();
		if(source.equalsIgnoreCase("save")){
			if(model.getWorld() == null){
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "There is nothing to save. Try loading a file or creating your own world!",
							"Nothing to save!",	JOptionPane.WARNING_MESSAGE);
				}
				return;
			}
			//don't save if the algorithm is running
			if(!model.getRunning()){
				String fileName = chooseSaveFile();
				if(fileName != ""){
					model.save(fileName);
				}else{
					if(Globals.getMode() == 0){
						JOptionPane.showMessageDialog(null, "You did not select a file, please try again.",
								"No file selected",	JOptionPane.ERROR_MESSAGE);
					}
				}
			}else{
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "Algorithm is running, you cannot save untill this is stopped or finishes naturally.",
							"Saving error",	JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		else if(source.equalsIgnoreCase("load")){
			if(!model.getRunning()){
				String fileName = chooseLoadFile();
				if(fileName != ""){
					model.load(fileName);
					if(model.getWorld() == null){
						//load failed
						return;
					}

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
					if(Globals.getMode() == 0){
						JOptionPane.showMessageDialog(null, "You did not select a file, please try again.",
								"No file selected",	JOptionPane.ERROR_MESSAGE);
					}
				}
			}else{
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "You cannot load a problem whilst the algorithm is running. Stop the current algorithm or let it finish, then try again.",
							"Algorithm is running",	JOptionPane.ERROR_MESSAGE);
				}
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
			if(!(model.getRunning())){
				model.setMethod(0);
			}else{
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "You cannot change the method whilst the algorithm is running. Stop the current algorithm or let it finish, then try again.",
							"Algorithm is running",	JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		else if(source.equalsIgnoreCase("elitist ant system")){
			if(!(model.getRunning())){
				String result = JOptionPane.showInputDialog(null, "How many Elite ants do you want?");

				try{
					int number = Integer.parseInt(result);
					if(number > Integer.parseInt(view.getControlContainer().getControlPanel().getAgentField().getText())){
						JOptionPane.showMessageDialog(null, "You cannot have more elite agents than there are agents",
								"Too many elite agents",	JOptionPane.ERROR_MESSAGE);
					}

					if(number < 0){
						JOptionPane.showMessageDialog(null, "You cannot less than 0 elite agents",
								"Too few elite agents",	JOptionPane.ERROR_MESSAGE);
						return;
					}
					model.setMethod(1);
					model.setEliteAnts(number);

				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(null, "You must enter a whole number for the number of Elite Ants",
							"Number format error",	JOptionPane.ERROR_MESSAGE);
				}
			}else{
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "You cannot change the method whilst the algorithm is running. Stop the current algorithm or let it finish, then try again.",
							"Algorithm is running",	JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		else if(source.equalsIgnoreCase("enable step mode")){
			if(model.getRunning()){
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "You cannot enable step mode whilst the algorithm is running. Stop the current algorithm or let it finish, then try again.",
							"Algorithm is running",	JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			view.getControlContainer().getControlPanel().getStartButton().setText("Step");
		}

		else if(source.equalsIgnoreCase("disable step mode")){
			if(model.getRunning()){
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "You cannot disable step mode whilst the algorithm is running. Stop the current algorithm or let it finish, then try again.",
							"Algorithm is running",	JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			view.getControlContainer().getControlPanel().getStartButton().setText("Start");
			ControlPanelListener.unlockUI();

		}

	}
	
	/**
	 * This method opens a {@link javax.swing.JFileChooser} which enables the destination file
	 * where a configuration can be loaded from. This {@link javax.swing.JFileChooser} is limited
	 * to the users home directory and .tsp or .txt file formats.
	 * 
	 * This method also checks to ensure that the a correct file is returned and correctly
	 * handles incorrect interactions with suitable error prompts.
	 * 
	 * @return String the file name of the selected input file
	 */

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

	/**
	 * This method opens a {@link javax.swing.JFileChooser} which enables the destination file
	 * where a configuration can be saved. This {@link javax.swing.JFileChooser} is limited
	 * to the users home directory and .tsp or .txt file formats.
	 * 
	 * This method also checks to ensure that the a correct file is returned and correctly
	 * handles incorrect interactions with suitable error prompts.
	 * 
	 * @return String the file name of the selected output file
	 */
	
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

