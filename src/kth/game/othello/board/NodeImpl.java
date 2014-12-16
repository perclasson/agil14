package kth.game.othello.board;

import java.util.Observable;
import java.util.Observer;

/**
 * An implementation of the {@link Node} interface.
 *
 */
public class NodeImpl extends Observable implements Node {

	private String occupantPlayerId;
	private int xCoordinate;
	private int yCoordinate;

	/**
	 *
	 * Create an othello node given x and y coordinates without an occupying player.
	 *
	 * @param xCoordinate x coordinate in the board.
	 * @param yCoordinate y coordinate in the board.
	 */
	public NodeImpl(int xCoordinate, int yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		occupantPlayerId = null;
	}

	/**
	 *
	 * @param xCoordinate x coordinate in the board.
	 * @param yCoordinate y coordinate in the board.
	 * @param occupantPlayerId id of the occupying player. Null if unoccupied.
	 */
	public NodeImpl(int xCoordinate, int yCoordinate, String occupantPlayerId) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		this.occupantPlayerId = occupantPlayerId;
	}

	/**
	 *
	 * Create a copy of an existing othello node.
	 *
	 * @param node node to be copied
	 */
	public NodeImpl(Node node) {
		this.xCoordinate = node.getXCoordinate();
		this.yCoordinate = node.getYCoordinate();
		occupantPlayerId = node.getOccupantPlayerId();
	}

	@Override
	public String getId() {
		return xCoordinate + "," + yCoordinate;
	}

	@Override
	public String getOccupantPlayerId() {
		return occupantPlayerId;
	}

	public void setOccupantPlayerId(String occupantPlayerId) {
		String previousPlayerId = this.occupantPlayerId;
		this.occupantPlayerId = occupantPlayerId;
		setChanged();
		notifyObservers(previousPlayerId);
	}

	@Override
	public int getXCoordinate() {
		return xCoordinate;
	}

	@Override
	public int getYCoordinate() {
		return yCoordinate;
	}

	@Override
	public boolean isMarked() {
		return occupantPlayerId != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((occupantPlayerId == null) ? 0 : occupantPlayerId.hashCode());
		result = prime * result + xCoordinate;
		result = prime * result + yCoordinate;
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
		NodeImpl other = (NodeImpl) obj;
		if (occupantPlayerId == null) {
			if (other.occupantPlayerId != null)
				return false;
		} else if (!occupantPlayerId.equals(other.occupantPlayerId))
			return false;
		if (xCoordinate != other.xCoordinate)
			return false;
		if (yCoordinate != other.yCoordinate)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return occupantPlayerId + "(" + xCoordinate + "," + yCoordinate + ")";
	}

	@Override
	public void addObserver(Observer observer) {
		super.addObserver(observer);
	}

}
