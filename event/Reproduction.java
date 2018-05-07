package event;

import board.Individual;
import numberGen.randNum;

public class Reproduction extends AbsEvent{
	static int parameter;
	
	public Reproduction(float time,Individual individual ){
		super(time,individual);
	}
	
	public void simulateEvent() {
		
	}
	
	public boolean initCheck() {
		
		return false;
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
		return "Reproduction [time=" + time + "] Parameter=["+parameter+"]\n" + individual;
	}

}
