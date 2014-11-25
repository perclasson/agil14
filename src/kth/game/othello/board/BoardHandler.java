package kth.game.othello.board;

import java.util.List;

public class BoardHandler {

	private GameBoard board;

	public BoardHandler(GameBoard board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	public void changeOccupantOnNodes(List<Node> nodesToBeChanged, String playerId) {
		for (Node n : nodesToBeChanged) {
			board.changeOccupantOnNode(n, playerId);
		}
	}

	public Node getNode(int x, int y) {
		return board.getNode(x, y);
	}

}
