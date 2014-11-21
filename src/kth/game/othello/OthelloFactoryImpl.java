package kth.game.othello;

import java.util.List;
import java.util.Random;
import java.util.Set;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.player.Player;
import kth.game.othello.move.Handler;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.Player.Type;

/**
 * This class consists of functions to create Othello games.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class OthelloFactoryImpl implements OthelloFactory {
	private static final int BOARD_ORDER = 8;

	@Override
	public Othello createComputerGame() {
		PlayerImpl black = new PlayerImpl(Type.COMPUTER, "black", "0");
		PlayerImpl white = new PlayerImpl(Type.COMPUTER, "white", "1");
		BoardImpl board = new BoardImpl(black.getId(), white.getId(), BOARD_ORDER);
		Random random = new Random();
		PlayerWrapper playerWrapper = new PlayerWrapper(black, white, random);
		Handler moveHandler = new Handler(board, playerWrapper);
		return new OthelloImpl(board, playerWrapper, moveHandler);
	}

	@Override
	public Othello createHumanGame() {
		PlayerImpl black = new PlayerImpl(Type.HUMAN, "black", "0");
		PlayerImpl white = new PlayerImpl(Type.HUMAN, "white", "1");
		BoardImpl board = new BoardImpl(black.getId(), white.getId(), BOARD_ORDER);
		Random random = new Random();
		PlayerWrapper playerWrapper = new PlayerWrapper(black, white, random);
		Handler moveHandler = new Handler(board, playerWrapper);
		return new OthelloImpl(board, playerWrapper, moveHandler);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		PlayerImpl black = new PlayerImpl(Type.COMPUTER, "black", "0");
		PlayerImpl white = new PlayerImpl(Type.HUMAN, "white", "1");
		BoardImpl board = new BoardImpl(black.getId(), white.getId(), BOARD_ORDER);
		Random random = new Random();
		PlayerWrapper playerWrapper = new PlayerWrapper(black, white, random);
		Handler moveHandler = new Handler(board, playerWrapper);
		return new OthelloImpl(board, playerWrapper, moveHandler);
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		return null;
	}

}
