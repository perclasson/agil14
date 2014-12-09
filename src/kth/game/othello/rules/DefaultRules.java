package kth.game.othello.rules;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.move.Direction;
import kth.game.othello.move.Move;

/**
 * This is a representation of the default rules in a Othello game.
 * 
 */
public class DefaultRules implements Rules {
	private final List<Direction> directions;
	private final Board board;

	public DefaultRules(List<Direction> directions, Board board) {
		this.directions = directions;
		this.board = board;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		List<Move> moves = getMoves(playerId, nodeId);
		Set<Node> swappedNodes = new HashSet<Node>();
		if (moves.size() > 0) {
			for (Move move : moves) {
				swappedNodes.addAll(move.getNodes());
			}
		}
		return new ArrayList<Node>(swappedNodes);
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return getMoves(playerId, nodeId).size() > 0;
	}

	@Override
	public boolean hasValidMove(String playerId) {
		// Try a move on every node on the board
		for (Node node : board.getNodes()) {
			if (isMoveValid(playerId, node.getId())) {
				return true;
			}
		}
		return false;
	}

	private List<Move> getMoves(String playerId, String nodeId) {
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

		// Add the current target node as the first visited
		Node currentNode = targetNode;
		visitedNodes.add(currentNode);

		// Move from the target until we meet a node of our own
		while (true) {
			currentNode = getNextNode(currentNode, direction);

			if (currentNode == null) {
				// We moved out of bounds
				break;
			}

			boolean nodeIsOpponent = currentNode.isMarked() && !currentNode.getOccupantPlayerId().equals(playerId);
			boolean nodeIsMine = currentNode.isMarked() && currentNode.getOccupantPlayerId().equals(playerId);

			if (nodeIsOpponent) {
				visitedNodes.add(currentNode);
			} else if (visitedNodes.size() >= 2 && nodeIsMine) {
				return new Move(visitedNodes);
			} else {
				break;
			}
		}
		return null;
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
