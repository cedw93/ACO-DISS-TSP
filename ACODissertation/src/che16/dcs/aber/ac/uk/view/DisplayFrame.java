package che16.dcs.aber.ac.uk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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

/**
 * This Class is used to repesent the highest level container which will house all the other user interface elements.
 * @author Christopher Edwards
 *
 */

public class DisplayFrame extends JFrame{

	//easier to modify later, 1 access and point
	private final static String title = "Chris Edwards | Ant Colony Optimisation | Major Project | Release";
	private final static int WIDTH = 1300;
	private final static int HEIGHT = 700;

	private final Font MENUFONT = new Font("serif", Font.BOLD, 20);
	private final Font MENUITEMFONT = new Font("serif", Font.BOLD, 16);

	private JFrame equationFrame;

	private DisplayCanvasContainer canvasContainer;	
	private ControlContainer controlContainer;

	private CityDetailView cityDetailView;

	private GridBagConstraints gbc;

	private UphillViewer uphillViewer;

	private JMenuBar menuBar;
	private JMenu file, speed, detail, method, stepMenu;
	private JMenuItem save, load, slowest, medium, fast, fastest, cityDetail, equationDetail, uphill, uphillDis, basic, elitist, uphillEnb, step, stepDis;

	/**
	 * Constructor with specified parameters. This is to ensure the correct visual elements are instantiated with the correct values
	 * @param model the current {@link che16.dcs.aber.ac.uk.model.AntColonyOptimisation} instance
	 * @param menuListener the current {@link che16.dcs.aber.ac.uk.controller.MenuListener} instance
	 */
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
		initUphillFrame(model);

	} 

	/**
	 * Create the instance of the {@link UphillViewer}
	 * @param model the current {@link che16.dcs.aber.ac.uk.model.AntColonyOptimisation} instance
	 */
	public void initUphillFrame(AntColonyOptimisation model){
		uphillViewer = new UphillViewer(model);
	}

	/**
	 * Create the contained user interface elements and add them to this container using a GridBagLayout.
	 */
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


	/**
	 * Create and populate the JMenuBar and add it to this container.
	 * @param menuListener the current {@link che16.dcs.aber.ac.uk.controller.MenuListener} instance
	 */
	public void addMenu(MenuListener menuListener){
		menuBar = new JMenuBar();

		file = new JMenu("File");
		file.setFont(MENUFONT);

		speed = new JMenu("Speed");
		speed.setFont(MENUFONT);

		detail = new JMenu("Details");
		detail.setFont(MENUFONT);

		method = new JMenu("Method");
		method.setFont(MENUFONT);

		stepMenu = new JMenu("Step Mode");
		stepMenu.setFont(MENUFONT);

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

		uphill = new JMenuItem("Uphill Routes");
		uphill.addActionListener(menuListener);
		uphill.setFont(MENUITEMFONT);
		detail.add(uphill);

		uphillDis = new JMenuItem("Disable Uphill Routes");
		uphillDis.addActionListener(menuListener);
		uphillDis.setFont(MENUITEMFONT);
		detail.add(uphillDis);

		uphillEnb = new JMenuItem("Enable Uphill Routes");
		uphillEnb.addActionListener(menuListener);
		uphillEnb.setFont(MENUITEMFONT);
		detail.add(uphillEnb);

		basic = new JMenuItem("Basic System");
		basic.addActionListener(menuListener);
		basic.setFont(MENUITEMFONT);
		method.add(basic);

		elitist = new JMenuItem("Elitist Ant System");
		elitist.addActionListener(menuListener);
		elitist.setFont(MENUITEMFONT);
		method.add(elitist);

		step = new JMenuItem("Enable step mode");
		step.addActionListener(menuListener);
		step.setFont(MENUITEMFONT);
		stepMenu.add(step);

		stepDis = new JMenuItem("Disable step mode");
		stepDis.addActionListener(menuListener);
		stepDis.setFont(MENUITEMFONT);
		stepMenu.add(stepDis);

		menuBar.add(file);
		menuBar.add(speed);
		menuBar.add(detail);
		menuBar.add(method);
		menuBar.add(stepMenu);

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

	/**
	 * 
	 * @return the current {@link DisplayCanvasContainer} instance
	 */
	public DisplayCanvasContainer getCanvasContainer() {
		return canvasContainer;
	}

	/**
	 * 
	 * @return the current {@link ControlContainer} instance
	 */
	public ControlContainer getControlContainer(){
		return controlContainer;
	}

	/**
	 * 
	 * @return the current {@link CityDetailView} instance
	 */

	public CityDetailView getCityDetailView(){
		return cityDetailView;
	}

	/**
	 * 
	 * @return the current {@link EquationFrame} instance
	 */
	public JFrame getEquationFrame(){
		return equationFrame;
	}
	/**
	 * 
	 * @return the current {@link UphillViewer} instance
	 */
	public UphillViewer getUphillFrame(){
		return uphillViewer;
	}


}