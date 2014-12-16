package kth.game.othello.rules;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Direction;
import kth.game.othello.board.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * The responsibility of SwapHandler is to determine which node to swap for a given player, node and board.
 */
public class SwapHandler {

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 *
	 * @param playerId the id of the player making the move
	 * @param nodeId the id of the node where the move is made
	 * @param board the board on which the action will be performed
	 * @return the list of nodes that will be swapped for the given move
	 */
	public List<Node> getNodesToSwap(String playerId, String nodeId, BoardImpl board) {
		Node node = board.getNode(nodeId);

		if (node.isMarked()) {
			return new ArrayList<Node>();
		}

		ArrayList<Node> nodesToSwap = new ArrayList<Node>();
		for (Direction direction : Direction.values()) {
			ArrayList<Node> candidates = new ArrayList<Node>();
			List<Node> nodesInDirection = board.getNodesInDirection(direction, node.getXCoordinate(),
					node.getYCoordinate());
			for (Node n : nodesInDirection) {
				if (n.getOccupantPlayerId() == null) {
					break;
				} else if (!n.getOccupantPlayerId().equals(playerId)) {
					candidates.add(n);
				} else if (n.getOccupantPlayerId().equals(playerId)) {
					nodesToSwap.addAll(candidates);
					break;
				}
			}
		}

		return nodesToSwap;
	}
}
