package simulation;

import java.util.*;

import board.*;
import pec.*;
import test.*;
import event.*;

public class Simulation {
	int finalInst;
	int initPopul;
	int maxPopulation;
	int k;
	public Grid grid;
	int currentTime;
	int currentEvent;
	EventPec pec;
	int numIndividuals;
	LinkedList<Individual> individualList = new LinkedList<Individual>();
	Individual bestIndividual;
	
	public Simulation(InitObject initObject, int initPopul, int k){
		this.finalInst=initObject.finalinst;
		this.initPopul=initPopul;
		this.maxPopulation=initObject.maxpop;
		this.k=k;
		
		
		Point[][] pointArray=createGridMatrix(initObject);
		pointArray=updateCostZones(pointArray,initObject.specialCostZones);
		
		grid=new Grid(this,initObject,pointArray);
		pec= new EventPec(this);
		
		this.initEvents(initObject);	
	}
	
	private void initEvents(InitObject initObject) {
		AbsEvent.setSim(this);	
		Death.setParameter(initObject.death);
		Move.setParameter(initObject.move);
		Reproduction.setParameter(initObject.reproduction);
	}
	
	public EventPec getEventPec() {
		return pec;
	}
	
	public int getK() {
		return k;
	}

	private boolean isPointInList(Point point,LinkedList<Point> pointList) {
		
		for(Point auxPoint: pointList) {
			if(point.equals(auxPoint)) {
				return true;
			}
		}	
		return false;
	}
	
	private Point [][] updateCostZones(Point[][] pointArray,LinkedList<Edge> specialZoneList){
		int x1,x2,y1,y2;
		int x,y;
		Point point;
		
		for(Edge edge : specialZoneList) {
			
			x1=edge.getInicialVertice().getx();
			x2=edge.getFinalVertice().getx();
			y1=edge.getInicialVertice().gety();
			y2=edge.getFinalVertice().gety();
			
			//System.out.println("\n"+edge);
			
			for(x=x1;x<=x2;x++) {
				for(y=y1;y<=y2;y++) {
					//System.out.println("x="+x+"y="+y);
					
					point=pointArray[x-1][y-1];
					
					if(x==x1 || x==x2) {
						if(y!=y1) {
							if(point.nearEdges.upEdge!=null)
								point.nearEdges.upEdge.setCost(edge.getCost());
						}
						if(y!=y2) {
							if(point.nearEdges.downEdge!=null)
								point.nearEdges.downEdge.setCost(edge.getCost());
						}
					}
					if(y==y1 || y==y2) {
						if(x!=x1) {
							if(point.nearEdges.leftEdge!=null)
								point.nearEdges.leftEdge.setCost(edge.getCost());
						}
						if(x!=x2) {
							if(point.nearEdges.rightEdge!=null)
								point.nearEdges.rightEdge.setCost(edge.getCost());
						}
					}	
				}
			}
				
		}
		
		return pointArray.clone();
	}
	
//		System.out.println("Error.Didnt find a value for the NearEdges!!");
//		return new NearEdges();
	
	private Point [][] createGridMatrix(InitObject initObject) {
		int colsnb=initObject.colsnb;
		int rowsnb=initObject.rowsnb;
		
		Point [][]  pointArray = new Point[colsnb][rowsnb];
		
		for(int i=0;i<colsnb;i++) {
			for(int j=0;j<rowsnb;j++) {
				//Se existir um obstaculo, o point fica a null
				pointArray[i][j]=new Point(i+1,j+1,fillNearEdges(initObject,i,j));
				
				if(isPointInList(new Point(i+1,j+1),initObject.obstacles)) {
					pointArray[i][j]=new Point(i+1,j+1,new NearEdges());		
				}	
			}
		}
		return pointArray;
	}
	
	

	public int getMaxPop(){
		return maxPopulation;
	}
	
