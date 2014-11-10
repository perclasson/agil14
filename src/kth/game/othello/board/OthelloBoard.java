package kth.game.othello.board;

import java.util.List;

public class OthelloBoard implements Board {

	private List<Node> nodes;
	private static final int BOARD_SIZE = 8;

	public OthelloBoard() {
		for (int x = 0; x < BOARD_SIZE; x++) {
			for (int y = 0; y < BOARD_SIZE; y++) {
				nodes.add(new OthelloNode(null, x, y));
			}
		}
	}

	@Override
	public List<Node> getNodes() {
		return nodes;
	}

}
