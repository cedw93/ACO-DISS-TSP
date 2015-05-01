package che16.dcs.aber.ac.uk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

/**
 * This Class is used to contain the {@link DisplayCanvas} instance and provide some additional visual aids to the
 * user
 * @author Christopher Edwards
 *
 */

public class DisplayCanvasContainer extends JPanel{

	private DisplayCanvas canvas;
	private GridBagConstraints gbc;
	private String contentTop, contentBottom;
	private DisplayFrame frame;

	public DisplayCanvasContainer() {
		this.contentTop = "Best Route:[N/A]                Best Distance: N/A";
		this.contentBottom = "Iteration: N/A               Total iterations: N/A               Agents Working: N/A";
		setBackground(new Color(0xC2C0CC));
		setVisible(true);
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.FIRST_LINE_START;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;

		addCanvas();

	}


	/**
	 * Create the instance of the {@link DisplayCanvas} and add it to this container.
	 */
	public void addCanvas(){

		canvas = new DisplayCanvas(this);
		this.add(canvas);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(900, 700);
	}

	/**
	 * 
	 * @return the current {@link DisplayCanvas} instance
	 */
	public DisplayCanvas getCanvas(){
		return canvas;
	}

	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//there is better ways to do this!

		//border around the top result box
		g.setColor(Color.BLACK);
		g.drawRect(49, 1, 801, 39);
		g.setColor(Color.white);
		g.fillRect(50, 2, 800, 38);

		//border around the canvas
		g.setColor(Color.BLACK);
		g.drawRect(49, 49, 801, 601);

		//border around the bottom result box
		g.setColor(Color.BLACK);
		g.drawRect(49, 659, 801, 39);
		g.setColor(Color.white);
		g.fillRect(50, 660, 800, 38);

		g.setColor(Color.BLACK);

		g.drawString(contentTop, 80, 25);
		g.drawString(contentBottom, 250, 680);
	}

	public void setContentTop(String content){
		this.contentTop = content;
	}

	public void setContentBottom(String content){
		this.contentBottom = content;
	}

	/**
	 * 
	 * @param frame the {@link DisplayFrame} instance
	 */
	public void setDisplayFrame(DisplayFrame frame) {
		this.frame = frame;

	}

	/**
	 * 
	 * @return the current {@link DisplayFrame} instance
	 */
	
	public DisplayFrame getFrame(){
		return frame;
	}

}