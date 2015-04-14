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
import javax.swing.text.JTextComponent;
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
	private final String INITPHEROLABELTEXT = "Initial Pheromone";
	private final String ITERATIONLABELTEXT = "Iterations";
	private final String UPHILLLABELTEXT = "Uphill Paths";

	private ControlPanelListener listener;

	private ControlContainer parent;
	private GridBagConstraints gbc;
	private JButton startButton, stopButton, resetButton;
	private JLabel betaLabel, alphaLabel, agentLabel, decayLabel, pheroLabel, goalNodesLabel, initPheroLabel, iterationLabel, uphillLabel;
	private JTextField  betaField, alphaField, agentField, decayField, goalNodesField, initPheroField, iterationField, uphillField;

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

		uphillLabel = new JLabel(UPHILLLABELTEXT);
		uphillLabel.setPreferredSize(LABELDIMENSION);
		uphillLabel.setHorizontalAlignment(JTextField.CENTER);
		uphillLabel.setFont(LABELFONT);

		iterationLabel = new JLabel(ITERATIONLABELTEXT);
		iterationLabel.setPreferredSize(LABELDIMENSION);
		iterationLabel.setHorizontalAlignment(JTextField.CENTER);
		iterationLabel.setFont(LABELFONT);

		//fields

		alphaField = new JTextField();
		alphaField.setPreferredSize(TEXTFIELDDIMENSION);
		alphaField.setHorizontalAlignment(JTextField.CENTER);
		alphaField.setText("2.5");

		betaField = new JTextField();
		betaField.setPreferredSize(TEXTFIELDDIMENSION);
		betaField.setHorizontalAlignment(JTextField.CENTER);
		betaField.setText("2.5");

		agentField = new JTextField();
		agentField.setPreferredSize(TEXTFIELDDIMENSION);
		agentField.setHorizontalAlignment(JTextField.CENTER);
		agentField.setText("15");

		decayField = new JTextField();
		decayField.setPreferredSize(TEXTFIELDDIMENSION);
		decayField.setHorizontalAlignment(JTextField.CENTER);
		decayField.setText("0.2");

		goalNodesField = new JTextField();
		goalNodesField.setPreferredSize(TEXTFIELDDIMENSION);
		goalNodesField.setHorizontalAlignment(JTextField.CENTER);
		goalNodesField.setText("10");

		initPheroField = new JTextField();
		initPheroField.setPreferredSize(TEXTFIELDDIMENSION);
		initPheroField.setHorizontalAlignment(JTextField.CENTER);
		initPheroField.setText("0.5");

		uphillField = new JTextField();
		uphillField.setPreferredSize(TEXTFIELDDIMENSION);
		uphillField.setHorizontalAlignment(JTextField.CENTER);
		uphillField.setText("5");

		iterationField = new JTextField();
		iterationField.setPreferredSize(TEXTFIELDDIMENSION);
		iterationField.setHorizontalAlignment(JTextField.CENTER);
		iterationField.setText("1");
		//positioning used is adapted from: http://jnb.ociweb.com/jnb/jnbMar2005.html


		this.add(uphillLabel, gbc);
		gbc.gridy++;
		this.add(uphillField, gbc);

		gbc.gridy++;

		this.add(iterationLabel, gbc);
		gbc.gridy++;
		this.add(iterationField, gbc);

		gbc.gridy++;

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
		return new Dimension((parent.getPreferredSize().width) - 100, (parent.getPreferredSize().height ));

	}

	public void setButtonListener(ControlPanelListener listener){

		this.listener = listener;

		resetButton.addActionListener(listener);
		startButton.addActionListener(listener);
		stopButton.addActionListener(listener);


	}


	public JTextField getIterationField() {
		return iterationField;

	}

	public JTextField getBetaField() {
		return betaField;
	}

	public JTextField getAlphaField() {
		return alphaField;
	}

	public JTextField getAgentField() {
		return agentField;
	}

	public JTextField getDecayField() {
		return decayField;
	}

	public JTextField getGoalNodesField() {
		return goalNodesField;
	}

	public JTextField getInitPheroField() {
		return initPheroField;
	}

	public JTextField getUphillField() {
		return uphillField;
	}

	public JButton getStartButton() {
		return startButton;
		
	}


}
