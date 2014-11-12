package kth.game.othello.board;

import java.util.List;

public class OthelloBoard implements Board {

	private List<Node> nodes;
	private static final int BOARD_SIZE = 8;

	public OthelloBoard(String playerOneId, String playerTwoId) {
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				if (x == 3 && y == 3 || x == 4 && y == 4) {
					nodes.add(new OthelloNode(playerOneId, x, y));
				} else if (x == 4 && y == 3 || x == 3 && y == 4) {
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
