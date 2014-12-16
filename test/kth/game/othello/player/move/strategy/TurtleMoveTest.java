package kth.game.othello.player.move.strategy;

import java.util.ArrayList;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.rules.MoveValidator;
import kth.game.othello.rules.SwapHandler;
import kth.game.othello.rules.Rules;
import kth.game.othello.rules.RulesImpl;

import org.junit.Assert;
import org.junit.Test;

public class TurtleMoveTest {

	private BoardImpl get8x8EmptyBoard() {
		ArrayList<Node> nodes = new ArrayList<Node>(64);
		for (int i = 0; i < 64; i++) {
			nodes.add(new NodeImpl((i % 8), i / 8));
		}
		return new BoardImpl(nodes);
	}

	private BoardImpl initiatePositionsOnBoard(BoardImpl board, Player black, int[] blackPositions, Player white,
			int[] whitePositions) {
		// place black pieces
		for (int position : blackPositions) {
			Node node = board.getNodes().get(position);
			board.setOccupantPlayerId(node.getId(), black.getId());
		}

		// place white pieces
		for (int position : whitePositions) {
			Node node = board.getNodes().get(position);
			board.setOccupantPlayerId(node.getId(), white.getId());
		}

		// Uncomment to see initialized board
		// System.out.print(board.toString());
		return board;
	}

	@Test
	public void testTurtleMoveStrategyStrait() {
		int[] whitePositions = { 2, 9 };
		int[] blackPositions = { 8, 10, 11, 12 };

		BoardImpl board = get8x8EmptyBoard();
		TurtleMove moveStrategy = new TurtleMove();

		Player black = PlayerImpl.getComputerPlayer("black", "black", moveStrategy);
		Player white = PlayerImpl.getComputerPlayer("white", "white", moveStrategy);

		board = initiatePositionsOnBoard(board, black, blackPositions, white, whitePositions);

		SwapHandler swapHandler = new SwapHandler();
		Rules rules = new RulesImpl(board, new MoveValidator(board, swapHandler), swapHandler);

		Assert.assertEquals(board.getNode(2, 2).getId(), moveStrategy.move(white.getId(), rules, board).getId());
	}

	@Test
	public void testTurtleMoveStrategyDiagonal() {
		int[] whitePositions = { 14, 20 };
		int[] blackPositions = { 7, 21, 28, 35, 42 };

		BoardImpl board = get8x8EmptyBoard();
		TurtleMove moveStrategy = new TurtleMove();

		Player black = PlayerImpl.getComputerPlayer("black", "black", moveStrategy);
		Player white = PlayerImpl.getComputerPlayer("white", "white", moveStrategy);

		board = initiatePositionsOnBoard(board, black, blackPositions, white, whitePositions);

		SwapHandler swapHandler = new SwapHandler();
		Rules rules = new RulesImpl(board, new MoveValidator(board, swapHandler), swapHandler);

		Assert.assertEquals(board.getNode(6, 2).getId(), moveStrategy.move(white.getId(), rules, board).getId());
	}
}