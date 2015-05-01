package che16.dcs.aber.ac.uk.view;


import javax.swing.table.AbstractTableModel;

/**
 * This Class is used to model the underlying table model represented in the instance of
 * {@link CityDetailView}. This has been abstracted into its own Class to reduce the coupling
 * between the view and data.
 * @author Christopher Edwards
 *
 */

public class MyTableModel extends AbstractTableModel{

	private final String[] COLUMNNAMES = {"City Index", "No. of Agents"}; 

	//26 because the max number of cities is 25, the +1 for the 'total' row
	public final int ROWS = 26;
	public final int COLS = 2;

	public Object[][] data = new Object[ROWS][COLS];

	public MyTableModel(){

		//-1 because the final row will be a total
		for(int i = 0; i < (ROWS-1); i++){
			for(int j = 0; j < COLS; j++){
				//initially there will be no real data
				data[i][j] = "N/A";

			}
		}

		data[ROWS-1][0] = "Total";
		data[ROWS-1][1] = "N/A";
	}

	/**
	 * @return the nuber of columns
	 */
	public int getColumnCount() {
		return COLUMNNAMES.length;
	}

	/**
	 * @return the number of rows
	 */
	public int getRowCount() {
		return data.length;
	}

	/**
	 * @param col the colum index
	 * @return the name of the specified column index
	 */
	
	public String getColumnName(int col) {
		return COLUMNNAMES[col];
	}

	/**
	 * Returns the value at the specified table location.
	 * 
	 * @param row the row index
	 * @param col the column index
	 * @return the value at said indexes
	 */
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