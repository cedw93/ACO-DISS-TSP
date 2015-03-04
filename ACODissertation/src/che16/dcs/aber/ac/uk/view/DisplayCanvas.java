package che16.dcs.aber.ac.uk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

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

		ArrayList<City> cities = (ArrayList)model.getWorld().getCities();
		ArrayList<Ant> agents = (ArrayList)model.getWorld().getAnts();

		for(City c: cities){
			//g2.fillOval((p.x - 10)  * 20, p.y * 20, 20, 20);
			g2.fillOval((c.getX() * 20) - 10, (c.getY() * 20) - 10, 20, 20);

		}

		for(int i = 0; i < cities.size()-1; i++){
			for(int j = 0; j < cities.size(); j++){
				g2.setColor(Color.BLACK);
				g2.drawLine(cities.get(i).getX() * 20, cities.get(i).getY() * 20, cities.get(j).getX() * 20, cities.get(j).getY() * 20);

			}
		}

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

