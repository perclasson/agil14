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

	public int getOrder() {
		return boardOrder;
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

}
