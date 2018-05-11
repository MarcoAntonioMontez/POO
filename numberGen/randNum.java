package numberGen;

import java.util.*;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.
 * 
 * This class is used to generate a value based
 * on an input parameter. For example, it is used to calculate 
 * the time of new events in the PEC, given the event parameter.
 *  
 */

public class randNum {
	static Random random = new Random();
/**
* @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.
*
* This is the method that returns a value based on the input parameter m 
* through the implementation of a stochastic mechanism.
* @param m
*/
	public static float expRandom(float m){
		float next = random.nextFloat();
		return -m*(float)Math.log(1.0-next);
	}
}
