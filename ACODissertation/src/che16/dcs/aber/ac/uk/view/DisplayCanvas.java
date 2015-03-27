package che16.dcs.aber.ac.uk.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import che16.dcs.aber.ac.uk.model.Ant;
import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.model.City;
import che16.dcs.aber.ac.uk.utils.MathsHelper;

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
		List<Integer> temp;

		g2 = (Graphics2D) g;
		if(model.getWorld() != null){
			ArrayList<City> cities = (ArrayList<City>)model.getWorld().getCities();
			ArrayList<Ant> agents = (ArrayList<Ant>)model.getWorld().getAnts();
			LinkedList<Integer> bestRoute;

			for(City c: cities){
				g2.fillOval((c.getX() * 20) - 10, (c.getY() * 20) - 10, 20, 20);
				g2.drawString(Integer.toString(c.getIndex()), c.getX() * 21, c.getY()*21);

			}

			if(!model.getFinished()){
				int alpha = 0;
				for(int i = 0; i < cities.size()-1; i++){
					for(int j = 0; j < cities.size(); j++){
						/*
						 * This pheromone display is not perfect and needs refining, but it somewhat demonstrates the ideas.
						 * Pheromone values are really small, so multiplying by 2000 helps reduce this however there has
						 * to be a better way to represent this, it works for now though.
						 * 
						 *TODO: REVISIT THIS
						 */
						double pheroIJ = model.getWorld().getPheromone()[i][j].getPheromoneValue();
						//toy with this 2000 value
						alpha = (int)(pheroIJ * 2000);
						if(alpha > 255){
							alpha = 255;
						}
						g2.setColor(new Color(0,0,0,alpha));
						g2.drawLine(cities.get(i).getX() * 20, cities.get(i).getY() * 20, cities.get(j).getX() * 20, cities.get(j).getY() * 20);
					}
				}			
			}else{
				//reset to zero when we are done!
				parent.getFrame().getCityDetailView().updateValues(model.getWorld().getCities());
			}

			//these markers will be present in the end just to aid understanding

			/* Decided not to draw the uphill routes as it may confuse the user use the uphill viewer instead
			City destination = null;
			for(City c: model.getWorld().getCities()){
				temp = c.getUphilRoutes();
				for(int i = 0; i < temp.size(); i++){
					for(City c2: model.getWorld().getCities()){
						if(c2.getIndex() == temp.get(i)){
							destination = c2;
							//we are done
							break;
						}
						double pheroIJ = model.getWorld().getPheromone()[c.getIndex()][temp.get(i)].getPheromoneValue();
						//toy with this 2000 value
						int alpha = (int)(pheroIJ * 2000);
						if(alpha > 255){
							alpha = 255;
						}
						//greenish 83,32,212
						if(destination != null){
							g2.setColor(new Color(0,220,0,alpha));
							g2.drawLine(c.getX() * 20, c.getY() * 20, destination.getX() * 20, destination.getY() * 20);
						}
					}
				}
			}
			 */

			parent.getFrame().getUphillFrame().setUphillData();

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
			City start = null;
			City destination = null;
			//TODO: MAYBE MAKE CITY DIAMETER CHANGE DEPENDING ON THE NUMBER OF ANTS AT THE CITY
			try{
				for(Ant ant: agents){
					if(!ant.getFinished()){
						for(City c: cities){
							if(ant.getCurrentIndex() == c.getIndex()){
								g2.setColor(Color.GREEN);
								g2.fillOval(c.getX() * 20, c.getY() * 20, 10, 10);

							}
							parent.getFrame().getCityDetailView().updateValues(model.getWorld().getCities());
							if(ant.getMovementTracker()[0] != ant.getMovementTracker()[1]){
								if(c.getIndex() == ant.getMovementTracker()[0]){
									start = c;
								}
								if(c.getIndex() == ant.getMovementTracker()[1]){
									destination = c;
								}
								if(start != null && destination != null){
									g2.setColor(Color.LIGHT_GRAY);
									for(int i = 1; i < 5; i++){
										//cast to a double to stop integer behaviours rounding down to 0
										double result = ((double)i)/5;
										//draw an ellipse2D every 1/5 the way along the line
										float y = (float) (MathsHelper.linearInterpolateY(start.getY(), destination.getY(), result));
										float x = (float) (MathsHelper.linearInterpolateY(start.getX(), destination.getX(), result));
										Ellipse2D movement = new Ellipse2D.Float((x * 20.0f) - 5, (y * 20.0f) - 5, 10, 10);
										g2.fill(movement);
										g2.draw(movement);

									}
								}
							}

						}

					}
					//reset the values
					start = null;
					destination = null;
				}
			}catch(ConcurrentModificationException e){
				//sometimes java complains if the thread speed is too fast
			}

			/*
			 * Only draw the best route if there is one.
			 * First you need to get each element in the bestRoute Linked list
			 * 	For each of these you need to match its value to that of a city
			 * 		you need to then get the next index and draw the route from the previous to the next
			 *  This is really messy and needs improvement
			 */

			if(model.getWorld().getBestDistance() > -1){
				bestRoute = model.getWorld().getBestRoute();
				if(bestRoute != null){
					for(int i = 0; i < bestRoute.size(); i++){
						for(City c: cities){
							if(bestRoute.get(i) == c.getIndex()){
								if(i + 1 < bestRoute.size()){
									for(City c2: cities){
										if(bestRoute.get(i + 1) == c2.getIndex()){
											g2.setColor(Color.RED);
											//make the best line slightly thicker than the rest so it stands out more
											g2.setStroke(new BasicStroke(2));
											g2.drawLine(c.getX() * 20, c.getY()*20, c2.getX() * 20, c2.getY() * 20);

										}
									}
								}
							}
						}
					}
				}
			}

			parent.setContentTop(" Best Route: " + model.getWorld().getBestRoute() + " Best Distance: " + model.getWorld().getBestDistance());
			if(model.getMethod() == 0){
				parent.setContentBottom("iteration: " + (model.getCurrentIteration() + 1) + " Agents finished: " + model.getAgentsFinished()+ " Running: " + model.getRunning());
			}else if(model.getMethod() == 1){
				if(model.getWorld().getEliteAnts() != null){
					parent.setContentBottom("iteration: " + (model.getCurrentIteration() + 1) + " Agents finished: " + model.getAgentsFinished()+ " Elite Agents: " + model.getWorld().getEliteCount() +  " Running: " + model.getRunning());
				}
			}
			parent.repaint();

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

