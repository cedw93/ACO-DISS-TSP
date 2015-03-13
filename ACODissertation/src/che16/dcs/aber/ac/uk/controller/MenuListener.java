package che16.dcs.aber.ac.uk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
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
				System.out.println("Nothing to save!");
				return;
			}
			//don't save if the algorithm is running
			if(!model.getRunning()){
				String fileName = chooseSaveFile();
				if(fileName != ""){
					model.save(fileName);
				}else{
					System.out.println("No file selected!");
				}
			}else{
				System.out.println("Algorithm is running, please wait or stop the execution before saving");
			}
		}

		else if(source.equalsIgnoreCase("load")){
			String fileName = chooseLoadFile();
			if(fileName != ""){
				model.load(fileName);

				//set the text field values to the same as what was loaded in
				view.getControlContainer().getControlPanel().getIterationField().setText(Integer.toString(model.getIterations()));;
				view.getControlContainer().getControlPanel().getAgentField().setText(Integer.toString(model.getNoOfAgents()));;
				view.getControlContainer().getControlPanel().getAlphaField().setText(Double.toString(model.getAlpha()));;
				view.getControlContainer().getControlPanel().getBetaField().setText(Double.toString(model.getIterations()));;
				view.getControlContainer().getControlPanel().getGoalNodesField().setText(Integer.toString(model.getWorld().getCities().size()));;
				view.getControlContainer().getControlPanel().getInitPheroField().setText(Double.toString(model.getInitialPheromone()));;
				view.getControlContainer().getControlPanel().getDecayField().setText(Double.toString(model.getDecayRate()));;
			}else{
				System.out.println("No file selected");
			}
		}

	}

	public String chooseLoadFile(){
		/*
		 * Locking method adapted from: http://stackoverflow.com/a/8926260/4142444 user 'dogbane'
		 */
		String result = "";
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TSP and TXT file", "tsp", "txt");
		JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
		chooser.setFileFilter(filter);
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
		JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			result =  chooser.getSelectedFile().getName();
		}

		return result;
	}

}

