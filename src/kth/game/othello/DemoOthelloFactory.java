package kth.game.othello;

import java.util.Random;

import kth.game.othello.board.OthelloBoard;
import kth.game.othello.player.OthelloPlayer;
import kth.game.othello.player.Player.Type;

/**
 * This class consists of functions to create Othello games.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class DemoOthelloFactory implements OthelloFactory {
	private static final int BOARD_ORDER = 8;

	@Override
	public Othello createComputerGame() {
		OthelloPlayer black = new OthelloPlayer(Type.COMPUTER, "black", "0");
		OthelloPlayer white = new OthelloPlayer(Type.COMPUTER, "white", "1");
		OthelloBoard board = new OthelloBoard(black.getId(), white.getId(), BOARD_ORDER);
		Random random = new Random();
		PlayerWrapper playerWrapper = new PlayerWrapper(black, white, random);
		MoveHandler moveHandler = new MoveHandler(board, playerWrapper, random);
		return new DemoOthello(board, playerWrapper, moveHandler, random);
	}

	@Override
	public Othello createHumanGame() {
		OthelloPlayer black = new OthelloPlayer(Type.HUMAN, "black", "0");
		OthelloPlayer white = new OthelloPlayer(Type.HUMAN, "white", "1");
		OthelloBoard board = new OthelloBoard(black.getId(), white.getId(), BOARD_ORDER);
		Random random = new Random();
		PlayerWrapper playerWrapper = new PlayerWrapper(black, white, random);
		MoveHandler moveHandler = new MoveHandler(board, playerWrapper, random);
		return new DemoOthello(board, playerWrapper, moveHandler, random);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		OthelloPlayer black = new OthelloPlayer(Type.COMPUTER, "black", "0");
		OthelloPlayer white = new OthelloPlayer(Type.HUMAN, "white", "1");
		OthelloBoard board = new OthelloBoard(black.getId(), white.getId(), BOARD_ORDER);
		Random random = new Random();
		PlayerWrapper playerWrapper = new PlayerWrapper(black, white, random);
		MoveHandler moveHandler = new MoveHandler(board, playerWrapper, random);
		return new DemoOthello(board, playerWrapper, moveHandler, random);
	}

}
