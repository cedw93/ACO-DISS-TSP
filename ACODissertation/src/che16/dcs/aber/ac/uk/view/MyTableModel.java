package che16.dcs.aber.ac.uk.view;


import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{

	private final String[] COLUMNNAMES = {"City Index", "No. of Agents"}; 

<<<<<<< HEAD
	//26 because the max number of cities is 25, the +1 for the 'total' row
	public final int ROWS = 26;
=======
	public final int ROWS = 25;
>>>>>>> origin/master
	public final int COLS = 2;

	public Object[][] data = new Object[ROWS][COLS];

	public MyTableModel(){

<<<<<<< HEAD
		//-1 because the final row will be a total
		for(int i = 0; i < (ROWS-1); i++){
=======
		for(int i = 0; i < ROWS; i++){
>>>>>>> origin/master
			for(int j = 0; j < COLS; j++){
				//initially there will be no real data
				data[i][j] = "N/A";

			}
		}
<<<<<<< HEAD

		data[ROWS-1][0] = "Total";
		data[ROWS-1][1] = "N/A";
=======
>>>>>>> origin/master
	}

	public int getColumnCount() {
		return COLUMNNAMES.length;
	}

	public int getRowCount() {
		return data.length;
	}

	public String getColumnName(int col) {
		return COLUMNNAMES[col];
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

}