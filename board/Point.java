package board;

public class Point extends Vertice implements Cloneable{
	int x;
	int y;

	public NearEdges nearEdges;
	
	public Point(int x, int y) {
		super(x,y);
		this.x = x;
		this.y = y;
		nearEdges=new NearEdges();
	}
	
	public Point(int x, int y, NearEdges nearEdges) {
		super(x, y);
		this.x = x;
		this.y = y;
		this.nearEdges=nearEdges;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

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

	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", nearEdges=" + nearEdges + "]";
	}
	
	public Object clone() throws CloneNotSupportedException
    {
        Point point = (Point) clone();
 
        point.nearEdges = (NearEdges) nearEdges;
 
        return point;
    }

	
}
