package board;

/**
 * @author nº 78508 Marco Montez, nº 79021 Tomás Cordovil, nº 78181 João Alves.		
 * 
 * Class containing the information about the edges of a point.
 * These edges are hard set to: up, right, down and left.
 */
public class NearEdges{
	public Edge upEdge=null;
	public Edge rightEdge=null;
	public Edge downEdge=null;
	public Edge leftEdge=null;
	
	/**
	 * Constructor, self explanatory. Pointers to Edge can be null.
	 * @param upEdge.
	 * @param rightEdge.
	 * @param downEdge.
	 * @param leftEdge.
	 */
	public NearEdges(Edge upEdge,Edge rightEdge,Edge downEdge,Edge leftEdge){
		this.upEdge=upEdge;
		this.rightEdge=rightEdge;
		this.downEdge=downEdge;
		this.leftEdge=leftEdge;
	}
	
	/**
	 * Method to determine if all the edges in this object are null.
	 * @return boolean, true if edges are null, false  otherwise.
	 */
	public boolean isNull() {
		if(upEdge==null && rightEdge==null && downEdge==null && leftEdge==null) {
			return true;
		}			
		return false;
	}
	
	/**
	 * Self-explanatory.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((downEdge == null) ? 0 : downEdge.hashCode());
		result = prime * result + ((leftEdge == null) ? 0 : leftEdge.hashCode());
		result = prime * result + ((rightEdge == null) ? 0 : rightEdge.hashCode());
		result = prime * result + ((upEdge == null) ? 0 : upEdge.hashCode());
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
		NearEdges other = (NearEdges) obj;
		if (downEdge == null) {
			if (other.downEdge != null)
				return false;
		} else if (!downEdge.equals(other.downEdge))
			return false;
		if (leftEdge == null) {
			if (other.leftEdge != null)
				return false;
		} else if (!leftEdge.equals(other.leftEdge))
			return false;
		if (rightEdge == null) {
			if (other.rightEdge != null)
				return false;
		} else if (!rightEdge.equals(other.rightEdge))
			return false;
		if (upEdge == null) {
			if (other.upEdge != null)
				return false;
		} else if (!upEdge.equals(other.upEdge))
			return false;
		return true;
	}
	
	/**
	 * Self-explanatory.
	 */
	@Override
	public String toString() {
		return "NearEdges [upEdge=" + upEdge + ", rightEdge=" + rightEdge + ", downEdge=" + downEdge + ", leftEdge="
				+ leftEdge + "]";
	}
	
}
