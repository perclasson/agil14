package kth.game.othello.board;

import java.util.List;

public class OthelloBoard implements Board {

	private List<Node> nodes;
	private static final int BOARD_SIZE = 8;

	public OthelloBoard(String playerOneId, String playerTwoId) {
		int[] startCoordinate = { BOARD_SIZE / 2 - 1, BOARD_SIZE / 2 };

		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
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
