package che16.dcs.aber.ac.uk.view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class ControlContainer extends JPanel{

	private ControlPanel controlPanel;
	private GridBagConstraints gbc;

	public ControlContainer() {

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

		addControlElements();


	}


	public void addControlElements(){

		controlPanel = new ControlPanel(this);
		this.add(controlPanel);

	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 700);
	}


	public  ControlPanel getControlPanel() {
		return controlPanel;
	}


}


