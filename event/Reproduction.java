package event;

import board.Individual;

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
		// TODO Auto-generated method stub
		return 0;
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

	public String toStringMini() {
		return "Reproduction [time=" + time + "]" + individual.myPoint.toString();
	}
}
