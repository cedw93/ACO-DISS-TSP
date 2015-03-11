package che16.dcs.aber.ac.uk.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import che16.dcs.aber.ac.uk.controller.ControlPanelListener;

public class ControlPanel extends JPanel{


	private final Dimension LABELDIMENSION = new Dimension(280,25);
	private final Dimension TEXTFIELDDIMENSION = new Dimension(100,25);
	private final Dimension BUTTONDIMENSION = new Dimension(150,40);

	private final Font LABELFONT = new Font("serif", Font.BOLD, 20);

	private final String BETALABELTEXT = "Beta";
	private final String ALPHALABELTEXT = "Alpha";
	private final String DECAYLABELTEXT = "Decay rate";
	private final String AGENTLABELTEXT = "Number of Agents";
	private final String GOALLABELTEXT = "Number of Cities";
	private final String INITPHEROLABELTEXT = "Initial Pheromone:  ";

	private final String BETAFORMAT = "";
	private final String ALPHAFORMAT = "";
	private final String AGENTFORMAT = "";
	private final String GOALFORMAT = "";
	private final String DECAYFORMAT = "";
	private final String PHEROFORMAT = "";

	private ControlPanelListener listener;

	private ControlContainer parent;
	private GridBagConstraints gbc;
	private JButton startButton, stopButton, resetButton;
	private JLabel betaLabel, alphaLabel, agentLabel, decayLabel, pheroLabel, goalNodesLabel, initPheroLabel;
	private JFormattedTextField  betaField, alphaField, agentField, decayField, goalNodesField, initPheroField;

	public ControlPanel(ControlContainer parent) {

		this.parent = parent;
		setBackground(null);
		setVisible(true);

		this.setLayout(new GridBagLayout());

		gbc = new GridBagConstraints();
		gbc.insets = new Insets(6, 6, 0, 0);
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.gridx = GridBagConstraints.RELATIVE;
		gbc.gridy = 0;

		//create the control elements
		controlInit();
	}

