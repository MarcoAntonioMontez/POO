package pec;

import java.util.*;

import event.*;
import simulation.*;
import board.*;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.
 */

/**
 * 
 * This class implements the Event PEC and the functions to perform operations over it.
 */

public class EventPec implements IPEC<AbsEvent>{
	
	private int numElements=0;
	private Simulation sim;
	Queue<AbsEvent> pec;
	
/**
* This method implements the Event PEC as a PriorityQueue with an inital size of three
* times the size of max population plus the 20 observations.
* @param sim
*/
	public EventPec(Simulation sim){
		this.sim=sim;
		pec=new PriorityQueue<>((this.sim.getMaxPop()*3)+20, timeComparator);
	}

/**
* Self-explanatory
**/
	public boolean isNull() {
		if(this.pec.isEmpty()) {
			return true;
		}
		return false;
	}

/**
* Removes all events from the PEC that are associated to the input Individual
* @param Individual
**/
	
	public void removeEventsOfIndividual(Individual individual) {
		// System.out.print("\n\nRemove Events of individual\n ");
		PriorityQueue<AbsEvent> auxQueue = new PriorityQueue<>((this.sim.getMaxPop()*3), timeComparator);
		AbsEvent aux;
		int pecSize=pec.size();
		
		for(int i=0;i<pecSize;i++) {
			aux=pec.remove();
			if(aux.getIndividual()!=individual) {
				auxQueue.add(aux);
			}
		}
		numElements=auxQueue.size();
		pec=auxQueue;
	}

/**
* Self-Explanatory
*/
	public float returnDeathTime(Individual individual) {
	
		Death death = new Death(0.0f,individual);
		
		for(AbsEvent event: pec) {
			
			if(event.getIndividual()==individual) {
				if(death.getClass()==event.getClass()) {
					return event.getTime();
				}
			}	
		}
		return this.sim.getFinalInst();
	}

/**
* This overrides the comparator interface to implement the time comparator between two events 
*/
	
	public static Comparator<AbsEvent> timeComparator = new Comparator<AbsEvent>(){
		
		@Override
		public int compare(AbsEvent E1, AbsEvent E2) {
			if(E1.getTime()>=E2.getTime())
				return 1;
			else
				return -1;
        }
	};
	
/**
* Adds an Event to the PEC
* @param event
*/	
	@Override
	public void add(AbsEvent event) {
		this.pec.add(event);
		numElements++;
	}

/**
* Removes an Event from the front of the PEC
* @param event
*/	
	@Override
	public AbsEvent removeFirst() {
		numElements--;
		return pec.poll();
	}	

/**
* Removes an Event from of the PEC
* @param event
*/
	public void remove(AbsEvent event) {
		pec.remove(event);
		numElements--;
	}

/**
* Prints out the event PEC
*/
	public String miniToString() {
		if(pec.isEmpty()) {
			return null;
		}		
		
		String str="";
		AbsEvent auxEvent;
		int auxNumElements=this.numElements;
		PriorityQueue<AbsEvent> auxQueue = new PriorityQueue<>((this.sim.getMaxPop()*3+20), timeComparator);
		
		for(AbsEvent event:pec) {
			auxQueue.add(event);
		}
		
		for(int i=0;i<pec.size();i++) {
			auxEvent=auxQueue.remove();
			str=str + "\n" + auxEvent.toStringMini(); //+ "\t confort " + auxEvent.getIndividual().getComfort()
		}
		
		this.numElements=auxNumElements;
		return "EventPec numElements=" + numElements+ str;	
	}
/**
*Self-Explanatory
*/
	@Override
	public String toString() {
		return "EventPec numElements=" + numElements + "\n list=" + pec ;
	}
	
	public int getNumEle() {
		return numElements;
	}

}
