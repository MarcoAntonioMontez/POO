package board;

import java.util.*;

import event.*;
import pec.*;
import simulation.*;
import test.InitObject;

public class Grid {
	Simulation sim;
	int numCol;
	int numRow;
	int numEdges;
	//Podemos fazer um getter
	public Point initialPoint, finalPoint;
	int cmax;
	
	public Point[][] pointArray;

	public int getNumCol() {
		return numCol;
	}
	
	public int getNumRow() {
		return numRow;
	}
	
	public int getCmax() {
		return cmax;
	}
	
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

	private int maxEdge(InitObject initObject) {
		int cmax=0;
		
		for(Edge edge : initObject.specialCostZones) {
			if(edge.getCost()>cmax) {
				cmax=edge.getCost();
			}
		}
		
		return cmax;
	}
	
	
	private String arrayToString(Point[][] pointArray){
		String str="";
		for(int i=0;i<numCol;i++) {
			for(int j=0;j<numRow;j++) {
			str=str+pointArray[i][j]+"\n\n";
			}
		}
		return str;
	}

	@Override
	public String toString() {
		return "Grid [numCol=" + numCol + ", numRow=" + numRow + ", numEdges=" + numEdges + ", \ninitialPoint="
				+ initialPoint + ", \nfinalPoint=" + finalPoint + ", \n\npointArray=\n" + arrayToString(pointArray) + "]";
	}
	
	
}
