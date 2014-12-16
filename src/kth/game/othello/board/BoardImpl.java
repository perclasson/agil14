package kth.game.othello.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * An implementation of the {@link Board} interface.
 */
public class BoardImpl implements Board {
	private List<Node> nodes;

	/**
	 *
	 * @param nodes a list of nodes for the board.
	 */
	public BoardImpl(List<Node> nodes) {
		this.nodes = nodes;
	}

	/**
	 * Copy constructor. The constructor will also create copies for the nodes.
	 * 
	 * @param board the board to be copied.
	 */
	public BoardImpl(Board board) {
		nodes = new ArrayList<Node>();
		for (Node node : board.getNodes()) {
			nodes.add(new NodeImpl(node));
		}
	}

	@Override
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * Returns the node for the given nodeId, null if not found.
	 *
	 * @param nodeId the nodeId of the desired node.
	 * @return the node with the given nodeId, null if not found.
	 */
	public Node getNode(String nodeId) {
		for (Node node : nodes) {
			if (node.getId().equals(nodeId)) {
				return node;
			}
		}
		throw new IllegalArgumentException("No such node in board");
	}

	/**
	 * Set the occupant player id for a node.
	 * 
	 * @param nodeId the id of the node.
	 * @param playerId the player id to set.
	 */
	public void setOccupantPlayerId(String nodeId, String playerId) {
		Node node = getNode(nodeId);
		if (node == null) {
			return;
		}
		((NodeImpl) node).setOccupantPlayerId(playerId);
	}

	/**
	 * Returns a list of all nodes in the direction that is indicated from the position given by the coordinates.
	 *
	 * @param direction direction in which to retrieve nodes
	 * @param x the x-coordinate of starting position
	 * @param y the y-coordinate of starting position
	 * @return a list of nodes in the given direction
	 */
	public List<Node> getNodesInDirection(Direction direction, int x, int y) {
		ArrayList<Node> nodesInDirection = new ArrayList<Node>();

		Node node;
		while ((node = getNodeInDirection(x, y, direction)) != null) {
			nodesInDirection.add(node);
			x = node.getXCoordinate();
			y = node.getYCoordinate();
		}

		return nodesInDirection;
	}

	/**
	 * Returns the first node in the given direction from the start index.
	 *
	 * @param x the x-coordinate of the reference node
	 * @param y the y-coordinate of the reference node
	 * @param direction direction in which to retrieve the node
	 * @return the node in that direction. returns null if it does not exist
	 */
	private Node getNodeInDirection(int x, int y, Direction direction) {
		switch (direction) {
		case DOWN:
			return hasNode(x, y + 1) ? getNode(x, y + 1) : null;
		case DOWN_LEFT:
			return hasNode(x - 1, y + 1) ? getNode(x - 1, y + 1) : null;
		case DOWN_RIGHT:
			return hasNode(x + 1, y + 1) ? getNode(x + 1, y + 1) : null;
		case LEFT:
			return hasNode(x - 1, y) ? getNode(x - 1, y) : null;
		case RIGHT:
			return hasNode(x + 1, y) ? getNode(x + 1, y) : null;
		case UP:
			return hasNode(x, y - 1) ? getNode(x, y - 1) : null;
		case UP_LEFT:
			return hasNode(x - 1, y - 1) ? getNode(x - 1, y - 1) : null;
		case UP_RIGHT:
			return hasNode(x + 1, y - 1) ? getNode(x + 1, y - 1) : null;
		default:
			return null;
		}
	}

	@Override
	public Node getNode(int x, int y) {
		for (Node node : nodes) {
			if (node.getXCoordinate() == x && node.getYCoordinate() == y) {
				return node;
			}
		}
		throw new IllegalArgumentException("No such node in board");
	}

	@Override
	public int getMaxX() {
		int maxX = 0;
		for (Node node : nodes) {
			if (node.getXCoordinate() > maxX)
				maxX = node.getXCoordinate();
		}
		return maxX;
	}

	@Override
	public int getMaxY() {
		int maxY = 0;
		for (Node node : nodes) {
			if (node.getYCoordinate() > maxY)
				maxY = node.getYCoordinate();
		}
		return maxY;
	}

	@Override
	public boolean hasNode(int x, int y) {
		for (Node node : nodes) {
			if (node.getXCoordinate() == x && node.getYCoordinate() == y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Only works for max 5 players. A player may change color after a turn. For debug purposes only.
	 *
	 * @return a string representation of the board
	 */
	@Override
	public String toString() {
		HashMap<String, Character> playerPieces = new HashMap<String, Character>();
		// x - going right, y - going down
		char[][] board = new char[getMaxX() + 1][getMaxY() + 1];
		Stack<Character> pieces = new Stack<Character>();
		pieces.push('$');
		pieces.push('@');
		pieces.push('#');
		pieces.push('O');
		pieces.push('X');

		char notMarked = '□';
		char outOfBounds = '■';

		// Initialize out of Bounds
		for (char[] row : board) {
			Arrays.fill(row, outOfBounds);
		}

		for (Node node : nodes) {
			int x = node.getXCoordinate();
			int y = node.getYCoordinate();
			if (node.isMarked()) {
				String playerId = node.getOccupantPlayerId();
				if (playerPieces.containsKey(playerId)) {
					board[x][y] = playerPieces.get(playerId);
				} else {
					playerPieces.put(playerId, pieces.pop());
					board[x][y] = playerPieces.get(playerId);
				}
			} else {
				board[x][y] = notMarked;
			}
		}

		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < board[0].length; y++) {
			for (int x = 0; x < board.length; x++) {
				builder.append(board[x][y]);
				builder.append(' ');
			}
			builder.append("\n");
		}

		return builder.toString();
	}
}
