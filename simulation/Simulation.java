package simulation;

import java.util.*;

import board.*;
import pec.*;
import test.*;
import event.*;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 */

/**
 * Class that contains all information on Simulation.
 * finalInst is an int which stores the instant in which the simulation should end.
 * initPopul is an int which stores the number of Individual with which the Simulation starts.
 * maxPopulation is an int which stores the maximum number of Individual for this Simulation.
 * k is an int which stores comfort sensibility.
 * iteration is an int which stores the number of Event which have occurred until the present instant.
 * grid is the associated grid.
 * pec is the associated list of Event.
 * numIndividuals is an int which stores the present number of alive Individual.
 * individualList is a LinkedList of Individual which stores all the alive Individual.
 * bestIndividual stores a copy of the best Individual until now.
 */
public class Simulation {
	int finalInst;
	int initPopul;
	int maxPopulation;
	int k;
	int iteration=0;
	public Grid grid;
	EventPec pec;
	int numIndividuals;
	public LinkedList<Individual> individualList = new LinkedList<Individual>();
	Individual bestIndividual=null;
	
	/**
	 * Constructor. Accesses InitObject to set parameters.
	 * Creates an associated grid. Updates special cost zones.
	 * Creates an empty pec.
	 * Sets the parameters for the Events.
	 * @param initObject object which contains all needed info to this simulation
	 */
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
	
	/**
	 * Method used to go through the pec, reading Events and simulating them until pec is empty.
	 * Constantly updates iteration.
	 * Constantly checks if number of Individual has not reached maxPop yet. If so, launches an epidemic.
	 */
	public void simulate(){
		this.popGenesis();
		AbsEvent event;
		iteration=1;
		
		while(!pec.isNull()) {
			event=this.getNextEvent();
			event.simulateEvent();
			
			iteration++;
			
			if(this.individualList.size()>=this.maxPopulation) {
				epidemic();
			}
		}	
	}
	
	/**
	 * Method used to populate Simulation with the initial population.
	 * Adds individuals to list of individuals and respective Events to the the pec.
	 * Creates Observations and adds them to the pec.
	 */
	public void popGenesis() {
		Individual adam = new Individual(this,this.grid.getInitialPoint());
		Reproduction firstReproduction = new Reproduction(0.0f,adam);
		Observation aux;
		
		for(int i=0;i<initPopul;i++) {
			firstReproduction.generateFirstPopulation();
		}
		
		for(float i=(finalInst/20); i<finalInst; i+=(finalInst/20)) {
			aux = new Observation(i);
			getEventPec().add(aux);
		}
		aux = new Observation(finalInst+1);
		getEventPec().add(aux);
	}
	
	/**
	 * Self-explanatory.
	 * @return int finalInst.
	 */
	public float getFinalInst() {
		return (float)this.finalInst;
	}
	
	/**
	 * Self-explanatory.
	 * @return int iteration.
	 */
	public int getIterations() {
		return iteration;
	}
	
	/**
	 * Self-explanatory.
	 * @return int numIndividuals
	 */
	public int getNumIndividuals() {
		return numIndividuals;
	}
	
	/**
	 * Self-explanatory.
	 * @return Individual bestIndividual
	 */
	public Individual getBestIndividual() {
		if(bestIndividual == null)
			return null;
		
		return new Individual(bestIndividual);
	}
	
	/**
	 * Method used to pop one Event from the pec. Always first Event in pec is popped.
	 * @return Event next event to occur
	 */
	public AbsEvent getNextEvent() {
		return pec.removeFirst();
	}
	
	/**
	 * Self-explanatory.
	 * @param individual Individual to be set as new best
	 */
	public void setBestIndividual(Individual individual) {
		bestIndividual = new Individual(individual);
	}
	
