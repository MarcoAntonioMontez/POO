package event;

import board.Individual;

public class Observation extends AbsEvent{
	int nEvents;
	int popSize;
	boolean hitFinalPoint = false;
	Individual bestIndividual;
	
	public Observation(float time){
		super(time);
	}
	
	public void simulateEvent() {
		nEvents = sim.getIterations();
		popSize = sim.individualList.size();
		bestIndividual = sim.getBestIndividual();
		if(bestIndividual!=null) {
			if(bestIndividual.myPoint.equals(sim.grid.finalPoint)) {
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
	
	public float getNextTime() {
		return 0.0f;
	}
	
	public boolean initCheck(){			
		return false;
	}
	
	public String toStringMini() {
		return "Observation [time = " + time + "]\n";
	}
	
	int getParameter() {
		return 0;
	}
}
