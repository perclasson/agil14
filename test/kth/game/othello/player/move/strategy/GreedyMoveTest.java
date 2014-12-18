package kth.game.othello.player.move.strategy;

import java.util.ArrayList;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.rules.MoveValidator;
import kth.game.othello.rules.Rules;
import kth.game.othello.rules.RulesImpl;
import kth.game.othello.rules.SwapHandler;

import org.junit.Assert;
import org.junit.Test;

public class GreedyMoveTest {

	private BoardImpl get8x8EmptyBoard() {
		ArrayList<Node> nodes = new ArrayList<Node>(64);
		for (int i = 0; i < 64; i++) {
			nodes.add(new NodeImpl((i % 8), i / 8, 1));
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
		return board;
	}

	@Test
	public void testGreedyMoveStrategy() {
		int[] whitePositions = { 7, 12, 14, 17, 18, 19, 20, 22, 28, 29, 30, 35, 37, 39, 42, 49 };
		int[] blackPositions = { 3, 16, 23, 45, 56 };

		BoardImpl board = get8x8EmptyBoard();
		GreedyMove moveStrategy = new GreedyMove();

		Player black = PlayerImpl.getComputerPlayer("black", "black", moveStrategy);
		Player white = PlayerImpl.getComputerPlayer("white", "white", moveStrategy);

		board = initiatePositionsOnBoard(board, black, blackPositions, white, whitePositions);

		SwapHandler swapHandler = new SwapHandler();
		Rules rules = new RulesImpl(board, new MoveValidator(board, swapHandler), swapHandler);
		Assert.assertEquals(board.getNodes().get(21).getId(), moveStrategy.move(black.getId(), rules, board).getId());
	}

	@Test
	public void testGreedyMoveWithTwoEquallyGoodOpportunities() {
		int[] whitePositions = { 1, 2, 3, 4, 5, 6, 14, 21, 28, 35, 42, 49 };
		int[] blackPositions = { 7 };

		BoardImpl board = get8x8EmptyBoard();
		GreedyMove moveStrategy = new GreedyMove();

		Player black = PlayerImpl.getComputerPlayer("black", "black", moveStrategy);
		Player white = PlayerImpl.getComputerPlayer("white", "white", moveStrategy);

		board = initiatePositionsOnBoard(board, black, blackPositions, white, whitePositions);

		SwapHandler swapHandler = new SwapHandler();
		Rules rules = new RulesImpl(board, new MoveValidator(board, swapHandler), swapHandler);
		Assert.assertEquals(board.getNodes().get(0).getId(), moveStrategy.move(black.getId(), rules, board).getId());
	}
}
