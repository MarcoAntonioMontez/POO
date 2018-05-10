package event;

import board.Individual;
import numberGen.*;
import java.util.*;


/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.  
 *
 * Class in which all information about Move Event is stored. 
 * parameter is an int associated with the evolution rate of all events of type Move.
 */
public class Move extends AbsEvent{
	static int parameter;
	
	/**
	 * Constructor. Self-explanatory.
	 * @param time
	 * @param individual
	 */
	public Move(float time,Individual individual ){
		super(time,individual);
	}
	
	/**
	 * Implementation of the abstract method. In this case, it randomly chooses a relative direction (up, right, down, left).
	 * Checks if direction is not null.
	 * Then, the Point on the other side of this edge is added to this Individual 's path.
	 * Checks if this is best Individual after move. If yes, updates it.
	 */
	public void simulateEvent() {
		String direction="";
		float result;
		float n=this.individual.getNumEdges();
		Random random=new Random();
		result=random.nextFloat();
		Queue<String> queue =this.individual.getValidEdges();
		
		for(float i=1;i<=n;i++) {
			direction=queue.poll();
			if(i*(1/n)>result) {
				break;
			}
		}
		
		time=this.getNextTime();
		
		if(this.initCheck()) {
			this.individual.addPointPath(direction);
			sim.getEventPec().add(this);
			if(sim.checkBestIndividual(this.individual))
				sim.setBestIndividual(this.individual);
		}	

	}
	
	/**
	 * Implementation of the abstract method. 
	 * It also uses the public method expRandom to create an exponential distribution using meanValue as a mean value. 
	 */
	@Override
	public float getNextTime() {
		float meanValue=0;
		meanValue=(float)parameter*(1-(float)Math.log(this.individual.getComfort()));
		return this.time+randNum.expRandom(meanValue);
	}
	
	/**
	 * Implementation of the abstract method.
	 */
	public boolean initCheck(){
		if( this.time>=sim.getFinalInst()) {
			return false;
		}
		
		if(time < sim.getEventPec().returnDeathTime(this.getIndividual())) {
			return true;
		}
			
		return false;
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
		return "Move [time=" + time + "] Parameter=["+parameter+"]\n"+ individual;
	}
	
	/**
	 * Implementation of the abstract method. Smaller version of toString used for debug.
	 */
	public String toStringMini() {
		return "Move [time=" + time + "]  " + individual.myPoint.verticeToString();
	}



}
