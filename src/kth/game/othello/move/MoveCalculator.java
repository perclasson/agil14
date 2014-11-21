package kth.game.othello.move;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

public class MoveCalculator {

	private Board board;
	private DirectionFactory directionFactory;

	public MoveCalculator(Board board) {
		this.board = board;
		this.directionFactory = new DirectionFactory();
	}

	/**
	 * For a specific node, this method return all the moves that could lead to
	 * that node. By from the specific node go in all directions and looking for
	 * a node that have the same playerId. All the nodes between the specific
	 * node the eventually findings must have the opponents playerId
	 * 
	 * @param playerId
	 * @param nodeId
	 * @return list of moves, where every move leads to a valid "placement" on
	 *         the nodeId.
	 */
	public List<Move> getMoves(String playerId, String nodeId) {
		// The valid moves
		List<Move> moves = new ArrayList<Move>();

		// Try every direction from the target node
		Node targetNode = getNode(nodeId);

		// Check that the target node is not already occupant
		if (targetNode.isMarked()) {
			return moves;
		}

		for (Direction direction : directionFactory.createAllDirections()) {
			Move move = getMoveInDirection(targetNode, direction, playerId);
			if (move != null) {
				moves.add(move);
			}
		}
		return moves;
	}

	public Move getMoveInDirection(Node targetNode, Direction direction,
			String playerId) {
		ArrayList<Node> visitedNodes = new ArrayList<Node>();

		// Follow the direction
		Move currentMove = null;
		Node currentNode = targetNode;

		while (true) {
			currentNode = getNextNode(currentNode, direction);
			if (currentNode == null) {
				break;
			}
			boolean nodeIsOpponent = currentNode.isMarked()
					&& !currentNode.getOccupantPlayerId().equals(playerId);
			boolean nodeIsMine = currentNode.isMarked()
					&& currentNode.getOccupantPlayerId().equals(playerId);

			if (nodeIsOpponent) {
				visitedNodes.add(currentNode);
			} else if (visitedNodes.size() > 0 && nodeIsMine) {
				// The move was valid
				currentMove = new Move(currentNode, targetNode, visitedNodes);
				break;
			} else {
				break;
			}
		}

		return currentMove;
	}

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 * 
	 * @param playerId
	 *            the player's id
	 * @param nodeId
	 *            the node's id
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move, for a move to a specific nodeID
	 */
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		List<Move> moves = getMoves(playerId, nodeId);
		List<Node> swappedNodes = new ArrayList<Node>();
		for (Move move : moves) {
			swappedNodes.addAll(move.getIntermediateNodes());
		}
		// Add the last node
		if (swappedNodes.size() > 0) {
			swappedNodes.add(getNode(nodeId));
		}

		return swappedNodes;
	}

	/**
	 * Retrieves all the moves that a player can do.
	 * 
	 * @param playerId
	 * @return list of moves for the playerId
	 */
	public List<Move> getMoves(String playerId) {
		List<Move> moves = new ArrayList<Move>();
		for (Node node : board.getNodes()) {
			moves.addAll(getMoves(playerId, node.getId()));
		}
		return moves;
	}

	private Node getNextNode(Node currentNode, Direction direction) {
		int x = currentNode.getXCoordinate() + direction.getX();
		int y = currentNode.getYCoordinate() + direction.getY();
		return board.getNode(x, y);
	}

	// TODO should not be here
	private Node getNode(String nodeId) {
		for (Node n : board.getNodes()) {
			if (n.getId().equals(nodeId)) {
				return n;
			}
		}
		return null;
	}

}
