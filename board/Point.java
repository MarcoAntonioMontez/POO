package board;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 */

/**
 *Class containing all the information about a point.
 * Point extends Vertice since Vertice only contains the coordinates of a node on the grid and Point extends Vertice by having edges.
 * int x and int y are the position of this point on the grid.
 *  nearEdges is an instance of NearEdges where all the information about the edges of this Point is stored.
 */
public class Point extends Vertice {
	int x;
	int y;
	public NearEdges nearEdges;
	
	/**
	 * Constructor. This constructor is used to create a point with no edges (ex: an obstacle).
	 * @param x coordinate.
	 * @param y coordinate.
	 */
	public Point(int x, int y) {
		super(x,y);
		this.x = x;
		this.y = y;
		nearEdges=new NearEdges(null, null, null, null);
	}
	
	/**
	 * Constructor. This constructor is used to create a point and initialize it with a set of edges.
	 * @param x coordinate.
	 * @param y coordinate.
	 * @param nearEdges.
	 */
	public Point(int x, int y, NearEdges nearEdges) {
		super(x, y);
		this.x = x;
		this.y = y;
		this.nearEdges=nearEdges;
	}

	/**
	 * Self-explanatory.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	/**
	 * Self-explanatory.
	 * @return string containing the coordinates of this point (used for observations).
	 */
	public String verticeToString() {
		return "(" + x +"," + y +")";
	}
	
	/**
	 * Self-explanatory.
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", nearEdges=" + nearEdges + "]";
	}
		
}
