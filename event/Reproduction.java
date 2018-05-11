package event;

import board.Individual;
import numberGen.*;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.
 *
 * Class that contains all information on Event reproduction.
 * parameter is an int associated with the evolution rate of Reproduction.
 */
public class Reproduction extends AbsEvent{
	static int parameter;
	
	public Reproduction(float time,Individual individual ){
		super(time,individual);
	}
	
	/**
	 * Implementation of the abstract method. In this case, it creates a new Individual with the method createSon (only 90% of parent's path + comfort).
	 * It will then create 3 new Event (Death, Move and Reproduction and associate them with the son.
	 * Finally, it also reschedules the father's reproduction.
	 * Events are added to the pec and the new Individual is added to the list of individuals in Simulation.
	 * All costs and lenghts of paths are updated.
	 * If the son's Event 's (Move and Reproduction) are not added to the pec (not permitted: after Death Event), then, the son is not added to individual's list 
	 * and simply discarded.
	 */
	public void simulateEvent() {
		Individual son = individual.createSon();
		Move move= new Move(time,son);
		move.time=move.getNextTime();
		Reproduction reproduction = new Reproduction(this.getNextTime(),son);		
		Death death= new Death(time,son);
		death.time=death.getNextTime();		
		Reproduction myReproduction = new Reproduction(this.getNextTime(),this.getIndividual());
		
		if(myReproduction.initCheck()) {
			sim.getEventPec().add(myReproduction);
		}
		
		if(death.initCheck()) {
			sim.getEventPec().add(death);
		}else {
			death.time=sim.getFinalInst();
			sim.getEventPec().add(death);
		}
		if(move.initCheck()) {
			sim.getEventPec().add(move);
		}	//guardar boolean do move.initCheck e do reproduction.initCheck
		if(reproduction.initCheck()) {
			sim.getEventPec().add(reproduction);
		}

		if(move.initCheck() || reproduction.initCheck()) {
			sim.individualList.add(son);
		}
		
	}
	
	/**
	 * Method used to generate the intial population for this simulation.
	 * Only generates one Indivdual at a time.
	 * It will generate initPop (int) individuals and add their respective Event 's to the pec.
	 * All Indivduals generated are a copy of a blank Individual which only has myPoint set to initialPoint.
	 */
	public void generateFirstPopulation() {
		Individual son = individual.createSon();
		Move move= new Move(time,son);
		move.time=move.getNextTime();
		Reproduction reproduction = new Reproduction(this.getNextTime(),son);
		Death death= new Death(time,son);
		death.time=death.getNextTime();
		
		if(death.initCheck()) {
			sim.getEventPec().add(death);
		}else {
			death.time=sim.getFinalInst();
			sim.getEventPec().add(death);
		}
		if(move.initCheck()) {
			sim.getEventPec().add(move);
		}	
		if(reproduction.initCheck()) {
			sim.getEventPec().add(reproduction);
		}
			//So adiciona individuo á lista se este adicionar alguma evento á pec
		if(move.initCheck() || reproduction.initCheck()) {
			sim.individualList.add(son);
		}
	}
	
	/**
	 * Implementation of the abstract method. In this case it will check if this Event is not being added after Death or after finalInst.
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
	 * Implementation of the abstract method. Generates a new time for this Reproduction based on this Individual 's comfort and Reproduction 's parameter.
	 */
	@Override
	public float getNextTime() {
		float meanValue=0;
		meanValue=(float)parameter*(1-(float)Math.log(this.individual.getComfort()));
		return (this.time+randNum.expRandom(meanValue));
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
		return "Reproduction [time=" + time + "] parameter=["+parameter+"]\n" + individual;
	}

	/**
	 * Shortened version of toString method. Used for debug.
	 */
	public String toStringMini() {
		return "Reproduction [time=" + time + "]  " + individual.myPoint.verticeToString();
	}

}
