package event;

import board.Individual;
import numberGen.*;

public class Reproduction extends AbsEvent{
	static int parameter1;
	static int parameter2;
	static int parameter3;
	
	public Reproduction(float time,Individual individual ){
		super(time,individual);
	}
	
	public void simulateEvent() {
		Individual son = new Individual(super.individual);
		super.sim.individualList.add(son);

		AbsEvent move= new Move((float)parameter2*(1-(float)Math.log(this.individual.getComfort())),son);
		AbsEvent reproduction = new Reproduction(this.getNextTime(),son);
		AbsEvent death= new Death((float)parameter3*(1-(float)Math.log((1-this.individual.getComfort()))),son);
		
		time=this.getNextTime();
		this.sim.getEventPec().add(this);
		
		this.sim.getEventPec().add(move);
		this.sim.getEventPec().add(reproduction);
		this.sim.getEventPec().add(death);
	}
	
	public boolean initCheck() {
		
		return false;
	}
	
	@Override
	public float getNextTime() {
		float meanValue=0;
		meanValue=(float)parameter1*(1-(float)Math.log(this.individual.getComfort()));
		return randNum.expRandom(meanValue);
	}
	
	public static void setParameter(int par1, int par2 ,int par3) {
		parameter1=par1;
		parameter2=par2;
		parameter3=par3;
	}
	
	public int getParameter() {
		return parameter1;
	}

	@Override
	public String toString() {
		return "Reproduction [time=" + time + "] parameter1=["+parameter1+"]\n" + individual;
	}

	public String toStringMini() {
		return "Reproduction [time=" + time + "]" + individual.myPoint.toString();
	}
}
