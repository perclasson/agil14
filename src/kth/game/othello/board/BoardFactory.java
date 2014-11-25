package kth.game.othello.board;

import java.util.List;

import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;

public class BoardFactory {

	public GameBoard createSquareBoard(int size, List<Player> players) {
		Square square = new Square();
		GameBoard board = new GameBoard(square.getNodes(size, players));
		return board;
	}

}
