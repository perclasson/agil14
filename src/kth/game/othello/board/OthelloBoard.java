package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing the nodes, boardOrder and helper functions for the board.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */

public class OthelloBoard implements Board {

	private List<Node> nodes;
	private int boardOrder;

	/**
	 * @param playerOneId
	 * @param playerTwoId
	 * @param boardOrder
	 *            , Must be an even number.
	 */
	public OthelloBoard(String playerOneId, String playerTwoId, int boardOrder) {
		this.boardOrder = boardOrder;
		initializeBoard(playerOneId, playerTwoId);
	}

	/**
	 * @return List of nodes and representing "a square" in board
	 */
	@Override
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * Get node by id from board, throws {@link IllegalArgumentException} if non-existent node.
	 * 
	 * @param String
	 *            nodeId
	 * @return Node node
	 * @throws IllegalArgumentException
	 */
	public Node getNode(String nodeId) {
		for (Node node : nodes) {
			if (node.getId().equals(nodeId)) {
				return node;
			}
		}
		throw new IllegalArgumentException("There is no node with that ID.");
	}

	/**
	 * Get node by coordinates.
	 * 
	 * @param int x
	 * @param int y
	 * @return Node node
	 */
	public Node getNodeByCoordinates(int x, int y) {
		return nodes.get(getNodeIndex(x, y));
	}

	/**
	 * @return the length of the board
	 */

	public int getOrder() {
		return boardOrder;
	}

	/**
	 * Update nodes in the list so the nodes now have the occupant player as playerID
	 * 
	 * @param nodesToBeChanged
	 * @param occupantPlayerId
	 */

	public void changeOccupantOnNodes(List<Node> nodesToBeChanged, String occupantPlayerId) { // TODO naming
		// Change the swapped nodes occupant
		for (Node n : nodesToBeChanged) {
			int x = n.getXCoordinate();
			int y = n.getYCoordinate();
			nodes.set(getNodeIndex(x, y), new OthelloNode(x, y, occupantPlayerId));
		}
	}

	/**
	 * Print a matrix representing the board
	 */
	@Override
	public String toString() {
		String s = "";
		for (int y = 0; y < boardOrder; y++) {
			for (int x = 0; x < boardOrder; x++) {
				s += getNodeByCoordinates(x, y) + " \t ";
			}
			s += System.getProperty("line.separator");
		}
		return s;
	}

	/**
	 * Fill the board with nodes and where the four in the middle have a representing playerId
	 * 
	 * @param playerOneId
	 * @param playerTwoId
	 */
	private void initializeBoard(String playerOneId, String playerTwoId) {
		nodes = new ArrayList<Node>(boardOrder * boardOrder);
		// The middle index of table where the nodes should have a playedId
		int[] startCoordinate = { boardOrder / 2 - 1, boardOrder / 2 };
		for (int y = 0; y < boardOrder; y++) {
			for (int x = 0; x < boardOrder; x++) {
				boolean isMarkedByPlayerOne = ((x == startCoordinate[0] && y == startCoordinate[0]) || (x == startCoordinate[1] && y == startCoordinate[1]));
				boolean isMarkedByPlayerTwo = ((x == startCoordinate[1] && y == startCoordinate[0]) || (x == startCoordinate[0] && y == startCoordinate[1]));

				if (isMarkedByPlayerOne) {
					nodes.add(new OthelloNode(x, y, playerOneId));
				} else if (isMarkedByPlayerTwo) {
					nodes.add(new OthelloNode(x, y, playerTwoId));
				} else {
					nodes.add(new OthelloNode(x, y));
				}
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @return The index (as Int) in the list nodes, that corresponds to the coordinate (x,y) on the table
	 */
	private int getNodeIndex(int x, int y) {
		return x + (boardOrder) * y;
	}
}
