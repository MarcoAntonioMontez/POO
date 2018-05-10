package board;

import java.util.*;
import simulation.*;

/**
 * Class used to store all the information concerning an individual 
 * 
 *sim is the associated simulation
 *myPoint is the current position of this individual
 *path is the path already traversed by this individual, it is a stack of points
 *comfort, costPath and lenghtPath are self-explanatory
 */
public class Individual implements Cloneable{
	Simulation sim;
	public Point myPoint;
	public Stack<Point>path=null;
	float comfort;
	int costPath;
	int lengthPath;
	
	
	/**
	 * Again, there are two constructors for this class: one to create the first Individual and one to clone individuals.
	 * This constructor is used to generate the first individual which will have no events and only be used to generate the initial population
	 * @param sim is the associated simulation
	 * @param myPoint current point of this individual
	 */
	public Individual(Simulation sim,Point myPoint) {
		this.sim=sim;
			
		Stack<Point> newPath = new Stack<Point>();
		newPath.add(myPoint);
		this.path=newPath;
		
		this.costPath = 0;
		this.myPoint=myPoint;
		this.lengthPath=0;
		this.comfort = this.getComfort();
	}
	
	/**
	 * This constructor is used when there are already individuals in the simulation.
	 * It will create an exact copy of the individual it receives
	 * @param individual is the individual which will be copied
	 */
	public Individual(Individual individual) {
		this.sim=individual.sim;
		Stack<Point> newPath = new Stack<Point>();
		for(Point p : individual.path) {
		    newPath.add(p);
		    this.path=newPath;
		}
		this.comfort = individual.getComfort();
		this.costPath = individual.getCostPath();
		this.myPoint=individual.myPoint;
		this.lengthPath=individual.getLengthPath();
	}
	
	/**
	 * This method is used to create a son for the reproduction event.
	 * It will not make an exact copy, it will only inherit 90% of its father's path plus comfort.
	 * It will update the length and cost of the path.
	 * It will correct myPoint for the son as well.
	 * @return son, the new individual created with some of its father's characteristics
	 */
	public Individual createSon() {
		int i=0;
		Individual individual = new Individual(this);
		
		double size = individual.lengthPath;
		size = Math.floor(size*0.9 + 0.1*this.getComfort());
		this.sim=individual.sim;

		for(i=0; i< (individual.lengthPath-size)+1;i++)
			individual.removePointPath();
		
		return individual;
	}
	
	
/**
 * Method used to identify valid edges for this individual: if this individual is in point(1,1),
 * it will only have 2 valid edges (down and right).
 * Obstacles are points with no edges, so edges leading to an obstacle are not valid.
 * @return listString, a string containing "up", "right", "down" and "left" depending if these are valid edges
 */
	public Queue<String> getValidEdges(){
		Queue<String> listString = new LinkedList<String>();
		if(this.myPoint.nearEdges.upEdge!=null) {
			listString.add("up");
		}
		if(this.myPoint.nearEdges.rightEdge!=null) {
			listString.add("right");
		}
		if(this.myPoint.nearEdges.downEdge!=null) {
			listString.add("down");
		}
		if(this.myPoint.nearEdges.leftEdge!=null) {
			listString.add("left");
		}	
		return listString;
	}
	
	/**
	 * Method used to identify number of valid edges for an individual.
	 * @return num, an int containing number of valid edges
	 */
	public int getNumEdges() {
		int num=0;
		if(this.myPoint.nearEdges.upEdge!=null) {
			num++;
		}
		if(this.myPoint.nearEdges.rightEdge!=null) {
			num++;
		}
		if(this.myPoint.nearEdges.downEdge!=null) {
			num++;
		}
		if(this.myPoint.nearEdges.leftEdge!=null) {
			num++;
		}
		
		return num;
	}
	
	public int getLengthPath() {
		return lengthPath;
	}
	
	public int getCostPath() {
		return costPath;
	}
	
	public float getComfort() {
		updateComfort();
		return this.comfort;
	}
	
	
	/**
	 * 
	 */
	private void updateComfort() {
		double a=0;
		double b=0;
		float c=0;
		
		a=(1-((double)costPath-(double)lengthPath+2)/(((double)sim.grid.getCmax()-1)*(double)lengthPath+3));
		//System.out.print("\n\n costPath "+ costPath+ " lengthPath "+ lengthPath + " cmax " + sim.grid.getCmax()+ "a=" + a );
		a=Math.pow(a,sim.getK());
				
		b=(1-((double)this.distFinalPoint())/((double)sim.grid.getNumCol()+(double)sim.grid.getNumRow()+1));
		b=Math.pow(b,sim.getK());
		
		//System.out.print("\n\n a=" + a +"\nb=" + b);
		c=(float)(a*b);
		
		this.comfort=c;
	}
	
	public int distFinalPoint() {
		int dist=0;
		Point finalPoint=sim.grid.getFinalPoint();
		
		dist=dist+ Math.abs(myPoint.getx()-finalPoint.getx()) + Math.abs(myPoint.gety()-finalPoint.gety());
		
		return dist;
	}
	
	public String direction(Point p2) {
		Point p1=myPoint;
		if(p2==null)
			return null;
		
		if(p1.x < p2.x) {
			return "right";
		}
		else if(p1.x > p2.x) {
			return "left";
		}
		else if(p1.y > p2.y) {
			return "up";
		}
		else if(p1.y < p2.y) {
			return "down";
		}
		return null;
	}
	