	//Por a private depois de test
	private NearEdges fillNearEdges(InitObject initObject, int _i, int _j) {
		NearEdges nearEdges;
		int colsnb=initObject.colsnb;
		int rowsnb=initObject.rowsnb;
		int cost=1;
		Edge upEdge=null;
		Edge rightEdge=null;
		Edge downEdge=null;
		Edge leftEdge=null;
		int i=_i+1;
		int j=_j+1;
		
		if(isPointInList(new Point(i,j),initObject.obstacles)) {
			nearEdges=new NearEdges(null,null,null,null);
			return nearEdges;
		}
		
		else if((i-1==0) && (j-1==0)) {
			if(!isPointInList(new Point(i+1,j),initObject.obstacles)) {
				rightEdge=new Edge(cost, new Vertice(i,j),new Vertice(i+1,j));
			}	
			if(!isPointInList(new Point(i,j+1),initObject.obstacles)) {
				downEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j+1));
			}		
					
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;
		}
		
		else if((i==colsnb) && (j-1==0)) {
			if(!isPointInList(new Point(i,j+1),initObject.obstacles)) {
				downEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j+1));
			}	
			
			if(!isPointInList(new Point(i-1,j),initObject.obstacles)) {
				leftEdge=new Edge(cost, new Vertice(i,j),new Vertice(i-1,j));
			}		
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;
		}
		
		else if((i==colsnb) && (j==rowsnb)) {
			if(!isPointInList(new Point(i,j-1),initObject.obstacles)) {
				upEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j-1));
			}	
			if(!isPointInList(new Point(i-1,j),initObject.obstacles)) {
				leftEdge=new Edge(cost, new Vertice(i,j),new Vertice(i-1,j));
			}	
					
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;
		}
		else if((i-1==0) && (j==rowsnb)) {
			if(!isPointInList(new Point(i,j-1),initObject.obstacles)) {
				upEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j-1));
			}	
			if(!isPointInList(new Point(i+1,j),initObject.obstacles)) {
				rightEdge=new Edge(cost, new Vertice(i,j),new Vertice(i+1,j));
			}	
					
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;
		}
		else if(j-1==0) {
			if(!isPointInList(new Point(i+1,j),initObject.obstacles)) {
				rightEdge=new Edge(cost, new Vertice(i,j),new Vertice(i+1,j));
			}	
			if(!isPointInList(new Point(i,j+1),initObject.obstacles)) {
				downEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j+1));
			}	
			if(!isPointInList(new Point(i-1,j),initObject.obstacles)) {
				leftEdge=new Edge(cost, new Vertice(i,j),new Vertice(i-1,j));
			}	
					
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;
		}
		
		else if(i==colsnb) {
			if(!isPointInList(new Point(i,j-1),initObject.obstacles)) {
				upEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j-1));
			}	
			if(!isPointInList(new Point(i,j+1),initObject.obstacles)) {
				downEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j+1));
			}	
			if(!isPointInList(new Point(i-1,j),initObject.obstacles)) {
				leftEdge=new Edge(cost, new Vertice(i,j),new Vertice(i-1,j));
			}	
					
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;
		}
		else if(j==rowsnb) {
			if(!isPointInList(new Point(i,j-1),initObject.obstacles)) {
				upEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j-1));
			}
			if(!isPointInList(new Point(i+1,j),initObject.obstacles)) {
				rightEdge=new Edge(cost, new Vertice(i,j),new Vertice(i+1,j));
			}	
			if(!isPointInList(new Point(i-1,j),initObject.obstacles)) {
				leftEdge=new Edge(cost, new Vertice(i,j),new Vertice(i-1,j));
			}	
					
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;
		}
		else if(i-1==0) {
			if(!isPointInList(new Point(i,j-1),initObject.obstacles)) {
				upEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j-1));
			}	
			if(!isPointInList(new Point(i+1,j),initObject.obstacles)) {
				rightEdge=new Edge(cost, new Vertice(i,j),new Vertice(i+1,j));
			}
			if(!isPointInList(new Point(i,j+1),initObject.obstacles)) {
				downEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j+1));
			}	
					
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;
		}
		else {
			if(!isPointInList(new Point(i,j-1),initObject.obstacles)) {
				upEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j-1));
			}	
			if(!isPointInList(new Point(i+1,j),initObject.obstacles)) {
				rightEdge=new Edge(cost, new Vertice(i,j),new Vertice(i+1,j));
			}
			if(!isPointInList(new Point(i,j+1),initObject.obstacles)) {
				downEdge=new Edge(cost, new Vertice(i,j),new Vertice(i,j+1));
			}	
			if(!isPointInList(new Point(i-1,j),initObject.obstacles)) {
				leftEdge=new Edge(cost, new Vertice(i,j),new Vertice(i-1,j));
			}	
			
			nearEdges=new NearEdges(upEdge,rightEdge,downEdge,leftEdge);
			return nearEdges;	
		}
	}	
}