	/**
	 * Method used to check if an Individual is "better" than the current bestIndividual.
	 * @param individual Individual to be checked
	 * @return boolean, true if this is new best, flase otherwise
	 */
	public boolean checkBestIndividual(Individual individual) {
		if(bestIndividual == null)
			return true;
		
		if(individual.myPoint.equals(grid.getFinalPoint())) {
			if(!bestIndividual.myPoint.equals(grid.getFinalPoint())) {
				return true;
			}
			if(individual.getComfort()>bestIndividual.getComfort()) {
				return true;
			}
		}
		
		if(!bestIndividual.myPoint.equals(grid.getFinalPoint())) {
			if(individual.getComfort()>bestIndividual.getComfort()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Method used to concatenate the list of individuals into a string. Used for debug purposes.
	 * @return String
	 */
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
	
	/**
	 * Method used to add individuals to the list of individuals.
	 * @param individual Individual to be added to list
	 */
	public void addIndividual(Individual individual) {
		individualList.add(individual);
	}
	
	/**
	 * Method which implements epidemic (as described in project requirements)
	 * Will save the 5 individuals with most comfort. All other individuals have (comfort*100)% chance of survival.
	 * It uses a Comparator to compare comforts.
	 */
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
	
	/**
	 * Method used to set parameters of Events (Death, Move and reproduction).
	 * @param initObject object containing all necessary info for this simulation
	 */
	private void initEvents(InitObject initObject) {
		AbsEvent.setSim(this);	
		Death.setParameter(initObject.death);
		Move.setParameter(initObject.move);
		Reproduction.setParameter(initObject.reproduction);
	}
	
	/**
	 * Self-explanatory.
	 * @return pec
	 */
	public EventPec getEventPec() {
		return pec;
	}
	
	/**
	 * Self-explanatory.
	 * @return int k (comfort sensibility)
	 */
	public int getK() {
		return k;
	}
	
	/**
	 * Self-explanatory.
	 * @return int maxPopulation
	 */
	public int getMaxPop(){
		return maxPopulation;
	}
	
	/**
	 * Self-explanatory.
	 * @param i new value for iterations
	 */
	public void setIteration(int i) {
		iteration = i;
	}

	/**
	 * Method to check if a point is in a linkedList.
	 * @param point Point to be tested
	 * @param pointList List to search
	 * @return boolean, true if point is in list, false otherwise.
	 */
	private boolean isPointInList(Point point,LinkedList<Point> pointList) {
		
		for(Point auxPoint: pointList) {
			if(point.equals(auxPoint)) {
				return true;
			}
		}	
		return false;
	}
	
	/**
	 * Method used to update special cost zones.
	 * Special cost zones are defined as Edge since an Edge has a starting point, a final point and a cost.
	 * This method will then convert the two points into the Edges associated with this zone and update their cost to be equal to the special zone's cost.
	 * @param pointArray grid
	 * @param specialZoneList list of edges representing special cost zones
	 * @return Point[][] grid with updated costs
	 */
	private Point [][] updateCostZones(Point[][] pointArray,LinkedList<Edge> specialZoneList){
		int x1,x2,y1,y2;
		int x,y;
		Point point;
		
		for(Edge edge : specialZoneList) {
			
			x1=edge.getInicialVertice().getx();
			x2=edge.getFinalVertice().getx();
			y1=edge.getInicialVertice().gety();
			y2=edge.getFinalVertice().gety();
			
			
			for(x=x1;x<=x2;x++) {
				for(y=y1;y<=y2;y++) {
					
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

	/**
	 * Method used to create the grid where the simulation will happen.
	 * The grid is defined as a 2D array of point, each containing respective edges and coordinates.
	 * @param initObject object containing all necessary info for this simulation
	 * @return Point[][] grid
	 */
	private Point [][] createGridMatrix(InitObject initObject) {
		int colsnb=initObject.colsnb;
		int rowsnb=initObject.rowsnb;
		
		Point [][]  pointArray = new Point[colsnb][rowsnb];
		
		for(int i=0;i<colsnb;i++) {
			for(int j=0;j<rowsnb;j++) {
				pointArray[i][j]=new Point(i+1,j+1,fillNearEdges(initObject,i,j));
				
				if(isPointInList(new Point(i+1,j+1),initObject.obstacles)) {
					pointArray[i][j]=new Point(i+1,j+1,new NearEdges(null, null, null, null));		
				}	
			}
		}
		return pointArray;
	}

	/**
	 * Method used to fill the edges of the grid based on their position on the grid and the obstacles.
	 * For example, if a point is in position (1,1) it will only have a down edge and a right edge.
	 * Obstacles do not have any edges. They do not differ from regular points in any other way.
	 * @param initObject
	 * @param _i coordinate which needs correction (this grid starts at (1,1) not (0,0))
	 * @param _j coordinate which needs correction (this grid starts at (1,1) not (0,0))
	 * @return NearEdges, object containing edges of a Point
	 */
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
	
	/**
	 * Method used to remove a specific individual from the list of individuals.
	 * Also updates numIndividuals.
	 * @param individual Individual to be removed
	 */
	public void removeAndUpdateList(Individual individual) {
		this.individualList.remove(individual);
		this.numIndividuals--;
	}
}
