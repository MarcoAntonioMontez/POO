package pec;

import java.util.*;

import event.*;
import simulation.*;
import board.*;

public class EventPec implements IPEC<AbsEvent>{
	
	private int numElements=0;
	private Simulation sim;
	Queue<AbsEvent> pec;
	
	public EventPec(Simulation sim){
		this.sim=sim;
		pec=new PriorityQueue<>((this.sim.getMaxPop()*3), timeComparator);
	}
	
	public boolean isNull() {
		if(this.pec.isEmpty()) {
			return true;
		}
		return false;
	}
	
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
	
	public float returnDeathTime(Individual individual) {
		// System.out.print("\n\nRemove Events of individual\n ");
		Death death = new Death(0.0f,individual);
		
		for(AbsEvent event: pec) {
//			System.out.print("\n\nEvent\n "+event.toStringMini());
//			System.out.print("\nEvent Indi "+event.getIndividual().toString());
//			System.out.print("\n\nIndi "+individual.toString());
			
			if(event.getIndividual()==individual) {
				//System.out.print("\n\nindividual==individual");
				if(death.getClass()==event.getClass()) {
					return event.getTime();
				}
			}	
		}
		return this.sim.getFinalInst();
	}
	
	public static Comparator<AbsEvent> timeComparator = new Comparator<AbsEvent>(){
		
		@Override
		public int compare(AbsEvent E1, AbsEvent E2) {
			if(E1.getTime()>=E2.getTime())
				return 1;
			else
				return -1;
        }
	};
	
	@Override
	public void add(AbsEvent event) {
		this.pec.add(event);
		numElements++;
	}
	
	@Override
	public AbsEvent removeFirst() {
		numElements--;
		return pec.poll();
	}	
	//Overload
	public void remove(AbsEvent event) {
		pec.remove(event);
		numElements--;
	}
	
	public String miniToString() {
		if(pec.isEmpty()) {
			return null;
		}		
		
		String str="";
		AbsEvent auxEvent;
		int auxNumElements=this.numElements;
		PriorityQueue<AbsEvent> auxQueue = new PriorityQueue<>((this.sim.getMaxPop()*3), timeComparator);
		
		for(AbsEvent event:pec) {
			auxQueue.add(event);
		}
		
		for(int i=0;i<pec.size();i++) {
			auxEvent=auxQueue.remove();
			str=str + "\n" + auxEvent.toStringMini()+ "\t confort " + auxEvent.getIndividual().getComfort(); 
		}
		
		this.numElements=auxNumElements;
		return "EventPec numElements=" + numElements+ str;	
	}
	
	@Override
	public String toString() {
//		String str="";
//		for(AbsEvent event : pec) {
//			str=str + event.toStringMini();
//		}
		return "EventPec numElements=" + numElements + "\n list=" + pec ;
	}
	
	public int getNumEle() {
		return numElements;
	}

}
