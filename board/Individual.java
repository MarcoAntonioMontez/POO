package board;

import java.util.*;
import simulation.*;

public class Individual implements Cloneable{
	Simulation sim;
	public Point myPoint;
	public Stack<Point> path =null;
	float comfort;
	int costPath;
	int lengthPath;
	
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
	
	public Individual createSon() {
		int i=0;
		Individual individual = new Individual(this);
		
		double size = individual.lengthPath;
		size = Math.floor(size*0.9 + 0.1*this.getComfort());
		this.sim=individual.sim;

		for(i=0; i< (individual.lengthPath-size)+1;i++)
			individual.removePointPath();
		
		System.out.print("\n\nFather=" + this.toString()+"\n\nson= " + individual.toString()+"\n\n");
		
		return individual;
	}
	
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
		Point finalPoint=sim.grid.finalPoint;
		
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
			System.out.println("\nCannot remove lastPoint from Path!\n");
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
				System.out.println("Cannot add up edge!");
				return false;
			}
			newPoint=this.getNextPoint("up");
			 if(isPointInList(newPoint)) {
				 while(!path.peek().equals(newPoint)){
					 this.removePointPath();
				 }
				System.out.println("Ciclo no Path!!");
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
				System.out.println("Cannot add right edge!");
				return false;
			}
			newPoint=this.getNextPoint("right");
			 if(isPointInList(newPoint)) {
				 while(!path.peek().equals(newPoint)){
					 this.removePointPath();
				 }
				System.out.println("\nCiclo no Path!!");
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
				System.out.println("Cannot add down edge!");
				return false;
			}
			newPoint=this.getNextPoint("down");
			 if(isPointInList(newPoint)) {
				 while(!path.peek().equals(newPoint)){
					 this.removePointPath();
				 }
				System.out.println("\nCiclo no Path!!");
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
					System.out.println("Cannot add left edge!");
					return false;
				}
				newPoint=this.getNextPoint("left");
				 if(isPointInList(newPoint)) {
					 while(!path.peek().equals(newPoint)){
						 this.removePointPath();
					 }
					System.out.println("\nCiclo no Path!!");
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
	//Não acho que esta função seja aqui	
	return false;
	}
	
	
	
	private String pathString() {
		String str="";
		for(Point auxPoint: path) {
			str=str+auxPoint.toString()+"\n\n";
		}	
		return str;
	}

	@Override
	public String toString() {
		return "Individual [myPoint=" + myPoint + ",\n\n path=" + pathString() + "comfort=" + this.getComfort() + ", costPath=" + costPath +"\nlengthPath "+lengthPath
				+ "]";
	}
	
	
	
	
}
