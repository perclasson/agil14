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
	public void testInvalidMove() {
		final OthelloPlayer black = Mockito.mock(OthelloPlayer.class);
		MoveLogic moveLogic = Mockito.mock(MoveLogic.class);

		Othello othello = new DemoOthello(null, black, null, moveLogic, null) {
			@Override
			public Player getPlayerInTurn() {
				return black;
			}
		};

		String playerId = "black";
		String nodeId = "x0y0";

		when(black.getId()).thenReturn(playerId);
		when(moveLogic.getNodesToSwap(playerId, nodeId)).thenReturn(new ArrayList<Node>());

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Move is not valid.");
		othello.move(playerId, nodeId);
	}

	@Test
	public void testMoveOfPlayerNotInTurn() {
		final OthelloPlayer black = Mockito.mock(OthelloPlayer.class);

		Othello othello = new DemoOthello(null, black, null, null, null) {
			@Override
			public Player getPlayerInTurn() {
				return black;
			}
		};

		when(black.getId()).thenReturn("black");

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Given player not in turn.");
		othello.move("white", "x0y0");
	}

	@Test
	public void testMoveOfHumanPlayer() {
		final OthelloPlayer black = Mockito.mock(OthelloPlayer.class);

		Othello othello = new DemoOthello(null, black, null, null, null) {
			@Override
			public Player getPlayerInTurn() {
				return black;
			}
		};

		when(black.getType()).thenReturn(Player.Type.HUMAN);

		// Exception when player is of type human
		exception.expect(IllegalStateException.class);
		exception.expectMessage("Player in turn is not a computer.");
		othello.move();
	}

	@Test
	public void testMove() {
		final OthelloPlayer black = Mockito.mock(OthelloPlayer.class);
		MoveLogic moveLogic = Mockito.mock(MoveLogic.class);
		Random random = new Random();

		Othello othello = new DemoOthello(null, black, null, moveLogic, random) {
			@Override
			public Player getPlayerInTurn() {
				return black;
			}
		};

		String playerId = "black";
		when(black.getType()).thenReturn(Player.Type.COMPUTER);
		when(black.getId()).thenReturn(playerId);

		othello.move();

		// Verify that moveLogic get's called with expected arguments
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
