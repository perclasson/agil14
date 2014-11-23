package kth.game.othello.board;

import java.util.List;

import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;

public class BoardFactory {

	public BoardImpl createSquareBoard(int size, List<Player> players) {
		Square square = new Square();
		BoardImpl board = new BoardImpl(square.getNodes(size, players));
		return board;
	}

}
