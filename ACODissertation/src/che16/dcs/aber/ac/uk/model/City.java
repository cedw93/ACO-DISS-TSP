package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class is used to represent a node in the graphical representation of the Travelling Salesman Problem.
 * @author Christopher Edwards
 *
 */

public class City {

	private int x, y, index, antsHere;
	private List<Integer> uphilRoutes;

	/**
	 * Constructor which creates a City with specified values
	 * @param x the x coordinate for the City
	 * @param y the y coordinate for the City
	 * @param index the City index
	 */

	public City(int x, int y, int index){
		this.x = x;
		this.y = y;
		this.index = index;
		antsHere = 0;
		uphilRoutes = new ArrayList<Integer>(5);
	}

	/**
	 * 
	 * @return this City's x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return this City's y coordinate
	 */

	public int getY() {
		return y;
	}

	/**
	 * 
	 * @return this City's index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 
	 * @return the number of {@link Ant ants} at this City
	 */
	public int getAntsHere(){
		return antsHere;
	}

	/**
	 * Adjust the number of {@link Ant ants} at this City location by a specified amount
	 * @param adjustment the adjustment to the number of {@link Ant ants} at this City
	 */
	public void adjustAntsHere(int adjustment){
		//this allows for a reduction and addition of ants, wrapped the value in parenthesis to ensure no confusion with negative values
		//e.g antsHere +=-1 becomes antsHere +=(-1) its easier.
		antsHere += (adjustment);
	}

	/**
	 * Reset the number of {@link Ant ants} at this City
	 */
	public void resetAntCount() {
		this.antsHere = 0;
	}

	/**
	 * 
	 * @return the list of uphill paths from this City
	 */
	public List<Integer> getUphilRoutes(){
		return uphilRoutes;
	}

	/**
	 * Add a specified City index to the uphill route list
	 * @param element the City index to add to the uphill routes
	 */
	public void addToUphil(int element){
		uphilRoutes.add(element);
	}

}