	private void controlInit() {

		//buttons
		resetButton = new JButton("Reset Values");
		resetButton.setPreferredSize(BUTTONDIMENSION);

		startButton = new JButton("Start");
		startButton.setPreferredSize(BUTTONDIMENSION);

		stopButton = new JButton("Stop");
		stopButton.setPreferredSize(BUTTONDIMENSION);

		//labels
		alphaLabel = new JLabel(ALPHALABELTEXT);
		alphaLabel.setPreferredSize(LABELDIMENSION);
		alphaLabel.setHorizontalAlignment(JTextField.CENTER);
		alphaLabel.setFont(LABELFONT);

		betaLabel = new JLabel(BETALABELTEXT);
		betaLabel.setPreferredSize(LABELDIMENSION);
		betaLabel.setHorizontalAlignment(JTextField.CENTER);
		betaLabel.setFont(LABELFONT);

		agentLabel = new JLabel(AGENTLABELTEXT);
		agentLabel.setPreferredSize(LABELDIMENSION);
		agentLabel.setHorizontalAlignment(JTextField.CENTER);
		agentLabel.setFont(LABELFONT);

		decayLabel = new JLabel(DECAYLABELTEXT);
		decayLabel.setPreferredSize(LABELDIMENSION);
		decayLabel.setHorizontalAlignment(JTextField.CENTER);
		decayLabel.setFont(LABELFONT);

		goalNodesLabel = new JLabel(GOALLABELTEXT);
		goalNodesLabel.setPreferredSize(LABELDIMENSION);
		goalNodesLabel.setHorizontalAlignment(JTextField.CENTER);
		goalNodesLabel.setFont(LABELFONT);

		initPheroLabel = new JLabel(INITPHEROLABELTEXT);
		initPheroLabel.setPreferredSize(LABELDIMENSION);
		initPheroLabel.setHorizontalAlignment(JTextField.CENTER);
		initPheroLabel.setFont(LABELFONT);

		//fields, formatted for correct use

		alphaField = new JFormattedTextField(createFormat(ALPHAFORMAT));
		alphaField.setPreferredSize(TEXTFIELDDIMENSION);
		alphaField.setHorizontalAlignment(JTextField.CENTER);

		betaField = new JFormattedTextField(createFormat(BETAFORMAT));
		betaField.setPreferredSize(TEXTFIELDDIMENSION);
		betaField.setHorizontalAlignment(JTextField.CENTER);

		agentField = new JFormattedTextField(createFormat(AGENTFORMAT));
		agentField.setPreferredSize(TEXTFIELDDIMENSION);
		agentField.setHorizontalAlignment(JTextField.CENTER);

		decayField = new JFormattedTextField(createFormat(DECAYFORMAT));
		decayField.setPreferredSize(TEXTFIELDDIMENSION);
		decayField.setHorizontalAlignment(JTextField.CENTER);

		goalNodesField = new JFormattedTextField(createFormat(GOALFORMAT));
		goalNodesField.setPreferredSize(TEXTFIELDDIMENSION);
		goalNodesField.setHorizontalAlignment(JTextField.CENTER);

		initPheroField = new JFormattedTextField(createFormat(PHEROFORMAT));
		initPheroField.setPreferredSize(TEXTFIELDDIMENSION);
		initPheroField.setHorizontalAlignment(JTextField.CENTER);

		//positioning used is adapted from: http://jnb.ociweb.com/jnb/jnbMar2005.html

		this.add(alphaLabel, gbc);
		gbc.gridy++;
		this.add(alphaField, gbc);

		gbc.gridy++;


		this.add(betaLabel, gbc);
		gbc.gridy++;
		this.add(betaField, gbc);

		gbc.gridy++;


		this.add(decayLabel, gbc);
		gbc.gridy++;
		this.add(decayField, gbc);
		gbc.weightx = 1;

		gbc.gridy++;

		this.add(agentLabel, gbc);
		gbc.gridy++;
		this.add(agentField, gbc);
		gbc.weightx = 1;

		gbc.gridy++;
		gbc.gridwidth = 1;

		this.add(goalNodesLabel, gbc);
		gbc.gridy++;
		this.add(goalNodesField, gbc);
		gbc.weightx = 1;

		gbc.gridy++;
		gbc.gridwidth = 1;

		this.add(initPheroLabel, gbc);
		gbc.gridy++;
		this.add(initPheroField, gbc);
		gbc.weightx = 1;

		gbc.gridy++;
		gbc.gridwidth = 1;

		gbc.insets = new Insets(20,0,0,0);

		this.add(startButton, gbc);
		gbc.gridy++;
		this.add(stopButton, gbc);
		gbc.gridy++;
		this.add(resetButton, gbc);
		//addComp(this, alphaLabel,GridBagConstraints.RELATIVE, 0, 1, 1, GridBagConstraints.BOTH, 0.7, 1);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((parent.getPreferredSize().width) - 100, (parent.getPreferredSize().height - 100));

	}

	public void setButtonListener(ControlPanelListener listener){

		this.listener = listener;
		
		resetButton.addActionListener(listener);
		startButton.addActionListener(listener);
		stopButton.addActionListener(listener);

		
	}


	/*
	 * Method taken from stack over flow: 
	 * http://stackoverflow.com/questions/18332267/gridbaglayout-2-jpanels-one-less-width-than-other
	 * date: 18/02/2015, user: niceE cOw
	 * 
	 * Trouble with grid bag positioning this resolves it.
	 * 
	 */

	private void addComp(JComponent container, JComponent comp,int gridx, int gridy, int gridwidth, int gridheight,
			int fill, double weightx, double weighty) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;

		container.add(comp, gbc);
	}


	/*
	 * take and adapted from the Oracle Java documentation
	 * used to create formats for use within the JFormattedTextFields for use input
	 * http://docs.oracle.com/javase/tutorial/uiswing/components/formattedtextfield.html#format
	 * 
	 */

	protected MaskFormatter createFormat(String format) {
		MaskFormatter result = null;
		try {
			result = new MaskFormatter(format);
		} catch (java.text.ParseException exc) {
			//as this is not used as runtime the user should never see this
			//debugging purposes only
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return result;
	}


}
