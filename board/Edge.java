package board;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 * 
 * This class is used to represent egdes in our grid.
 *  These edges have 3 attributes: its cost, and the two points which define the position of the Edge.
 */
public class Edge {
	int cost;
	Vertice initialVertice;
	Vertice finalVertice;
	
	/**
	 * There are two constructors, one is used to build the grid and the other is used to update the special cost zones respectively.
	 * This one is for building the grid.
	 * @param cost int which represents the cost of this specific edge.
	 * @param initialVertice vertice representing the starting point of the edge.
	 * @param finalVertice  vertice representing the final point of the edge.
	 */	
	public Edge(int cost, Vertice initialVertice, Vertice finalVertice) {
		super();
		this.cost = cost;
		this.initialVertice = initialVertice;
		this.finalVertice = finalVertice;
	}
	
	/**
	 * This one is for updating the special cost zones.
	 * @param cost int which represents the cost of this specific edge.
	 * @param initialPoint point representing the starting point of the edge.
	 * @param finalPoint  point representing the final point of the edge.
	 */
	public Edge(int cost, Point initialPoint, Point finalPoint) {
		super();
		this.cost = cost;
		this.initialVertice = new Vertice(initialPoint.x,initialPoint.y);
		this.finalVertice = new Vertice(finalPoint.x,finalPoint.y);;
	}
		
	/**
	 * 	Self-explanatory.
	 * @param cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
		return;
	}
	
	/**
	 * Self-explanatory.
	 * @return cost (int)
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Self-explanatory.
	 * @return Vertice inicialVertice.
	 */
	public Vertice getInicialVertice() {
		return initialVertice;
	}
	
	/**
	 * Self-explanatory.
	 * @return Vertice finalVertice.
	 */
	public Vertice getFinalVertice() {
		return finalVertice;
	}
	/**
	 * Self-explanatory.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cost;
		result = prime * result + ((finalVertice == null) ? 0 : finalVertice.hashCode());
		result = prime * result + ((initialVertice == null) ? 0 : initialVertice.hashCode());
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
		Edge other = (Edge) obj;
		if (cost != other.cost)
			return false;
		if (finalVertice == null) {
			if (other.finalVertice != null)
				return false;
		} else if (!finalVertice.equals(other.finalVertice))
			return false;
		if (initialVertice == null) {
			if (other.initialVertice != null)
				return false;
		} else if (!initialVertice.equals(other.initialVertice))
			return false;
		return true;
	}

	/**
	 * Self-explanatory.
	 */
	@Override
	public String toString() {
		return "Edge [cost=" + cost + ", initialVertice=" + initialVertice + ", finalVertice=" + finalVertice + "]";
	}
	
}
