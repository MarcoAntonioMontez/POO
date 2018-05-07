package event;

import board.*;
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
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean initCheck(){
		
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
	
	

}
