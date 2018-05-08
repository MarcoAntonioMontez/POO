package event;

import java.util.PriorityQueue;

import board.Individual;
import numberGen.*;

public class Reproduction extends AbsEvent{
	static int parameter;
	
	public Reproduction(float time,Individual individual ){
		super(time,individual);
	}
	
	public void simulateEvent() {
		Individual son = individual.createSon();
		sim.individualList.add(son);
		AbsEvent move= new Move(time,son);
		move.time=move.getNextTime();
		AbsEvent reproduction = new Reproduction(this.getNextTime(),son);
		AbsEvent death= new Death(time,son);
		death.time=death.getNextTime();
		
		time=this.getNextTime();
		if(this.initCheck())
			sim.getEventPec().add(this);
		
		sim.getEventPec().add(death);
		
		if(move.initCheck())
			sim.getEventPec().add(move);
		if(reproduction.initCheck())
			sim.getEventPec().add(reproduction);
		
	}
	
	public void generateFirstPopulation() {
		Individual son = individual.createSon();
		sim.individualList.add(son);
		AbsEvent move= new Move(time,son);
		move.time=move.getNextTime();
		AbsEvent reproduction = new Reproduction(this.getNextTime(),son);
		AbsEvent death= new Death(time,son);
		death.time=death.getNextTime();
		
		sim.getEventPec().add(death);
		
		if(move.initCheck()) {
			System.out.print("\n\nINItCHECK\n\n\n");
			sim.getEventPec().add(move);
		}
			
//		if(reproduction.initCheck())
//			sim.getEventPec().add(reproduction);
		
	}
	
	public boolean initCheck(){
		if( this.time>=sim.getFinalInst()) {
			return false;
		}
	
		System.out.print("\n\nTime event " + this.time + " death time "+ sim.getEventPec().returnDeathTime(this.getIndividual()));
		
		if(time < sim.getEventPec().returnDeathTime(this.getIndividual())) {
			return true;
		}
			
		return true;
	}
	
	@Override
	public float getNextTime() {
		float meanValue=0;
		meanValue=(float)parameter*(1-(float)Math.log(this.individual.getComfort()));
		return randNum.expRandom(meanValue);
	}
	
	public static void setParameter(int par) {
		parameter=par;
	}
	
	public int getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		return "Reproduction [time=" + time + "] parameter=["+parameter+"]\n" + individual;
	}

	public String toStringMini() {
		return "Reproduction [time=" + time + "]  " + individual.myPoint.verticeToString();
	}
}
