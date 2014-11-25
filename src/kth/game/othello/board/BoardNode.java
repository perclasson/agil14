package kth.game.othello.board;

import java.util.Observable;

import kth.game.othello.board.factory.NodeData;

/**
 * This is a node on the board, which containts information about it's coordinates and who holds the node.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class BoardNode extends Observable implements Node {

	private String id;
	private String playerId;
	private int x, y;

	/**
	 * Initializes a Othello node given coordinates and a player.
	 */
	public BoardNode(int x, int y, String playerId) {
		this.playerId = playerId;
		this.x = x;
		this.y = y;
		setId(x, y);
	}

	/**
	 * Initializes a Othello node given coordinates.
	 */
	public BoardNode(int x, int y) {
		this.x = x;
		this.y = y;
		setId(x, y);
	}

	/**
	 * Initializes a Othello node given nodeData
	 */

	public BoardNode(NodeData nodeData) {
		this.x = nodeData.getXCoordinate();
		this.y = nodeData.getYCoordinate();
		this.playerId = nodeData.getOccupantPlayerId();
		setId(this.x, this.y);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getOccupantPlayerId() {
		return playerId;
	}

	/**
	 * Sets the occupant player of the node
	 * 
	 * @param playerID
	 *            one player's id
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
		return "(x: " + x + ", y: " + y + ", playerId: "
				+ (getOccupantPlayerId() != null ? getOccupantPlayerId() : "n/a") + ")";
	}

	/**
	 * Sets the id for the node given the coordinates.
	 * 
	 * @param x
	 *            X-coordinate
	 * @param y
	 *            Y-coordinate
	 */
	private void setId(int x, int y) {
		this.id = "x" + x + "y" + y;
	}
}
