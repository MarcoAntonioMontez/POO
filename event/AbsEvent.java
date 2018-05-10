package event;

import board.*;
import pec.*;
import simulation.*;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 *
 * Abstract class which contains common information to all Events.
 * sim is the associated simulation.
 * pec is the associated list of events.
 * individual is the associated individual.
 * time is the time at which this event will occur.
 */
public abstract class AbsEvent implements Comparable<AbsEvent>, IEvent {
	
	static Simulation sim=null;
	static EventPec pec=null;
	Individual individual;
	float time;
	
	/**
	 * Constructor in which this event is initialized with time and individual.
	 * @param time
	 * @param individual
	 */
	AbsEvent(float time,Individual individual){
		this.time=time;
		this.individual=individual;	
	}
	
	/**
	 * Constructor in which this event is only initialized with a time (used for observations).
	 */
	AbsEvent(float time){
		this.time=time;
	}
	
	/**
	 * Generic order to simulate an event.
	 */
	abstract public void simulateEvent();
	
	/**
	 * Generic "get int" to reschedule this generic event.
	 * @return
	 */
	abstract public float getNextTime();
	
	/**
	 * Generic toString but shortened for debug purposes.
	 * @return
	 */
	abstract public String toStringMini();
	
	/**
	 * Generic "get int" to return the associated parameter (death, move and reproduction parameters).
	 * @return
	 */
	abstract int getParameter();
	
	/**
	 * Generic "get object" to return associated individual.
	 * @return
	 */
	public Individual getIndividual() {
		return this.individual;
	}
	
	/**
	 * Setter for the associated simulation.
	 * @param simul
	 */
	public static void setSim(Simulation simul) {
		sim=simul;
		pec=sim.getEventPec();
	}
	
	/**
	 * Self-explanatory.
	 * @return int time.
	 */
	public float getTime() {
		return this.time;
	}
	
	/**
	 * Self-explanatory.
	 */
	@Override
	public String toString() {
		return "AbsEvent [time=" + time + "]";
	}
	
	/**
	 * Override of compareTo to compare events based on time to implement the ordered linked list that is the pec.
	 */
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
