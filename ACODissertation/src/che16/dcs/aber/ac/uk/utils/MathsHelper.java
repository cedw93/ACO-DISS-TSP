package che16.dcs.aber.ac.uk.utils;

public class MathsHelper {

	public static double calculateEuclidianDistance(double x1, double y1, double x2, double y2) {
		double xDiff = x2 - x1;
		double yDiff = y2 - y1;

		//could use Math.pow but the function starts to look messi there is no need to here
		return Math.abs((Math.sqrt((xDiff * xDiff) + (yDiff * yDiff))));
	}

}
