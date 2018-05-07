package board;

public class Obstacle extends Vertice {
	int x;
	int y;

	public Obstacle(int x, int y) {
		super(x, y);
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "Obstacle [x=" + x + ", y=" + y + "]";
	}
}
