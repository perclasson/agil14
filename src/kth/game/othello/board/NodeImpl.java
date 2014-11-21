package kth.game.othello.board;

import java.util.Observable;

/**
 * This is a node on the board, which containts information about it's coordinates and who holds the node.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class NodeImpl extends Observable implements Node {

	private String id;
	private String playerId;
	private int x, y;

	/**
	 * Initializes a Othello node given coordinates and a player.
	 */
	public NodeImpl(int x, int y, String playerId) {
		this.playerId = playerId;
		this.x = x;
		this.y = y;
		setId(x, y);
	}

	/**
	 * Initializes a Othello node given coordinates.
	 */
	public NodeImpl(int x, int y) {
		this.x = x;
		this.y = y;
		setId(x, y);
	}

	/**
	 * Sets the occupant player of the node
	 * 
	 * @param the
	 *            player's id
	 */
	public void setOccupantPlayerId(String playerId) {
		String oldPlayerId = this.playerId;
		if (oldPlayerId != playerId) {
			setChanged();
			this.playerId = playerId;
			notifyObservers(new NodeNotification(oldPlayerId, playerId));
		}
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getOccupantPlayerId() {
		return playerId;
	}

	@Override
	public int getXCoordinate() {
		return x;
	}

	@Override
	public int getYCoordinate() {
		return y;
	}

	@Override
	public boolean isMarked() {
		return getOccupantPlayerId() != null;
	}

	@Override
	public String toString() {
		return getOccupantPlayerId() != null ? getOccupantPlayerId() : "-1";
	}

	/**
	 * Sets the id for the node given the coordinates.
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 */
	private void setId(int x, int y) {
		this.id = "x" + x + "y" + y;
	}
}
