package che16.dcs.aber.ac.uk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class DisplayCanvasContainer extends JPanel{

	private DisplayCanvas canvas;
	private GridBagConstraints gbc;
	

	public DisplayCanvasContainer() {

		setBackground(new Color(0xC2C0CC));
		setVisible(true);
		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.FIRST_LINE_START;

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;

		addCanvas();

	}



	public void addCanvas(){

		canvas = new DisplayCanvas(this);
		this.add(canvas);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(900, 700);
	}


	public DisplayCanvas getCanvas(){
		return canvas;
	}
	
}