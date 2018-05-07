package test;

import java.util.*;

import board.*;

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
	@Override
	public String toString() {
		return "InitObject [finalinst=" + finalinst + ", initpop=" + initpop + ", maxpop=" + maxpop + ", confortsens="
				+ comfortsens + ",\n colsnb=" + colsnb + ", rowsnb=" + rowsnb + ",\n xinitial=" + xinitial + ", yinitial="
				+ yinitial + ",\n xfinal=" + xfinal + ", yfinal=" + yfinal + ",\n specialCostZones=" + specialCostZones + ",\n obstacles="
				+ obstacles + ",\n death=" + death + ", reproduction=" + reproduction + ", move=" + move + "]";
	}
	
	

}
