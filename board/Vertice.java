package board;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 */

/**
 * Class containing all the information about Vertice, which is a generic node of the grid.
 * This class only contains the coordinates of the node.
 */
public class Vertice {
	int x;
	int y;
	
	/**
	 * Self-Explanatory.
	 * @param x coordinate.
	 * @param y coordinate.
	 */
	public Vertice(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Self-explanatory.
	 * @return x coordinate.
	 */
	public int getx() {
		return x;
	}
	
	/**
	 * Self-explanatory.
	 * @return y coordinate.
	 */
	public int gety() {
		return y;
	}
	
	/**
	 * Self-explanatory.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/**
	 * Self-explanatory.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertice other = (Vertice) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	/**
	 * Self-explanatory.
	 * @param obj object to be tested
	 * @return boolean
	 */
	public boolean equalsPoint(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Vertice other = (Vertice) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * Self-explanatory.
	 */
	@Override
	public String toString() {
		return "Vertice [x=" + x + ", y=" + y + "]";
	}
	
}

