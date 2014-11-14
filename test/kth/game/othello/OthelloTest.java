package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.player.OthelloPlayer;
import kth.game.othello.player.Player;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

	@Rule
	public ExpectedException exception = ExpectedException.none();

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
		Random random = new Random();

		Othello othello = new DemoOthello(board, black, white, moveLogic, random) {
			@Override
			public Player getPlayerInTurn() {
				return black;
			}
		};

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Player not in turn.");
		othello.move("black", "x0y0");

		when(black.getType()).thenReturn(Player.Type.HUMAN);

		// Exception when player is of type human
		exception.expect(IllegalStateException.class);
		othello.move();

		String playerId = "black";
		when(black.getType()).thenReturn(Player.Type.COMPUTER);
		when(black.getId()).thenReturn(playerId);

		othello.move();
		// Verify that moveLogic get's called correctly
		verify(moveLogic).getRandomValidMove(playerId, random);

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
