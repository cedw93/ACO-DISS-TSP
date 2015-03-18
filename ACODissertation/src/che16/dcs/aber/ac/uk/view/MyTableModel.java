package che16.dcs.aber.ac.uk.view;


import javax.swing.table.AbstractTableModel;

public class MyTableModel extends AbstractTableModel{

	private final String[] COLUMNNAMES = {"City Index", "No. of Agents"}; 

	public final int ROWS = 25;
	public final int COLS = 2;

	public Object[][] data = new Object[ROWS][COLS];

	public MyTableModel(){

		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				//initially there will be no real data
				data[i][j] = "N/A";

			}
		}
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