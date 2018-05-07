package numberGen;

import java.util.*;

public class randNum {
	static Random random = new Random();
	public static float expRandom(float m){
		float next = random.nextFloat();
		return -m*(float)Math.log(1.0-next);
	}
}
