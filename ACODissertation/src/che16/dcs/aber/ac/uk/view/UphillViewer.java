package che16.dcs.aber.ac.uk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.model.City;

public class UphillViewer extends JFrame{

	private UphillPanel content;
	private AntColonyOptimisation model;

	public UphillViewer(AntColonyOptimisation model){

		super("Uphill routes (green paths) and thier direction");
		setSize(new Dimension(500, 600));

		content = new  UphillPanel(model);

		add(content);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(false);
	}

	public void setUphillData(){
		content.repaint();
	}

	private class UphillPanel extends JPanel{

		private AntColonyOptimisation model;

		private UphillPanel(AntColonyOptimisation model){
			setBackground(Color.WHITE);
			this.model = model;
		}

		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setFont(new Font("serif", Font.BOLD, 28));
			g.drawString("Weighted routes", 160, 50);
			g.setFont(new Font("serif", Font.BOLD, 18));
			int index = 100;

			if(model.getWorld() != null){
				if(model.getUphillAtive()){
					for(City c: model.getWorld().getCities()){
						for(int i = 0; i < c.getUphilRoutes().size(); i++){
							g.drawString("Index: " + c.getIndex() + " to index: " + c.getUphilRoutes().get(i)+ " is 'uphill' and costs more to travel" , 40, index);
							index += 20;
						}
					}
				}else{
					g.drawString("Uphill routes are disabled, please enable them.", 50, 100);
				}
			}else{
				g.drawString("No cities currently present, please create some.", 50, 100);
			}

		}
	}
}
