package che16.dcs.aber.ac.uk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import che16.dcs.aber.ac.uk.model.Ant;
import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.model.City;

public class DisplayCanvas extends JPanel{

	private DisplayCanvasContainer parent;
	private AntColonyOptimisation model;

	public DisplayCanvas(DisplayCanvasContainer parent) {

		this.parent = parent;
		setBackground(Color.WHITE);
		setVisible(true);

	}

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2;
		g2 = (Graphics2D) g;

		ArrayList<City> cities = (ArrayList<City>)model.getWorld().getCities();
		ArrayList<Ant> agents = (ArrayList<Ant>)model.getWorld().getAnts();
		LinkedList<Integer> bestRoute;

		for(City c: cities){
			//g2.fillOval((p.x - 10)  * 20, p.y * 20, 20, 20);
			g2.fillOval((c.getX() * 20) - 10, (c.getY() * 20) - 10, 20, 20);
			g2.drawString(Integer.toString(c.getIndex()), c.getX() * 21, c.getY()*21);

		}

		//set these as defaults so its easy to compare, they are inverted to what you'd expect
		//maxEdgePhero is negative infinity so when you compare if a value is larger than the current max, there will always be one match
		//the same applied with the min pheromone, there exsits always one value less than positive infinity.
		double maxEdgePhero = Double.NEGATIVE_INFINITY;
		double minEdgePhero = Double.POSITIVE_INFINITY;
		double differenceMinMax = 0.0d;
		//get the max edge pheromone and the minimum edge pheromone
		for(int i = 0; i < model.getWorld().getPheromone().length; i++){
			for(int j = 0; j < model.getWorld().getPheromone().length; j++){
				//if i and j are equal then continue the loop we dont want to check the value if i and j match
				if(i != j){
					if(maxEdgePhero < model.getWorld().getPheromone()[i][j].getPheromoneValue()){
						maxEdgePhero = model.getWorld().getPheromone()[i][j].getPheromoneValue();
					}
					if(minEdgePhero > model.getWorld().getPheromone()[i][j].getPheromoneValue()){
						minEdgePhero = model.getWorld().getPheromone()[i][j].getPheromoneValue();
					}
				}
			}
		}
		differenceMinMax = (maxEdgePhero - minEdgePhero);

		if(model.getFinished()){
			//	System.out.println("maxEdgePhero * 1000 == " + maxEdgePhero * 1000);
			//System.out.println("minEdgePhero * 1000 == " + minEdgePhero * 1000);
			//System.out.println("differenceMinMax *1000 == " + differenceMinMax * 1000);

		}


		//if(!model.getFinished()){
		int alpha = 0;
		for(int i = 0; i < cities.size()-1; i++){
			for(int j = 0; j < cities.size(); j++){
				/*
				 * This pheromone display is not perfect and needs refining, but it somewhat demonstrates the ideas.
				 * Pheromone values are really small, so multiplying by 10000 helps reduce this however there has
				 * to be a better way to represent this, it works for now though.
				 * 
				 *TODO: REVISIT THIS
				 */
				double pheroIJ = model.getWorld().getPheromone()[i][j].getPheromoneValue();
				alpha = (int)(pheroIJ * 10000);
				if(alpha > 255){
					alpha = 255;
				}
				g2.setColor(new Color(0,0,0,alpha));
				g2.drawLine(cities.get(i).getX() * 20, cities.get(i).getY() * 20, cities.get(j).getX() * 20, cities.get(j).getY() * 20);

			}
		}
		//	}
		/*
		 * Because of the way an Ant stores is current location, and the way the next one is select there is no safe or quick way
		 * to get the [x][y] index of the Ant.
		 * 
		 * Instead every ant is looped through, is the ant is not finished its walk (then it needs to be painted) then:
		 * loop through every city:
		 * 			if the current ant's index matches a cities index
		 * 				paint the ant at this cities location
		 * 
		 * This could probably be improved as the complexity is quite large as it stands.
		 */

		//TODO: MAYBE MAKE CITY DIAMETER CHANGE DEPENDING ON THE NUMBER OF ANTS AT THE CITY
		for(Ant ant: agents){
			if(!ant.getFinished()){
				for(City c: cities){
					if(ant.getCurrentIndex() == c.getIndex()){
						g2.setColor(Color.GREEN);
						g2.fillOval(c.getX() * 20, c.getY() * 20, 10, 10);
					}
				}
			}
		}

		/*
		 * Only draw the best route if there is one.
		 * First you need to get each element in the bestRoute Linked list
		 * 	For each of these you need to match its value to that of a city
		 * 		you need to then get the next index and draw the route from the previous to the next
		 *  This is really messy and needs improvement
		 */

		if(model.getBestDistance() > -1){
			bestRoute = model.getBestRoute();
			if(bestRoute != null){
				for(int i = 0; i < bestRoute.size(); i++){
					for(City c: cities){
						if(bestRoute.get(i) == c.getIndex()){
							if(i + 1 < bestRoute.size()){
								for(City c2: cities){
									if(bestRoute.get(i + 1) == c2.getIndex()){
										g2.setColor(Color.RED);
										g.drawLine(c.getX() * 20, c.getY()*20, c2.getX() * 20, c2.getY() * 20);
									}
								}
							}
						}
					}
				}
			}
		}

	}


	@Override
	public Dimension getPreferredSize() {

		return new Dimension((parent.getPreferredSize().width) - 100, (parent.getPreferredSize().height - 100));
	}

	public void setModel(AntColonyOptimisation model) {
		this.model = model;

	}

	public void render() {

		this.repaint();

	}

}

