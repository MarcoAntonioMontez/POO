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
	public LinkedList<Individual> individualList = new LinkedList<Individual>();
	Individual bestIndividual=null;
	
	public Simulation(InitObject initObject){
		this.finalInst=initObject.finalinst;
		this.initPopul=initObject.initpop;
		this.maxPopulation=initObject.maxpop;
		this.k=initObject.comfortsens;
		
		
		Point[][] pointArray=createGridMatrix(initObject);
		pointArray=updateCostZones(pointArray,initObject.specialCostZones);
		
		grid=new Grid(this,initObject,pointArray);
		pec= new EventPec(this);
		
		this.initEvents(initObject);	
	}
	
	public void simulate(){
		this.popGenesis();
		System.out.print("\n\nInitial Pec " + pec.miniToString());
		System.out.print("\n\nInitial List of Individuals " + this.individualListToString());
		AbsEvent event;
		int iteration=1;
		
		while(!pec.isNull()) {
			System.out.print("\n\n-----Iteration: " + iteration+"----\n\n");
			event=this.getNextEvent();
			event.simulateEvent();
			

			
			System.out.print("\n\nPec " + pec.miniToString());
			System.out.print("\n\nList of Individuals " + this.individualListToString());
			iteration++;
			
			if(this.individualList.size()>=this.maxPopulation) {
				System.out.print("\n\nEPIDEMIC!!!!!!!!!!!\n\n");
				epidemic();
				System.out.print("\n\nList of Individuals " + this.individualListToString());
			}
		}
		
		System.out.print("\n\nEnd of simulation\n " + pec.miniToString());
		System.out.print("\n\nPec " + pec.miniToString());
		System.out.print("\n\nList of Individuals " + this.individualListToString());		
	}
	
	public void popGenesis() {
		Individual adam = new Individual(this,this.grid.initialPoint);
		Reproduction firstReproduction = new Reproduction(0.0f,adam);
		
		for(int i=0;i<initPopul;i++) {
			firstReproduction.generateFirstPopulation();
		}	
	}
	
	public float getFinalInst() {
		return (float)this.finalInst;
	}
	
	public AbsEvent getNextEvent() {
		return pec.removeFirst();
	}
	
	public void setBestIndividual(Individual individual) {
		bestIndividual = new Individual(individual);
	}
	
	public boolean checkBestIndividual(Individual individual) {
		if(bestIndividual == null)
			return true;
		
		if(individual.myPoint.equals(grid.finalPoint)) {
			if(!bestIndividual.myPoint.equals(grid.finalPoint)) {
				return true;
			}
			if(individual.getComfort()>bestIndividual.getComfort()) {
				return true;
			}
		}
		
		if(!bestIndividual.myPoint.equals(grid.finalPoint)) {
			if(individual.getComfort()>bestIndividual.getComfort()) {
				return true;
			}
		}
		
		return false;
	}
	
	public String individualListToString(){	
		if(individualList.isEmpty()) {
			return null;
		}		
		String str="";
		for(Individual indiv:individualList) {
			 str=str + "\n" + indiv.myPoint.verticeToString() + "\t comfort " + indiv.getComfort(); 
		 }
		return str;
	}
	
	public void addIndividual(Individual individual) {
		individualList.add(individual);
	}
	
	public void epidemic() {
		 Individual aux;
		 Random random = new Random();
		
		Comparator<Individual> comfortComparator = new Comparator<Individual>(){
			@Override
			public int compare(Individual I1, Individual I2) {
				if(I1.getComfort()==I2.getComfort())
					return 0;
				else if(I1.getComfort()<I2.getComfort())
					return 1;
				else 
					return -1;
	        }
		};
		 Collections.sort(this.individualList, comfortComparator);
		 
		 LinkedList<Individual> tempList = new LinkedList<Individual>();
		 
		 int counter=0;
		 while(counter<5 && !individualList.isEmpty()) {

			 tempList.add(individualList.removeFirst());
			 counter++;
		 }
		 
		 while(!individualList.isEmpty()) {
			 aux=individualList.removeFirst();
			 if(aux.getComfort() >random.nextFloat()) {
				 tempList.addLast(aux);
			 }
			 else {
				 pec.removeEventsOfIndividual(aux);
			 }
		 }
		 individualList=tempList;
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
	
	public void removeAndUpdateList(Individual individual) {
		this.individualList.remove(individual);
		this.numIndividuals--;
	}
}
