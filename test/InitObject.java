package test;

import java.util.*;

import board.*;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.
 * 
 * This class is used to store the input values read form the XML file, 
 * such as the final instant, initial population, maximum population,
 * comfort sensibility, number of grid columns, number of grid rows, 
 * x coordinate of the initial point, y coordinate of the initial point
 * x coordinate of the final point, y coordinate of the final point,
 * special cost zones, obstacles and the death, reproduction and move 
 * parameters.
 *  
 */

public class InitObject {
	public int finalinst;
	public int initpop;
	public int maxpop;
	public int comfortsens;
	public int colsnb;
	public int rowsnb;
	public int xinitial;
	public int yinitial;
	public int xfinal;
	public int yfinal;
	
	public LinkedList<Edge> specialCostZones = new LinkedList<Edge>();
	public LinkedList<Point> obstacles=new LinkedList<Point>();
	
	public int death;
	public int reproduction;
	public int move;

	/**
	 * Self-explanatory.
	 */
	@Override
	public String toString() {
		return "InitObject [finalinst=" + finalinst + ", initpop=" + initpop + ", maxpop=" + maxpop + ", confortsens="
				+ comfortsens + ",\n colsnb=" + colsnb + ", rowsnb=" + rowsnb + ",\n xinitial=" + xinitial + ", yinitial="
				+ yinitial + ",\n xfinal=" + xfinal + ", yfinal=" + yfinal + ",\n specialCostZones=" + specialCostZones + ",\n obstacles="
				+ obstacles + ",\n death=" + death + ", reproduction=" + reproduction + ", move=" + move + "]";
	}
	
	

}
