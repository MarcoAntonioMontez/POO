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
		Move move= new Move(time,son);
		move.time=move.getNextTime();
		Reproduction reproduction = new Reproduction(this.getNextTime(),son);
		
		Death death= new Death(time,son);
		death.time=death.getNextTime();
		
		Reproduction myReproduction = new Reproduction(this.getNextTime(),this.getIndividual());
		/////Debug values
//		move.time=4.0f;
//		reproduction.time=3.0f;
//		death.time=20.0f;
//		myReproduction.time=6.0f;
		///////
		
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
	
	public void generateFirstPopulation() {
		Individual son = individual.createSon();
		Move move= new Move(time,son);
		move.time=move.getNextTime();
		Reproduction reproduction = new Reproduction(this.getNextTime(),son);
		Death death= new Death(time,son);
		death.time=death.getNextTime();
//		move.time=2.0f;
//		reproduction.time=1.0f;
//		death.time=3.0f;
		
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
	
	public boolean initCheck(){
		//System.out.print("\n\nTime eventRepro " + time + " death time "+ sim.getEventPec().returnDeathTime(this.getIndividual()));
		if( this.time>=sim.getFinalInst()) {
			return false;
		}
		
		if(time < sim.getEventPec().returnDeathTime(this.getIndividual())) {
			return true;
		}
			
		return false;
	}
	
	@Override
	public float getNextTime() {
		float meanValue=0;
		meanValue=(float)parameter*(1-(float)Math.log(this.individual.getComfort()));
		return (this.time+randNum.expRandom(meanValue));
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
