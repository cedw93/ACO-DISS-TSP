package che16.dcs.aber.ac.uk.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class EquationFrame extends JFrame{


	private ProbabilityPanel probabilityPanel;
	private PheroPanel pheroPanel;

	private CardLayout cardLayout;

	private JButton probabilityButton, pheroButton;

	public EquationFrame(){
		super("Ant Colony Optimisation | Equations");
		setSize(new Dimension(800, 600));
		setLocationRelativeTo(null);
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		initPanels();
		cardLayout.show(this.getContentPane(), "probability");
		setResizable(false);
		setVisible(true);

	}

	public void initPanels(){

		probabilityPanel = new ProbabilityPanel(this);
		this.add(probabilityPanel, "probability");

		pheroPanel = new PheroPanel(this);
		this.add(pheroPanel, "pheromone");
	}

	public void paint(Graphics g) {
		super.paint(g);

	}

	private class ProbabilityPanel extends JPanel{

		private Image probabilityImage;

		private ProbabilityPanel(final EquationFrame equationFrame){
			super();

			try{
				probabilityImage = ImageIO.read(getClass().getResource("/images/probability.png"));
			}catch (IOException e) {
				e.printStackTrace();
			}

			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 500));
			setBackground(Color.GREEN);

			probabilityButton = new JButton("Next");
			probabilityButton.setPreferredSize(new Dimension(150,50));	
			probabilityButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e)
				{
					cardLayout.show(equationFrame.getContentPane(), "pheromone");	               
				}
			});      

			add(probabilityButton);
		}


		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(probabilityImage, 0, 0, null);
		}

	}

	private class PheroPanel extends JPanel{

		private Image pheroImage;

		private PheroPanel(final EquationFrame equationFrame){
			super();

			try{
				pheroImage = ImageIO.read(getClass().getResource("/images/placeholder.png"));
			}catch (IOException e) {
				e.printStackTrace();
			}

			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 500));
			setBackground(Color.GREEN);

			pheroButton = new JButton("Previous");
			pheroButton.setPreferredSize(new Dimension(150,50));	
			pheroButton.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e)
				{
					cardLayout.show(equationFrame.getContentPane(), "probability");	               
				}
			});      

			add(pheroButton);
		}
		
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(pheroImage, 0, 0, null);
		}

	}

}

