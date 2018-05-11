package event;

import board.Individual;


/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 *
 * Class that contains all information on Event Observation.
 * int nEvents stores total number of events until now.
 * int popSize stores the number of alive individuals.
 * boolean hitFinalPoint stores info on if the final point has been hit
 * Individual bestIndividual stores a copy of the best individual until now for this simulation.
 */
public class Observation extends AbsEvent{
	int nEvents;
	int popSize;
	boolean hitFinalPoint = false;
	Individual bestIndividual;
	
	/**
	 * Constructor. Self-explanatory.
	 * @param time
	 */
	public Observation(float time){
		super(time);
	}
	
	/**
	 * Implementation of the abstract method. In this case, Observation accesses Simulation to get all the information it needs to print
	 */
	public void simulateEvent() {
		nEvents = sim.getIterations();
		popSize = sim.individualList.size();
		bestIndividual = sim.getBestIndividual();
		if(bestIndividual!=null) {
			if(bestIndividual.myPoint.equals(sim.grid.getFinalPoint())) {
				hitFinalPoint = true;
			}
		}
		
		
		if(time == sim.getFinalInst()+1)
			time = sim.getFinalInst();

		System.out.print("\nObservation "+Math.ceil(20*time/sim.getFinalInst()) + ":");
		System.out.print("\n\tPresent instant:\t\t\t"+time);
		System.out.print("\n\tNumber of events realized:\t\t"+nEvents);
		System.out.print("\n\tPopulation size:\t\t\t"+popSize);
		System.out.print("\n\tFinal point has been hit:\t\t"+hitFinalPoint);
		if(bestIndividual!=null) {
			System.out.print("\n\tPath of the best fit individual:\t"+bestIndividual.verticePathString());
			System.out.print("\n\tCost/Comfort:\t\t\t\t"+bestIndividual.getCostPath()+"/"+bestIndividual.getComfort()+"\n");
		}else {
			System.out.print("\n\tPath of the best fit individual:\t"+null);
			System.out.print("\n\tCost/Comfort:\t\t\t\t"+"no individual yet\n");
		}
		
		sim.setIteration(sim.getIterations()-1);
		
	}
	
	/**
	 * Abstract method, not used in this class since time is hard-coded for observations.
	 */
	public float getNextTime() {
		return 0.0f;
	}

	/**
	 * Abstract method, not used in this class since time is hard-coded for observations.
	 */
	public boolean initCheck(){			
		return false;
	}
	
	/**
	 * Shortened version of toString method. Used for debug.
	 */
	public String toStringMini() {
		return "Observation [time = " + time + "]\n";
	}
	
	/**
	 * Abstract method. Not used in this class since Observation does not have an associated parameter.
	 */
	int getParameter() {
		return 0;
	}
}
