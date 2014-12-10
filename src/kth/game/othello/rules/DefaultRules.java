package kth.game.othello.rules;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.move.Direction;

/**
 * This is a representation of the default rules in a Othello game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class DefaultRules implements Rules {
	private final List<Direction> directions;
	private final GameBoard board;

	/**
	 * Creates a DefaultRules object given directions and a GameBoard to play
	 * on. The moves that will be possible to make depends on the the directions
	 * and board sent in.
	 * 
	 * @param directions
	 *            The directions in which a move can be made.
	 * @param board
	 *            The board that the moves should be made on.
	 */
	public DefaultRules(List<Direction> directions, GameBoard board) {
		this.directions = directions;
		this.board = board;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		List<Node> nodesToSwap = new ArrayList<Node>();

		// Check that the target node is not already occupant
		Node targetNode = board.getNode(nodeId);
		if (targetNode.isMarked()) {
			return nodesToSwap;
		}

		// Try every direction from the target node
		for (Direction direction : directions) {
			List<Node> nodes = getNodesToSwapInDirection(direction, targetNode, playerId);
			nodesToSwap.addAll(nodes);
		}

		if (nodesToSwap.size() > 0) {
			nodesToSwap.add(targetNode);
		}

		return nodesToSwap;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return getNodesToSwap(playerId, nodeId).size() > 0;
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

	private List<Node> getNodesToSwapInDirection(Direction direction, Node targetNode, String playerId) {
		// Store the move as visited nodes
		ArrayList<Node> swaps = new ArrayList<Node>();

		// Move from the target node until we meet a node of our own
		Node currentNode = getNextNodeInDirection(targetNode, direction);
		while (currentNode != null && currentNode.isMarked()) {
			boolean nodeIsMine = currentNode.getOccupantPlayerId().equals(playerId);
			if (nodeIsMine) {
				return swaps;
			}
			swaps.add(currentNode);
			currentNode = getNextNodeInDirection(currentNode, direction);
		}
		return new ArrayList<Node>();
	}

	private Node getNextNodeInDirection(Node currentNode, Direction direction) {
		int x = currentNode.getXCoordinate() + direction.getX();
		int y = currentNode.getYCoordinate() + direction.getY();
		if (!board.hasNode(x, y)) {
			return null;
		}
		return board.getNode(x, y);
	}

}
