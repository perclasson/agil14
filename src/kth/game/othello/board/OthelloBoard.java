package kth.game.othello.board;

import java.util.List;

public class OthelloBoard implements Board {

	private List<Node> nodes;

	/**
	 * 
	 * @param playerOneId
	 * @param playerTwoId
	 * @param boardOrder
	 *            Must be an even number.
	 */
	public OthelloBoard(String playerOneId, String playerTwoId, int boardOrder) {
		int[] startCoordinate = { boardOrder / 2 - 1, boardOrder / 2 };

		for (int y = 0; y < boardOrder; y++) {
			for (int x = 0; x < boardOrder; x++) {
				boolean isMarkedByPlayerOne = ((x == startCoordinate[0] && y == startCoordinate[0]) || (x == startCoordinate[1] && y == startCoordinate[1]));
				boolean isMarkedByPlayerTwo = ((x == startCoordinate[1] && y == startCoordinate[0]) || (x == startCoordinate[0] && y == startCoordinate[1]));

				if (isMarkedByPlayerOne) {
					nodes.add(new OthelloNode(playerOneId, x, y));
				} else if (isMarkedByPlayerTwo) {
					nodes.add(new OthelloNode(playerTwoId, x, y));
				} else {
					nodes.add(new OthelloNode(null, x, y));
				}
			}
		}
	}

	@Override
	public List<Node> getNodes() {
		return nodes;
	}

}
