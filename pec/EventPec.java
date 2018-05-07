package pec;

import java.util.*;

import event.*;
import simulation.*;

public class EventPec implements IPEC<AbsEvent>{
	
	private int numElements=0;
	private Simulation sim;
	Queue<AbsEvent> pec;
	
	public EventPec(Simulation sim){
		this.sim=sim;
		pec=new PriorityQueue<>((this.sim.getMaxPop()*3), timeComparator);
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
		pec.add(event);
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
