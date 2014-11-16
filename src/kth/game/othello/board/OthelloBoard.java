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
	 * Initializes a Othello board given players and board order.
	 * @param playerOneId
	 * 		the first player's id
	 * @param playerTwoId
	 * 		the second player's id
	 * @param boardOrder
	 * 		the board's order, must be an even number
	 */
	public OthelloBoard(String playerOneId, String playerTwoId, int boardOrder) {
		this.boardOrder = boardOrder;
		initializeBoard(playerOneId, playerTwoId);
	}

	@Override
	public List<Node> getNodes() {
		return nodes;
	}

	/**
	 * Get node by id from board, throws {@link IllegalArgumentException} if non-existent node.
	 * 
	 * @param nodeId 
	 * 		id of the node to be retrieved
	 * @return node that was found
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
	 * @param x coordinate
	 * @param y coordinate
	 * @return Node node
	 */
	public Node getNodeByCoordinates(int x, int y) {
		return nodes.get(getNodeIndex(x, y));
	}

	/**
	 * The order of the board, the number of nodes is the square of the order.
	 * 
	 * @return the order of the board
	 */

	public int getOrder() {
		return boardOrder;
	}

	/**
	 * Changes the occupant player of nodes in the board given a list of nodes.
	 * 
	 * @param nodesToBeChanged
	 *            a list of nodes that should be changed in the board
	 * @param occupantPlayerId
	 *            the id of the new occupant
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
	 * A string representing a matrix of the board
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
	 *            the first player's id
	 * @param playerTwoId
	 *            the second player's id
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
	 * The board is internally a list and this returns the correct index for a node in matrix notation.
	 *
	 * @param the x coordinate
	 * @param the y coordinate
	 * @return The index in the list nodes, that corresponds to the coordinate (x,y) in the board
	 */
	private int getNodeIndex(int x, int y) {
		return x + (boardOrder) * y;
	}
}
