package kth.game.othello.move;

/**
 * This class represent a direction using (x, y) coordinates.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class Direction {
	private int x;
	private int y;

	/**
	 * Creates a new Direction object represented by (x, y) coordinates.
	 * 
	 * @param x
	 *            The x coordinate.
	 * @param y
	 *            The y coordinate.
	 */
	public Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * The x coordinate of this direction.
	 * 
	 * @return the x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * The y coordinate of this direction.
	 * 
	 * @return the y coordinate.
	 */
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
