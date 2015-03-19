package che16.dcs.aber.ac.uk.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import che16.dcs.aber.ac.uk.controller.ControlPanelListener;
import che16.dcs.aber.ac.uk.controller.MenuListener;
import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;

public class DisplayFrame extends JFrame{

	//easier to modify later, 1 access and point
	private final static String title = "Chris Edwards | Ant Colony Optimisation | Major Project";
	private final static int WIDTH = 1300;
	private final static int HEIGHT = 700;

	private final Font MENUFONT = new Font("serif", Font.BOLD, 20);
	private final Font MENUITEMFONT = new Font("serif", Font.BOLD, 16);

	private JFrame equationFrame;
	
	private DisplayCanvasContainer canvasContainer;	
	private ControlContainer controlContainer;

	private CityDetailView cityDetailView;

	private GridBagConstraints gbc;

	private JMenuBar menuBar;
	private JMenu file, speed, detail;
	private JMenuItem save, load, slowest, medium, fast, fastest, cityDetail, equationDetail;

	public DisplayFrame(AntColonyOptimisation model, MenuListener menuListener) {

		super(title);
		//simple inheritance. 
		setSize(new Dimension(WIDTH, HEIGHT));
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMenu(menuListener);
		setVisible(true);

		canvasContainer = new DisplayCanvasContainer();
		controlContainer = new ControlContainer();
		cityDetailView = new CityDetailView();

		addComponents();
		this.pack();
		//used to display info about the equations used to the user, initially visible
		equationFrame = new EquationFrame();

	} 

	public void addComponents(){

		this.setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		//dont want any insets... for now
		gbc.insets = new Insets(0,0,0,0);

		addComp(this, canvasContainer, 0, 0, 1, 1, GridBagConstraints.BOTH, 0.7, 1);
		addComp(this, controlContainer, 1, 0, 1, 1, GridBagConstraints.BOTH, 0.3, 1);

	}

	public void render(){
		canvasContainer.getCanvas().render();
	}

	public void addMenu(MenuListener menuListener){
		menuBar = new JMenuBar();

		file = new JMenu("File");
		file.setFont(MENUFONT);

		speed = new JMenu("Speed");
		speed.setFont(MENUFONT);

		detail = new JMenu("Details");
		detail.setFont(MENUFONT);

		//ITEMS
		save = new JMenuItem("Save");
		save.addActionListener(menuListener);
		save.setFont(MENUITEMFONT);
		file.add(save);

		load = new JMenuItem("Load");
		load.addActionListener(menuListener);
		load.setFont(MENUITEMFONT);
		file.add(load);  

		slowest = new JMenuItem("Slowest - 1000ms");
		slowest.addActionListener(menuListener);
		slowest.setFont(MENUITEMFONT);
		speed.add(slowest);

		medium = new JMenuItem("Medium - 500ms");
		medium.addActionListener(menuListener);
		medium.setFont(MENUITEMFONT);
		speed.add(medium);

		fast = new JMenuItem("Fast - 100ms");
		fast.addActionListener(menuListener);
		fast.setFont(MENUITEMFONT);
		speed.add(fast);

		fastest = new JMenuItem("Fastest - 10ms");
		fastest.addActionListener(menuListener);
		fastest.setFont(MENUITEMFONT);
		speed.add(fastest);

		cityDetail = new JMenuItem("City Detail"); 
		cityDetail.addActionListener(menuListener);
		cityDetail.setFont(MENUITEMFONT);
		detail.add(cityDetail);

		equationDetail = new JMenuItem("Equations");
		equationDetail.addActionListener(menuListener);
		equationDetail.setFont(MENUITEMFONT);
		detail.add(equationDetail);

		menuBar.add(file);
		menuBar.add(speed);
		menuBar.add(detail);
		this.setJMenuBar(menuBar);
	}

	/*
	 * Method taken from stack over flow: 
	 * http://stackoverflow.com/questions/18332267/gridbaglayout-2-jpanels-one-less-width-than-other
	 * date: 18/02/2015, user: niceE cOw
	 * 
	 * Trouble with grid bag positioning this resolves it.
	 * 
	 */

	private void addComp(JFrame frame, JComponent comp, int gridx, int gridy, int gridwidth, int gridheight,
			int fill, double weightx, double weighty) {
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.gridwidth = gridwidth;
		gbc.gridheight = gridheight;
		gbc.fill = fill;
		gbc.weightx = weightx;
		gbc.weighty = weighty;

		frame.add(comp, gbc);
	}


	/* doing it this way as it is the least 'messy' way to do so
	 * you don't have to pass instances of the listener through all classes of the view
	 * and it is still very maintainable
	 */
	public void setControlPanelListener(ControlPanelListener panelListener) {

		controlContainer.getControlPanel().setButtonListener(panelListener);

	}

	public DisplayCanvasContainer getCanvasContainer() {
		return canvasContainer;
	}

	public ControlContainer getControlContainer(){
		return controlContainer;
	}

	public CityDetailView getCityDetailView(){
		return cityDetailView;
	}

	public JFrame getEquationFrame(){
		return equationFrame;
	}

}