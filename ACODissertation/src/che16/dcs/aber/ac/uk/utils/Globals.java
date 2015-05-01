package che16.dcs.aber.ac.uk.utils;

/**
 * This Class is used to contain methods which are used in several locations throughout the application or
 * variables which require application wide accessibility.
 * @author Christopher Edwards
 *
 */
public class Globals {
	
	public static final int REALESE = 0;
	public static final int TESTMODE = 1;
	public static int MODE = 0;
	
	/**
	 * Calculate the Euclidean distance between any two locations
	 * @param x1 the x coordinate for the start location
	 * @param y1 the y coordinate for the start location
	 * @param x2 the x coordinate for the end location
	 * @param y2 the y coordinate for the end location
	 * @return the euclidean distance between the start and end location
	 */
	public static double calculateEuclidianDistance(double x1, double y1, double x2, double y2) {
		double xDiff = x2 - x1;
		double yDiff = y2 - y1;

		//could use Math.pow but the function starts to look messi there is no need to here
		return Math.abs((Math.sqrt((xDiff * xDiff) + (yDiff * yDiff))));
	}

	/*
	 * Both linerInterpolate functions (X and Y) are provided almost 'as is' here:
	 * http://paulbourke.net/miscellaneous/interpolation/
	 * These functions are used to paint the ant's movements between cities. 
	 * 
	 * The x1/2 (or y1/2) are the X and Y co-ordinates of the start/end locations, and mu is the 
	 * destination on this line which you want the value for. a mu of 0.2 will return a location approximately 1/5 
	 * the line length.
	 * 
	 * These two are identical functions. They are two separate functions just to enable easier debugging and the like.
	 * You could use linerInterpolateX to calculate the Y value, the inverse is true also.
	 */

	/**
	 * Retrieve the x coordinate for the specified point on a line 
	 * @param x1 the start x coordinate of the line
	 * @param x2 the end x coordinate of the line
	 * @param mu the distance along the line you wish to find
	 * @return the x coordinate for the specified location
	 */
	
	public static double linearInterpolateX(double x1,double x2, double mu)	{
		return(x1*(1-mu)+x2*mu);

	}

	/**
	 * Retrieve the y coordinate for the specified point on a line 
	 * @param y1 the start y coordinate of the line
	 * @param y2 the end y coordinate of the line
	 * @param mu the distance along the line you wish to find
	 * @return the y coordinate for the specified location
	 */
		
	public static double linearInterpolateY(double y1,double y2, double mu)	{
		return(y1*(1-mu)+y2*mu);

	}
	
	/**
	 * Set the application to test mode.
	 */
	public static void setTestMode(){
		MODE = TESTMODE;
	}
	
	/**
	 * 
	 * @return the current mode
	 */
	public static int getMode(){
		return MODE;
	}
		
}
