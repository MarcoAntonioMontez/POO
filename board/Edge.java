package board;

public class Edge implements Cloneable{
	int cost;
	Vertice initialVertice;
	Vertice finalVertice;
	
	public Edge(int cost, Vertice initialVertice, Vertice finalVertice) {
		super();
		this.cost = cost;
		this.initialVertice = initialVertice;
		this.finalVertice = finalVertice;
	}
	
	public Edge(int cost, Point initialPoint, Point finalPoint) {
		super();
		this.cost = cost;
		this.initialVertice = new Vertice(initialPoint.x,initialPoint.y);
		this.finalVertice = new Vertice(finalPoint.x,finalPoint.y);;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
		return;
	}
	
	public int getCost() {
		return cost;
	}
	
	public Vertice getInicialVertice() {
		return initialVertice;
	}
	
	public Vertice getFinalVertice() {
		return finalVertice;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cost;
		result = prime * result + ((finalVertice == null) ? 0 : finalVertice.hashCode());
		result = prime * result + ((initialVertice == null) ? 0 : initialVertice.hashCode());
		return result;
	}

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

	@Override
	public String toString() {
		return "Edge [cost=" + cost + ", initialVertice=" + initialVertice + ", finalVertice=" + finalVertice + "]";
	}

	public Object clone() throws CloneNotSupportedException
	    {
	        return this.clone();
	    }
	
	
}
