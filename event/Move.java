package event;

import board.Individual;
import java.util.*;

public class Move extends AbsEvent{
	static int parameter;
	
	public Move(float time,Individual individual ){
		super(time,individual);
	}
	
	public void simulateEvent() {
		String direction="";
		float result;
		float n=this.individual.getNumEdges();
		Random random=new Random();
		result=random.nextFloat();
		Queue<String> queue =this.individual.getValidEdges();
		
		for(float i=1;i<=n;i++) {
			direction=queue.poll();
			if(i*(1/n)>result) {
				break;
			}
		}
//		System.out.print("\n\nQueue"+ queue);
//		
//		System.out.print("\n\nRandom" + result + " direction " + direction);	
		this.individual.addPointPath(direction);
		
		//Calcular o time do proximo event
		//Por o novo move na pec
		
		//float newTime=;
	}
	
	@Override
	public float getNextTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean initCheck() {
		
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
		return "Move [time=" + time + "] Parameter=["+parameter+"]\n"+ individual;
	}



}
