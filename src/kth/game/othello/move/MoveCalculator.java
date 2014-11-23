package kth.game.othello.move;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

public class MoveCalculator {
	private final List<Direction> directions;
	private final Board board;

	public MoveCalculator(List<Direction> directions, Board board) {
		this.directions = directions;
		this.board = board;
	}

	public MoveCalculator(Board board) {
		this.directions = new DirectionFactory().getAllDirections();
		this.board = board;
	}

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 * 
	 * @param playerId
	 *            the player's id
	 * @param nodeId
	 *            the node's id
	 * @param boardHandler2
	 * @return the nodes that where swapped for this move, including the node
	 *         where the player made the move, for a move to a specific nodeID
	 */
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		List<Move> moves = getMoves(playerId, nodeId);
		List<Node> swappedNodes = new ArrayList<Node>();
		if (moves.size() > 0) {
			Move firstMove = moves.get(0);
			swappedNodes.add(firstMove.getStartNode());
			for (Move move : moves) {
				swappedNodes.addAll(move.getIntermediateNodes());
			}
			swappedNodes.add(firstMove.getEndNode());
		}
		return swappedNodes;
	}

	public List<Move> getAllPossibleMoves(String playerId) {
		List<Move> moves = new ArrayList<Move>();
		for (Node node : board.getNodes()) {
			List<Move> move = getMoves(playerId, node.getId());
			if (move.size() > 0) {
				moves.addAll(move);
			}
		}
		return moves;
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
		Node targetNode = null;
		for (Node node : board.getNodes()) {
			if (node.getId().equals(nodeId)) {
				targetNode = node;
				break;
			}
		}

		// Check that the target node is not already occupant
		if (targetNode.isMarked()) {
			return moves;
		}

		for (Direction direction : directions) {
			Move move = getMoveInDirection(targetNode, direction, playerId);
			if (move != null) {
				moves.add(move);
			}
		}

		return moves;
	}

	private Move getMoveInDirection(Node targetNode, Direction direction, String playerId) {
		ArrayList<Node> visitedNodes = new ArrayList<Node>();

		// Follow the direction
		Move currentMove = null;
		Node currentNode = targetNode;

		while (true) {
			currentNode = getNextNode(currentNode, direction);
			if (currentNode == null) {
				break;
			}
			boolean nodeIsOpponent = currentNode.isMarked() && !currentNode.getOccupantPlayerId().equals(playerId);
			boolean nodeIsMine = currentNode.isMarked() && currentNode.getOccupantPlayerId().equals(playerId);

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

	private Node getNextNode(Node currentNode, Direction direction) {
		int x = currentNode.getXCoordinate() + direction.getX();
		int y = currentNode.getYCoordinate() + direction.getY();
		try {
			return board.getNode(x, y);
		} catch (Exception e) {
			return null;
		}
	}

}
