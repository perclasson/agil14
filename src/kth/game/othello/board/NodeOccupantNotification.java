package kth.game.othello.board;

/**
 * A notification that contains information about a node's player id.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class NodeOccupantNotification {
	public String oldOccupantPlayerId;
	public String newOccupantPlayerId;

	/**
	 * Creates a new NodeOccupantNotification that contains the previous and new
	 * occupant player id.
	 * 
	 * @param prevPlayerId
	 *            The previous player id.
	 * @param newPlayerId
	 *            The new player id.
	 */
	public NodeOccupantNotification(String prevPlayerId, String newPlayerId) {
		this.oldOccupantPlayerId = prevPlayerId;
		this.newOccupantPlayerId = newPlayerId;
	}
}
