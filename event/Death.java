package event;

import board.*;
import numberGen.randNum;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 *
 * Class that contains all information on Event Death.
 * parameter is an int associated with the evolution rate of all events of type Death.
 */
public class Death extends AbsEvent{
	static int parameter;
	
	/**
	 * Constructor. Self-explanatory.
	 * @param time
	 * @param individual
	 */
	public Death(float time,Individual individual ){
		super(time,individual);
	}
	
	/**
	 * Implementation of the abstract method. In this case it simply removes the associated Individual from the LinkedList of Individual in this Simulation and updates its counter.
	 */
	public void simulateEvent() {
		sim.removeAndUpdateList(this.getIndividual());
	}

	/**
	 * Implementation of the abstract method. Death has a different formula to calculate its next time since it is inversely proportional to comfort.
	 * It also uses the public method expRandom to create an exponential distribution using meanValue as a mean value. 
	 */
	@Override
	public float getNextTime() {
		float meanValue=0;
		meanValue=(float)parameter*(1-(float)Math.log((1.0f-(float)this.individual.getComfort())));
		return this.time+randNum.expRandom(meanValue);
	}
	
	/**
	 * Implementation of the abstract method. Death only has to follow one rule to be permitted: it needs to have a lower time than finalInst.
	 */
	public boolean initCheck(){
		if( this.time>=sim.getFinalInst()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Self-explanatory.
	 * @param par
	 */
	public static void setParameter(int par) {
		parameter=par;
	}
	
	/**
	 * Self-explanatory.
	 */
	public int getParameter() {
		return parameter;
	}

	/**
	 * Self-explanatory.
	 */
	@Override
	public String toString() {
		return "Death [time=" + time + "] Parameter=["+parameter+"]\n" + individual ;
	}
	
	/**
	 * Implementation of the abstract method. Smaller version of toString used for debug.
	 */
	public String toStringMini() {
		return "Death [time=" + time + "]  " + individual.myPoint.verticeToString();
	}

}
