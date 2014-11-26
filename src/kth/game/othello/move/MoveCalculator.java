package kth.game.othello.move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;

/**
 * The responsibility of this class is to calculate Othello moves on a board.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveCalculator {
	private final List<Direction> directions;
	private final Board board;

	/**
	 * Creates a new MoveCalculator object that can calculate moves on a board
	 * with different directions.
	 * 
	 * @param directions
	 *            The directions that will be used by the calculator.
	 * @param board
	 *            The board that will be used to calculate moves on.
	 */
	public MoveCalculator(List<Direction> directions, Board board) {
		this.directions = directions;
		this.board = board;
	}

	/**
	 * Creates a new MoveCalculator object that can calculate moves on a board
	 * with different directions.
	 * 
	 * @param board
	 *            The board that will be used to calculate moves on.
	 */
	public MoveCalculator(Board board) {
		this.directions = new DirectionFactory().getAllDirections();
		this.board = board;
	}

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 * 
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeId
	 *            the id of the node where the move the move should be made
	 * @return the list of nodes that will be swapped for the given move
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

	/**
	 * Returns all possible moves that the given player can make.
	 * 
	 * @param playerId
	 *            the id of the player.
	 * @return the list of all possible moves that can be made by the player.
	 */
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
	 * Returns the moves possible to the the given nodeId.
	 * 
	 * @param playerId
	 *            the id of the player making the move
	 * @param nodeId
	 *            the id of the node where the move should be made
	 * @return a list of moves possible to the node.
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
		// Store the move as visited nodes
		ArrayList<Node> visitedNodes = new ArrayList<Node>();

		// Follow the direction
		Move currentMove = null;
		Node currentNode = targetNode;

		// Add the current target node as the first visited
		visitedNodes.add(currentNode);

		// Move from the target until we meet a node of our own
		while (true) {
			currentNode = getNextNode(currentNode, direction);
			if (currentNode == null) {
				break;
			}
			boolean nodeIsOpponent = currentNode.isMarked() && !currentNode.getOccupantPlayerId().equals(playerId);
			boolean nodeIsMine = currentNode.isMarked() && currentNode.getOccupantPlayerId().equals(playerId);

			if (nodeIsOpponent) {
				visitedNodes.add(currentNode);
			} else if (visitedNodes.size() >= 2 && nodeIsMine) {
				// The move was valid and add the current node
				visitedNodes.add(currentNode);

				// The move made is the reverse of the list
				Collections.reverse(visitedNodes);
				currentMove = new Move(visitedNodes);
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
