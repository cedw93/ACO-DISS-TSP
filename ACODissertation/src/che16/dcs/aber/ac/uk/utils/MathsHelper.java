package che16.dcs.aber.ac.uk.utils;

public class MathsHelper {

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
	 */
	
	public static double linearInterpolateX(double x1,double x2, double mu)	{
		return(x1*(1-mu)+x2*mu);

	}
	
	public static double linearInterpolateY(double y1,double y2, double mu)	{
		return(y1*(1-mu)+y2*mu);

	}

}
