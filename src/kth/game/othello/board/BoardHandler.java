package kth.game.othello.board;

import java.util.List;

public class BoardHandler {

	private BoardImpl board;

	public BoardHandler(BoardImpl board) {
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}

	/**
	 * Calling board.changeOccupantOnNode fore all nodes in the list
	 * 
	 * @param nodesToBeChanged
	 * @param playerId
	 */
	public void changeOccupantOnNodes(List<Node> nodesToBeChanged, String playerId) {
		for (Node n : nodesToBeChanged) {
			board.changeOccupantOnNode(n, playerId);
		}
	}

	public Node getNode(int x, int y) {
		return board.getNode(x, y);
	}

}
