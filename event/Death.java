package event;

import board.*;
import numberGen.randNum;
import pec.*;
import simulation.*;

public class Death extends AbsEvent{
	static int parameter;
	
	public Death(float time,Individual individual ){
		super(time,individual);
	}
	
	public void simulateEvent() {
		
	}
	
	@Override
	public float getNextTime() {
		float meanValue=0;
		meanValue=(float)parameter*(1-(float)Math.log((1-this.individual.getComfort())));
		return randNum.expRandom(meanValue);
	}
	
	public boolean initCheck(float time){
		
		return false;
	}
	
	public static void setParameter(int par) {
		parameter=par;
	}
	
	public int getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		return "Death [time=" + time + "] Parameter=["+parameter+"]\n" + individual ;
	}
	
	public String toStringMini() {
		return "Death [time=" + time + "]  " + individual.myPoint.verticeToString();
	}

}
