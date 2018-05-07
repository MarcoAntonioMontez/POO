package board;

public class Vertice {
	public int x;
	public int y;
	
	public Vertice(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Vertice other = (Vertice) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
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

	@Override
	public String toString() {
		return "Vertice [x=" + x + ", y=" + y + "]";
	}
	
	
}

