package kth.game.othello;

import kth.game.othello.board.Board;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.player.OthelloPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;

public class DemoOthelloFactory implements OthelloFactory {

	@Override
	public Othello createComputerGame() {
		Player black = new OthelloPlayer(Type.COMPUTER, "black", "0");
		Player white = new OthelloPlayer(Type.COMPUTER, "white", "1");
		Board board = new OthelloBoard(black.getId(), white.getId());
		return new DemoOthello(board, black, white);
	}

	@Override
	public Othello createHumanGame() {
		Player black = new OthelloPlayer(Type.HUMAN, "black", "0");
		Player white = new OthelloPlayer(Type.HUMAN, "white", "1");
		Board board = new OthelloBoard(black.getId(), white.getId());
		return new DemoOthello(board, black, white);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		Player black = new OthelloPlayer(Type.COMPUTER, "black", "0");
		Player white = new OthelloPlayer(Type.HUMAN, "white", "1");
		Board board = new OthelloBoard(black.getId(), white.getId());
		return new DemoOthello(board, black, white);
	}

}