	public Point getNextPoint(String str) {
		if(str.equals("up")) {
			if(myPoint.nearEdges.upEdge!=null) {
				return sim.grid.pointArray[myPoint.x-1][myPoint.y-2];
			}
		}else if(str.equals("right")) {
			if(myPoint.nearEdges.rightEdge!=null) {
				return sim.grid.pointArray[myPoint.x][myPoint.y-1];
			}
		}else if(str.equals("down")) {
			if(myPoint.nearEdges.downEdge!=null) {
				return sim.grid.pointArray[myPoint.x-1][myPoint.y];
			}
		}else if(str.equals("left")) {
			if(myPoint.nearEdges.leftEdge!=null) {
				return sim.grid.pointArray[myPoint.x-2][myPoint.y-1];
			}
		}
	return null;
	}
	
	public boolean removePointPath() {
		String str="";
		if(path==null) {
			System.out.println("\nCannot remove Point from Path!\n");
			return false;
		}

		Point aux=path.pop();
		if(path.isEmpty()) {
			//System.out.println("\nCannot remove lastPoint from Path!\n");
			path.add(aux);
			return false;
		}
		Point prev=path.peek();
		str=this.direction(prev);
		if(str.equals("up")) {
			costPath=costPath - aux.nearEdges.upEdge.cost;
			myPoint=prev;
			lengthPath--;
		}
		else if(str.equals("right")) {
			costPath=costPath - aux.nearEdges.rightEdge.cost;
			myPoint=prev;
			lengthPath--;
		}
		else if(str.equals("down")) {
			costPath=costPath - aux.nearEdges.downEdge.cost;
			myPoint=prev;
			lengthPath--;
		}
		else if(str.equals("left")) {
			costPath=costPath - aux.nearEdges.leftEdge.cost;
			myPoint=prev;
			lengthPath--;
		}
		return true;
	}
	
	public boolean addPointPath(String str) {
		Point newPoint;
		
		if(str.equals("up")) {
			if(myPoint.nearEdges.upEdge==null) {
				System.out.println("\n\n\n\n\n\nCannot add up edge!");
				return false;
			}
			newPoint=this.getNextPoint("up");
			 if(isPointInList(newPoint)) {
				 while(!path.peek().equals(newPoint)){
					 this.removePointPath();
				 }
				//System.out.println("Ciclo no Path!!");
				return true;
				}
			 else {
				 costPath=costPath + this.myPoint.nearEdges.upEdge.cost;
				 path.push(newPoint);
				 lengthPath++;
				 myPoint=newPoint;
				 return true;
			 }	 	
		}
		
		else if(str.equals("right")) {
			if(myPoint.nearEdges.rightEdge==null) {
				System.out.println("\n\n\n\n\n\nCannot add right edge!");
				return false;
			}
			newPoint=this.getNextPoint("right");
			 if(isPointInList(newPoint)) {
				 while(!path.peek().equals(newPoint)){
					 this.removePointPath();
				 }
				//System.out.println("\nCiclo no Path!!");
				return true;
				}
			 else {
				 costPath=costPath + this.myPoint.nearEdges.rightEdge.cost;
				 path.push(newPoint);
				 lengthPath++;
				 myPoint=newPoint;
				 return true;
			 }	 	
		}
		
		else if(str.equals("down")) {
			if(myPoint.nearEdges.downEdge==null) {
				System.out.println("\n\n\n\n\n\nCannot add down edge!");
				return false;
			}
			newPoint=this.getNextPoint("down");
			 if(isPointInList(newPoint)) {
				 while(!path.peek().equals(newPoint)){
					 this.removePointPath();
				 }
				//System.out.println("\nCiclo no Path!!");
				return true;
				}
			 else {
				 costPath=costPath + this.myPoint.nearEdges.downEdge.cost;
				 path.push(newPoint);
				 lengthPath++;
				 myPoint=newPoint;
				 return true;
			 }	 	
		}
		 else if(str.equals("left")) {
				if(myPoint.nearEdges.leftEdge==null) {
					System.out.println("\n\n\n\n\n\nCannot add left edge!");
					return false;
				}
				newPoint=this.getNextPoint("left");
				 if(isPointInList(newPoint)) {
					 while(!path.peek().equals(newPoint)){
						 this.removePointPath();
					 }
					//System.out.println("\nCiclo no Path!!");
					return true;
					}
				 else {
					 costPath=costPath + this.myPoint.nearEdges.leftEdge.cost;
					 path.push(newPoint);
					 lengthPath++;
					 myPoint=newPoint;
					 return true;
				 }	
		 	}
		
	return false;
	}
	
	public Point pollPath() {
		return path.pop();
	}
	
	private boolean isPointInList(Point point) {
		
		for(Point auxPoint: path) {
			if(point.equals(auxPoint)) {
				return true;
			}
		}	
		return false;
	}
	
	public void die(){
	////???????????????////
	}
	
	public void updateComfort(int comfort){
		this.comfort=comfort;
	}
	
	
	public boolean checkNumIndividuals(int max_individuals){
	//N�o acho que esta fun��o seja aqui	
	return false;
	}
	
	
	
	private String pathString() {
		String str="";
		for(Point auxPoint: path) {
			str=str+auxPoint.toString()+"\n\n";
		}	
		return str;
	}
	
	public String verticePathString() {
		String str="{";
		
		for(Point auxPoint: path) {
			str=str+auxPoint.verticeToString()+",";
		}
		
		if(path!=null) {  
			str = str.substring(0, str.length() - 1);
		}
		
		return str+"}";
	}

	@Override
	public String toString() {
		return "Individual [myPoint=" + myPoint + ",\n\n path=" + pathString() + "comfort=" + this.getComfort() + ", costPath=" + costPath +"\nlengthPath "+lengthPath
				+ "]";
	}
	
	
	
	
}
