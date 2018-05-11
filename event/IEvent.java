package event;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 */

/**
 * Interface that permits the implementation of generic simulateEvent and initCheck.
 */
public interface IEvent{
	
	/**
	 * Interface for the method simulateEvent, which is different for every type of Event.
	 */
	void simulateEvent();
	
	/**
	 * Interface for the method initCheck which returns a boolean, true if this event has a permitted time, false otherwise.
	 * Permitted time is a time which does not violate the two following rules:
	 * 		- An Event is never placed after finalInst (if its time has a higher value than finalInst, then, its value is set to finalInst, weird if this occurs).
	 * 		- Move and Reproduction Event should never have a higher time than the Death Event associated with this Individual.
	 * @return boolean
	 */
	boolean initCheck();
}
