package che16.dcs.aber.ac.uk.model;

import java.util.ArrayList;
import java.util.List;

public class City {

	private int x, y, index, antsHere;
	private List<Integer> uphilRoutes;

	public City(int x, int y, int index){
		this.x = x;
		this.y = y;
		this.index = index;
		antsHere = 0;
		uphilRoutes = new ArrayList<Integer>(5);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getIndex() {
		return index;
	}

	public int getAntsHere(){
		return antsHere;
	}

	public void adjustAntsHere(int adjustment){
		//this allows for a reduction and addition of ants, wrapped the value in parenthesis to ensure no confusion with negative values
		//e.g antsHere +=-1 becomes antsHere +=(-1) its easier.
		antsHere += (adjustment);
	}

	public void resetAntCount() {
		this.antsHere = 0;
	}

	public List<Integer> getUphilRoutes(){
		return uphilRoutes;
	}

	public void addToUphil(int element){
		uphilRoutes.add(element);
	}

}
