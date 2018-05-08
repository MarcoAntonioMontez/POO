package event;

import board.*;
import pec.*;
import simulation.*;



public abstract class AbsEvent implements Comparable<AbsEvent>, IEvent {
	
	static EventPec pec=null;
	static Simulation sim=null;
	Individual individual;
	float time;
	
	AbsEvent(float time,Individual individual){
		this.time=time;
		this.individual=individual;	
	}
	
	abstract public void simulateEvent();
	
	abstract public float getNextTime();
	
	abstract public String toStringMini();
	
	abstract int getParameter();
	
	public Individual getIndividual() {
		return this.individual;
	}
	
	public static void setSim(Simulation simul) {
		sim=simul;
		pec=sim.getEventPec();
	}
	
	public float getTime() {
		return this.time;
	}
	
	@Override
	public String toString() {
		return "AbsEvent [time=" + time + "]";
	}
	

	
	@Override
	public int compareTo(AbsEvent event) {
		if(time==event.time)
			return 0;
		
		if(time<event.time)
			return -1;
		else
			return 1;
		
	}
	
}
