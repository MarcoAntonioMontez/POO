package board;

public class NearEdges  implements Cloneable{
	public Edge upEdge=null;
	public Edge rightEdge=null;
	public Edge downEdge=null;
	public Edge leftEdge=null;
	
	public NearEdges(){
		
	}
	
	public boolean isNull() {
		if(upEdge==null && rightEdge==null && downEdge==null && leftEdge==null) {
			return true;
		}			
		return false;
	}
	
	public NearEdges(Edge upEdge,Edge rightEdge,Edge downEdge,Edge leftEdge){
		this.upEdge=upEdge;
		this.rightEdge=rightEdge;
		this.downEdge=downEdge;
		this.leftEdge=leftEdge;
	}
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
	@Override
	public String toString() {
		return "NearEdges [upEdge=" + upEdge + ", rightEdge=" + rightEdge + ", downEdge=" + downEdge + ", leftEdge="
				+ leftEdge + "]";
	}
	
	public Object clone() throws CloneNotSupportedException
    {
        return this.clone();
    }
}
