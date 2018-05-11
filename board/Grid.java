package board;

import simulation.*;
import test.InitObject;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 */

/**
 * Class grid to store all the information relative to the grid: size of the grid,
 * starting and finishing points for the simulation, 
 * all the points in this grid (obstacles included as points with no edges),
 * max cost of an edge on this grid.
 */
public class Grid {
	Simulation sim;
	int numCol;
	int numRow;
	int numEdges;
	Point initialPoint, finalPoint;
	int cmax;
	public Point[][] pointArray;

	/**
	 * Constructor
	 * @param sim the simulation associated to this grid,
	 * @param initObject object which contains the information (gotten from the xml) needed to initiate the simulation.
	 * @param pointArray 2D array containing all the points in this grid.
	 */
	public Grid(Simulation sim, InitObject initObject, Point[][] pointArray) {
		this.sim=sim;
		this.numCol = initObject.colsnb;
		this.numRow = initObject.rowsnb;
		this.numEdges=(2*numCol*numRow-numCol-numRow);
		
		this.pointArray=pointArray;
		this.initialPoint = pointArray[initObject.xinitial-1][initObject.yinitial-1];
		this.finalPoint = pointArray[initObject.xfinal-1][initObject.yfinal-1];	
		this.cmax = this.maxEdge(initObject);
	}
	
	/**
	 * Self-esplanatory.
	 * @return int numCol.
	 */
	public int getNumCol() {
		return numCol;
	}
	
	/**
	 * Self-explanatory.
	 * @return int numRow.
	 */
	public int getNumRow() {
		return numRow;
	}
	
	/**
	 * Self-explanatory.
	 * @return int cmax (max cost of an edge on this grid).
	 */
	public int getCmax() {
		return cmax;
	}
	
	/**
	 * Self-explanatory.
	 * @return Point initialPoint (starting point for the simulation, on this grid).
	 */
	public Point getInitialPoint() {
		return initialPoint;
	}
	
	/**
	 * Self-explanatory.
	 * @return Point finalPoint (final point for the simulation, on this grid).
	 */
	public Point getFinalPoint() {
		return finalPoint;
	}
	
	/**
	 * Method used to calculate which is the edge with the highest cost in this grid.
	 * @param initObject object containing information from xml.
	 * @return cmax, an int wihch contains the highest value of an edge
	 */
	private int maxEdge(InitObject initObject) {
		int cmax=0;
		
		for(Edge edge : initObject.specialCostZones) {
			if(edge.getCost()>cmax) {
				cmax=edge.getCost();
			}
		}
		
		return cmax;
	}	
	
	/**
	 * Method used to represent the full grid as a string.
	 * @param pointArray the 2D array containing all the points in this grid.
	 * @return str, string with all the points concatenated.
	 */
	private String arrayToString(Point[][] pointArray){
		String str="";
		for(int i=0;i<numCol;i++) {
			for(int j=0;j<numRow;j++) {
			str=str+pointArray[i][j]+"\n\n";
			}
		}
		return str;
	}

	/**
	 * Self-explanatory.
	 */
	@Override
	public String toString() {
		return "Grid [numCol=" + numCol + ", numRow=" + numRow + ", numEdges=" + numEdges + ", \ninitialPoint="
				+ initialPoint + ", \nfinalPoint=" + finalPoint + ", \n\npointArray=\n" + arrayToString(pointArray) + "]";
	}
	
}
