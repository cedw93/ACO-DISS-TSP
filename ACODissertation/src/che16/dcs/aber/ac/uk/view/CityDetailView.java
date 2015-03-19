package che16.dcs.aber.ac.uk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import che16.dcs.aber.ac.uk.model.AntColonyOptimisation;
import che16.dcs.aber.ac.uk.model.City;

public class CityDetailView extends JFrame{

	private final Font TABLEFONT = new Font("serif", Font.BOLD, 16);
	private JTable table;

	public CityDetailView(){

		super("Number of Agents at each City | City Details");
		setBackground(new Color(0xC2C0CC));
		//add the table to the environment
		addTable();
		pack();
		setLocationRelativeTo(null);
		setSize(new Dimension(200,470));
		setResizable(false);
		setVisible(false);

	}

	public void addTable(){


		table = new JTable(new MyTableModel());
		//stop a user editing the values, then remove the grid lines (it looks nicer)
		table.setEnabled(false);
		table.setShowGrid(false);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFont(TABLEFONT);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER );

		for(int i = 0; i < table.getColumnCount(); i++){
			table.getColumnModel().getColumn(i).setMaxWidth(100);
			table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		}

		JScrollPane scrollPane = new JScrollPane(table);
		//x pos, y pos, x width, y width
		scrollPane.setBounds(0,0,200,500);
		this.add(scrollPane);
	}

	public void updateValues(List<City> cities){
		int total = 0;
		for(City c: cities){
			table.setValueAt(c.getIndex(), c.getIndex(), 0);
			table.setValueAt(c.getAntsHere(), c.getIndex(), 1);
			total += c.getAntsHere();
		}
		table.setValueAt(total, (table.getRowCount() - 1), 1);
	}

}
