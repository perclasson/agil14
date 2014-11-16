package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;

public class OthelloBoard implements Board {

	private List<Node> nodes;
	private int boardOrder;

	/**
	 * 
	 * @param playerOneId
	 * @param playerTwoId
	 * @param boardOrder
	 *            Must be an even number.
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
	
	public int getOrder() {
		return boardOrder;
	}
	
	public void changeOccupantOnNodes(List<Node> nodesToBeChanged, String occupantPlayerId) { // TODO naming
		// Change the swapped nodes occupant
		for (Node n : nodesToBeChanged) {
			int x = n.getXCoordinate();
			int y = n.getYCoordinate();
			nodes.set(getNodeIndex(x, y), new OthelloNode(x, y, occupantPlayerId));
		}
	}
	
	private void initializeBoard(String playerOneId, String playerTwoId) {
		nodes = new ArrayList<Node>(boardOrder * boardOrder); // TODO: I think this is OK?
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

	private int getNodeIndex(int x, int y) {
		return x + (boardOrder)*y;
	}
	
}
