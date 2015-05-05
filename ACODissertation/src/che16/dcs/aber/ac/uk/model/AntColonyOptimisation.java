package che16.dcs.aber.ac.uk.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JOptionPane;

import che16.dcs.aber.ac.uk.controller.ControlPanelListener;
import che16.dcs.aber.ac.uk.utils.Globals;

/**
 * This Class is the main data centre for the underlying Ant Colony algorithms
 * present in this application. This Class is also the entry point for the che16.dcs.aber.ac.uk.model 
 * package. This Class contains the relevant parameter values and {@link World} instance required for algorithm
 * execution.
 * 
 * @author Christopher Edwards
 *
 */


public class AntColonyOptimisation extends Observable{

	private World world;
	private int boundaryX, boundaryY, iterations, method, eliteAnts;
	private double alpha, beta, q, decayRate, initialPheromone;
	private int noOfAgents, noOfCities, agentsWorking, currentIter, uphillPaths, stepIndex;
	private boolean finished, loaded, running, uphillActive, stepInit;
	private Worker worker;
	private long speed;

	/**
	 * Default constructor which sets all parameters to defaults.
	 */
	//default constructor this will load first - when the user hasn't specified any values yet
	public AntColonyOptimisation() {

		//these boundaries are used when deciding a cities random location.
		//the canvas is 40x30 in dimension, however the boundaries are 1 less than this to stop cities being half rendered out of view
		boundaryX = 39;
		boundaryY = 29;
		//q in constant, often 1, so use one for now
		q = 1.0d;
		stepIndex = 0;
		loaded = false;
		eliteAnts = 0;
		finished = false;
		running = false;
		currentIter = 0;
		uphillPaths = 0;
		stepInit = false;
		speed = 500L;

	}

	/**
	 * This method is used to set the algorithms parameter values.
	 * 
	 * @param alpha the alpha parameter value
	 * @param beta the beta parameter value
	 * @param decayRate the decaryRate parameter value
	 * @param initalPhero the initial edge pheromone
	 * @param agents the number of {@link Ant ants}
	 * @param cities the number of {@link City cities}
	 * @param iterations the number of iterations
	 * @param uphillPaths the number of uphill paths to be generated
	 */

	//algorithm with user defined values
	public void setValues(double alpha, double beta, double decayRate, double initalPhero, int agents, int cities, int iterations, int uphillPaths) {
		this.alpha = alpha;
		this.beta = beta;
		this.decayRate = decayRate;
		this.initialPheromone = initalPhero;
		this.noOfAgents = agents;
		this.agentsWorking = noOfAgents;
		this.noOfCities = cities;
		this.iterations = iterations;
		this.uphillPaths = uphillPaths;
		finished = false;

	}

	/**
	 * Signals the algorithm to stop its current execution.}
	 */
	public void stop(){

		running = false;
		this.setChanged();
		this.notifyObservers(this);
		this.clearChanged();
		//this shouldnt really be here
		ControlPanelListener.unlockUI();
		stepInit = false;
		stepIndex = 0;

	}

