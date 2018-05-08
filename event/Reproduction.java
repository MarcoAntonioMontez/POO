package event;

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
		if(initCheck(time))
			sim.getEventPec().add(this);
		
		sim.getEventPec().add(death);
		if(initCheck(move.time))
			sim.getEventPec().add(move);
		if(initCheck(reproduction.time))
			sim.getEventPec().add(reproduction);
		
	}
	
	public boolean initCheck(float time) {
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
