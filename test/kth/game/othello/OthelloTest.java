package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.player.OthelloPlayer;
import kth.game.othello.player.Player;

import org.junit.Test;
import org.mockito.Mockito;

public class OthelloTest {

	private List<Node> mockBoardNodes(int n) {
		List<Node> nodes = new ArrayList<Node>();
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				Node node = mock(Node.class);
				when(node.getId()).thenReturn("x" + x + "y" + y);
				when(node.getXCoordinate()).thenReturn(x);
				when(node.getYCoordinate()).thenReturn(y);
				nodes.add(node);
			}
		}
		return nodes;
	}

	@Test
	public void testHasValidMove() {
	}

	@Test
	public void testIsMoveValid() {
	}

	@Test
	public void testMove() {
		OthelloBoard board = Mockito.mock(OthelloBoard.class);
		final OthelloPlayer black = Mockito.mock(OthelloPlayer.class);
		OthelloPlayer white = Mockito.mock(OthelloPlayer.class);
		MoveLogic moveLogic = Mockito.mock(MoveLogic.class);

		Othello othello = new DemoOthello(board, black, white, moveLogic, new Random()) {
			@Override
			public Player getPlayerInTurn() {
				return black;
			}
		};

		String blackId = "black";
		when(black.getId()).thenReturn(blackId);
		when(black.getType()).thenReturn(Player.Type.COMPUTER);
		when(moveLogic.getValidMoves(blackId)).thenReturn(new ArrayList<Move>());

		// When no valid moves are return from moveLogic, it should return empty list
		assertEquals(othello.move(), new ArrayList<Node>());

		List<Node> nodes = new ArrayList<Node>();
		Node node = Mockito.mock(Node.class);
		when(node.getId()).thenReturn("x0y0");
		nodes.add(node);

		List<Move> moves = new ArrayList<Move>();
		Move move = Mockito.mock(Move.class);
		moves.add(move);

		when(move.getIntermediateNodes()).thenReturn(nodes);
		when(move.getMovedToNode()).thenReturn(node);
		when(moveLogic.getValidMoves(blackId)).thenReturn(moves);

	}

	@Test(expected = IllegalStateException.class)
	public void testMoveException() {
		OthelloBoard board = Mockito.mock(OthelloBoard.class);
		final OthelloPlayer black = Mockito.mock(OthelloPlayer.class);
		OthelloPlayer white = Mockito.mock(OthelloPlayer.class);
		MoveLogic moveLogic = Mockito.mock(MoveLogic.class);

		Othello othello = new DemoOthello(board, black, white, moveLogic, null) {
			@Override
			public Player getPlayerInTurn() {
				return black;
			}
		};

		when(black.getType()).thenReturn(Player.Type.HUMAN);

		// As player in turn is of type human, it should give exception
		othello.move();
	}

	@Test
	public void testStart() {
		OthelloBoard board = Mockito.mock(OthelloBoard.class);
		OthelloPlayer black = Mockito.mock(OthelloPlayer.class);
		OthelloPlayer white = Mockito.mock(OthelloPlayer.class);
		MoveLogic moveLogic = Mockito.mock(MoveLogic.class);

		Othello othello = new DemoOthello(board, black, white, moveLogic, new Random());

		when(black.getId()).thenReturn("black");

		// The game is active after start
		othello.start();
		assertTrue(othello.isActive());

		// The game starts with correct player
		othello.start("black");
		assertEquals(othello.getPlayerInTurn(), black);
	}

}
