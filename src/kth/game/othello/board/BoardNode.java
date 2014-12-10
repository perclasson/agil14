package kth.game.othello.board;

import java.util.Observable;

import kth.game.othello.board.factory.NodeData;

/**
 * This class represents a node on the Board.
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
	 * Creates a new BoardNode object that represents a node on a board. The node will not have an occupant.
	 *
	 * @param x
	 *            The node's x coordinate position on a board.
	 * @param y
	 *            The node's y coordinate position on a board.
	 */
	public BoardNode(int x, int y) {
		this.x = x;
		this.y = y;
		setId(x, y);
	}

	/**
	 * Creates a new BoardNode object that represents a node on a board.
	 *
	 * @param x
	 *            The node's x coordinate on a board.
	 * @param y
	 *            The node's y coordinate on a board.
	 * @param playerId
	 *            The player id that occupies the node.
	 */
	public BoardNode(int x, int y, String playerId) {
		this.playerId = playerId;
		this.x = x;
		this.y = y;
		setId(x, y);
	}

	/**
	 * Creates a new BoardNode object that represents a node on a board.
	 * 
	 * @param nodeData
	 *            The NodeData to create the node with.
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
	 * Sets the occupant player of this node.
	 * 
	 * @param playerId
	 *            The occupant player id.
	 * @param notification
	 *            The notification to be sent to the observer, contain previous and current player on node.
	 */
	public void setOccupantPlayerId(String playerId, NodeOccupantNotification notification) {
		if (this.playerId != playerId) {
			setChanged();
			this.playerId = playerId;
			notifyObservers(notification);
		}
	}

	private void setId(int x, int y) {
		this.id = "x" + x + "y" + y;
	}
}