	/**
	 * Signals the algorithm to start its current execution. If the current {@link World} is null, a random
	 * {@link World} will be generated.
	 */
	public void start() {
		//TODO: make sure this is only possible if not already running
		if(running){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Algorithm is running, you cannot generate a new problem untill this is stopped or finishes naturally.",
						"Algorithm is running",	JOptionPane.ERROR_MESSAGE);
			}
			return;
		}
		this.finished = false;
		//if the world isn't loaded from a file
		if(loaded){
			//reset the value if it is loaded so the next instance works fine
			loaded = false;
			if(world == null){
				if(Globals.getMode() == 0){
					JOptionPane.showMessageDialog(null, "There is no world, please load a file or make a new world",
							"No world created",	JOptionPane.ERROR_MESSAGE);
				}
				return;
			}
		}else{
			if(method == 1){
				world = new World(this, noOfAgents, noOfCities, uphillPaths, method);
				if(checkEliteValueIsValid(eliteAnts)){
					world.initEliteAnts(eliteAnts);
				}else{
					return;
				}
			}else{
				world = new World(this, noOfAgents, noOfCities, uphillPaths, method);
			}
		}

		running = true;
		worker = new Worker(this, iterations);
		worker.execute();

	}

	/**
	 * Used to progress the algorithms step based execution by one step. This is only used if the
	 * application is in step-based iteration mode otherwise {@link #start()} is used. 
	 */

	public void step(){
		//TODO: make sure this is only possible if not already running
		if(!stepInit){

			currentIter = 0;
			this.finished = false;

			//if the world isn't loaded from a file
			if(!running){
				if(loaded){
					//reset the value if it is loaded so the next instance works fine
					loaded = false;
					if(world == null){
						if(Globals.getMode() == 0){
							JOptionPane.showMessageDialog(null, "There is no world, please load a file or make a new world",
									"No world created",	JOptionPane.ERROR_MESSAGE);
						}
						return;
					}
				}else{
					if(method == 1){
						world = new World(this, noOfAgents, noOfCities, uphillPaths, method);
						if(checkEliteValueIsValid(eliteAnts)){
							world.initEliteAnts(eliteAnts);
						}else{
							return;
						}
					}else{
						world = new World(this, noOfAgents, noOfCities, uphillPaths, method);
					}
				}
			}
			stepInit = true;
			this.notifyCanvas();
			return;

		}
		//when counter exceedes iterations then stop, no while loop as this is an iterative stepping process
		if(currentIter < iterations){
			running = true;
			Ant ant = world.getAnts().get(stepIndex);
			if(!ant.getFinished()){
				ant.step();
				this.notifyCanvas();
				if(ant.getUnvisted() == 0){
					ant.setFinished(true);
				}

			}else{
				reduceWorking();
				stepIndex++;
			}

			if(stepIndex >= world.getAnts().size()){
				world.initAnts();
				for(City c: world.getCities()){
					c.resetAntCount();
				}
				stepIndex = 0;
				agentsWorking = noOfAgents;
				currentIter++;
			}

		}

		if(currentIter >= iterations){
			currentIter = iterations;
			finished = true;
			running = false;
			stepInit = false;
			this.setChanged();
			this.notifyObservers(this);
			this.clearChanged();
		}
	}

	/**
	 * 
	 * @return the alpha parameter value
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * 
	 * @return the beta parameter value
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * 
	 * @return the Q parameter value
	 */
	public double getQ() {
		return q;
	}

	/**
	 * 
	 * @return the initial edge pheromone
	 */
	public double getInitialPheromone() {
		return initialPheromone;
	}

	/**
	 * 
	 * @return the decayRate parameter value
	 */

	public double getDecayRate() {
		return decayRate;
	}

	/**
	 * 
	 * @return the number of {@link Ant ants}
	 */
	public int getNoOfAgents() {
		return noOfAgents;
	}

	/**
	 * 
	 * @return the current {@link World} instance
	 */

	public World getWorld() {
		return world;
	}

	/**
	 * 
	 * @return the finished status
	 */

	public boolean getFinished(){
		return finished;
	}

	/**
	 * Notifies the Observer that an update to the view is required.
	 */

	public void notifyCanvas() {
		setChanged();
		notifyObservers(this);
		clearChanged();
		try{
			Thread.sleep(speed);
		}catch(Exception e){

		}
	}

	/**
	 * 
	 * @return the maximum x coordinate for a {@link City}
	 */

	public int getBoundaryX() {
		return boundaryX;
	}

	/**
	 * 
	 * @return the maximum y coordinate for a {@link City}
	 */

	public int getBoundaryY() {
		return boundaryY;
	}

	/**
	 * 
	 * @param b the finished status
	 */
	public void setFinished(boolean b) {
		this.finished = b;

	}

	/**
	 * This method is used to initiate a {@link World} with specified values. 
	 * @param fileName the name of the file where the specified values are located
	 */

	public void load(String fileName){
		if(!running){
			this.world = loadWorldFromFile(fileName);
			if(method == 1){
				if(checkEliteValueIsValid(eliteAnts)){
					world.initEliteAnts(eliteAnts);
				}else{
					return;
				}
			}
			notifyCanvas();
		}else{
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Algorithm is running, you cannot load untill this is stopped or finishes naturally.",
						"Loading error",	JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * 
	 * @param b the loaded status
	 */
	public void setLoaded(boolean b) {
		this.loaded = b;

	}

	/**
	 * This method is used to initiate a {@link World} with specified values. 
	 * @param fileName the name of the file where the specified values are located
	 * @return the created {@link World} instance
	 */


	public World loadWorldFromFile(String fileName) {

		double alpha, beta, decayRate, initPhero;
		int agents, cities, iterations, x , y, uphill;
		ArrayList<City> tempCities = new ArrayList<City>();
		String[] coords = new String[2];
		try {  

			FileInputStream stream = new FileInputStream(System.getProperty("user.home") + "/"+fileName);
			Scanner s = new Scanner(stream);
			alpha = Double.parseDouble(s.nextLine());
			beta = Double.parseDouble(s.nextLine());
			decayRate = Double.parseDouble(s.nextLine());
			initPhero = Double.parseDouble(s.nextLine());
			agents = Integer.parseInt(s.nextLine());
			if(s.nextLine().contains("cities")){
				cities = Integer.parseInt(s.nextLine());
				for(int i = 0; i < cities; i++){
					coords = s.nextLine().split(" ");
					x = Integer.parseInt(coords[0]);
					y = Integer.parseInt(coords[1]);
					if(x > boundaryX || x <= 0){
						if(Globals.getMode() == 0){
							JOptionPane.showMessageDialog(null, "Invalid X value for this city",
									"Invalid X value",	JOptionPane.ERROR_MESSAGE);
						}
						loaded = false;
						s.close();
						return null;
					}
					if(y > boundaryY || y <= 0){
						if(Globals.getMode() == 0){
							JOptionPane.showMessageDialog(null, "Invalid Y value for this city",
									"Invalid Y value",	JOptionPane.ERROR_MESSAGE);
						}
						loaded = false;
						s.close();
						return null;
					}
					tempCities.add(new City(x, y, i));	
				}
			}
			uphill = Integer.parseInt(s.nextLine());
			iterations = Integer.parseInt(s.nextLine());

			//loading is only complete if it gets to here and the above checks pass
			if(s.nextLine().contains("EOF")){
				s.close();
				if(this.validate(alpha, beta, decayRate, initPhero, agents, tempCities.size(), iterations, uphill)){
					this.setValues(alpha, beta, decayRate, initPhero, agents, tempCities.size(), iterations, uphill);
				}else{
					loaded = false;
					return null;
				}
				/*
				 * I only want the user to load words from the file, thus method 0.
				 * they can swap the method once the world has been loaded
				 */
				this.loaded = true;
				return new World(this, agents, tempCities.size(), tempCities, uphill, 0);
			}
			s.close();
		}catch(InputMismatchException e){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Corrput file, please check the files format",
						"File Corruption",	JOptionPane.ERROR_MESSAGE);
			}
			loaded = false;
			return null;
		}catch(NumberFormatException e){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Corrput file, please check the files format",
						"File Corruption",	JOptionPane.ERROR_MESSAGE);
			}
			loaded = false;
			return null;
		} catch (FileNotFoundException e) {
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "The seclted file: " + fileName + " cannot be found.",
						"File not found",	JOptionPane.ERROR_MESSAGE);
			}
			loaded = false;
			return null;
		}
		return null;

	}

	/**
	 * 
	 * @return the number of iterations
	 */
	public int getIterations() {
		return iterations;
	}

	/**
	 * This method ensures that the specified algorithm parameters are in legal. If any parameter value
	 * is deemed to be illegal then a correct error prompt will be displayed to the user.
	 * 
	 * @param alpha the alpha parameter value
	 * @param beta the beta parameter value
	 * @param decayRate the decayRate parameter value
	 * @param initialPhero the initial edge pheromone
	 * @param agents the number of {@link Ant ants}
	 * @param cities the number of {@link City cities}
	 * @param iterations the number of iterations
	 * @param uphill the number of uphill paths to be generated
	 * @return the validity status of the algorithms parameters
	 */
	public boolean validate(double alpha, double beta, double decayRate, double initialPhero, int agents, int cities, int iterations, int uphill) {
		if(alpha > 5.0d || alpha < -5.0d){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Illegal alpha value. Alpha must be between -5.0 and 5.0\nYou entered: " + alpha,
						"Illegal value",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if(beta > 5.0d || beta < -5.0d){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Illegal beta value. Beta must be between -5.0 and 5.0\nYou entered: " + beta,
						"Illegal value",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if(decayRate >= 1.0d || decayRate <= 0.0d){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Illegal decayRate value. DecayRate must be between 0 and 1\nYou entered: " + decayRate,
						"Illegal value",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if(initialPhero >= 1.0d || initialPhero <= 0.0d){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Illegal initialPheromone value. InitialPheromone must be between 0 and 1\nYou entered: " + initialPhero,
						"Illegal value",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if(agents > 50 || agents < 1){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Illegal number of agents. Number of agents must be between 1 and 50\nYou entered: " + agents,
						"Illegal value",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if(cities > 25 || cities < 3){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Illegal number of cities. Number of cities must be between 3 and 25\nYou entered: " + cities,
						"Illegal value",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if(iterations < 1){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Illegal number of iterations. Number of iterations must be at least 1.\nYou entered: " + iterations,
						"Illegal value",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if(uphill > 15 || uphill < 0){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "Illegal Uphill Paths value. Uphill Paths must be between 0 and 15\nYou entered: " + uphill,
						"Illegal value",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return the loaded status
	 */
	public boolean getLoaded(){
		return loaded;
	}

	/**
	 * 
	 * @return the running status
	 */
	public boolean getRunning(){
		return running;
	}

	/**
	 * 
	 * @param running the running status
	 */

	public void setRunning(boolean running){
		this.running = running;
	}

	/**
	 * This methods outputs the current configuration to a specified file location
	 * @param fileName the location of the output file
	 */
	public void save(String fileName){
		/*
		 * Don't really need to check the results of the parsing here, if the algorithm gets this far the values are fine
		 */
		try {
			ArrayList<City> tempCities = (ArrayList<City>)this.getWorld().getCities();
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(System.getProperty("user.home"), fileName)));
			out.write(Double.toString(this.alpha));
			out.newLine();
			out.write(Double.toString(this.beta));
			out.newLine();
			out.write(Double.toString(this.decayRate));
			out.newLine();
			out.write(Double.toString(this.initialPheromone));
			out.newLine();
			out.write(Integer.toString(this.noOfAgents));
			out.newLine();
			out.write("cities");
			out.newLine();
			out.write(Integer.toString(tempCities.size()));
			out.newLine();
			for(City city: tempCities){
				out.write(Integer.toString(city.getX()) + " " + Integer.toString(city.getY()));
				out.newLine();
			}
			out.write(Integer.toString(this.uphillPaths));
			out.newLine();
			out.write(Integer.toString(this.iterations));
			out.newLine();
			out.write("EOF");
			out.close();
		} catch (IOException e) {
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "A error occured during file writing, please try again",
						"File writing error",	JOptionPane.ERROR_MESSAGE);
			}
		}

	}

	/**
	 * 
	 * @return the number of {@link Ant ants} working
	 */
	public int getAgentsWorking(){
		return agentsWorking;
	}

	/**
	 * 
	 * @return the number of {@link Ant ants} finished
	 */
	public int getAgentsFinished(){
		return (this.noOfAgents - agentsWorking);
	}

	/**
	 * Reduce the number of {@link Ant ants} working by 1.
	 *
	 */
	public void reduceWorking() {
		if(agentsWorking == 0){
			agentsWorking = noOfAgents;
		}
		this.agentsWorking--;

	}

	
	/**
	 * 
	 * @param i the current iteration number
	 */
	public void setCurrentIteration(int i) {
		this.currentIter = i;

	}

	/**
	 * 
	 * @return the current iteration number
	 */
	public int getCurrentIteration(){
		return currentIter;
	}

	/**
	 * 
	 * @param speed the Thread speed in milliseconds
	 */
	public void setSpeed(long speed) {
		this.speed = speed;

	}

	/**
	 * 
	 * @return the number of uphill paths
	 */
	public int getUphillPaths() {
		return uphillPaths;
	}

	/**
	 * 
	 * @param i the method used for this algorithms execution
	 */
	public void setMethod(int i) {
		this.method = i;

	}

	/**
	 * 
	 * @return the method used for this algorithms iteration
	 */
	public int getMethod(){
		return method;
	}

		public void uphillActive(boolean status) {
		this.uphillActive = status;
	}

	public boolean getUphillAtive(){
		return uphillActive;
	}

	/**
	 * 
	 * @param i the number of elite ants
	 */
	
	public void setEliteAnts(int i) {
		this.eliteAnts = i;

	}

	/**
	 * This methods checks to see if the specified number of elite ants is valid
	 * @param value the number of elite ants
	 * @return the validity of the specified number
	 */
	public boolean checkEliteValueIsValid(int value){
		if(value > noOfAgents){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "You cannot have more elite agents than there are agents",
						"Too many elite agents",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}
		if(value < 0){
			if(Globals.getMode() == 0){
				JOptionPane.showMessageDialog(null, "You cannot less than 0 elite agents",
						"Too few elite agents",	JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}
		return true;
	}

}
