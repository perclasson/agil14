package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Random;

import kth.game.othello.OthelloImpl;
import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.move.Handler;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.Player;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

/**
 * Unit tests of class DemoOthello.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class OthelloTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testHasValidMove() {
		Handler moveLogic = Mockito.mock(Handler.class);
		OthelloImpl othello = new OthelloImpl(null, null, null, moveLogic, null);
		String playerId = "player";
		othello.hasValidMove(playerId);
		verify(moveLogic).hasValidMove(playerId);
	}

	@Test
	public void testIsMoveValid() {
		Handler moveLogic = Mockito.mock(Handler.class);
		OthelloImpl othello = new OthelloImpl(null, null, null, moveLogic, null);
		String playerId = "player";
		String nodeId = "x0y0";
		othello.isMoveValid(playerId, nodeId);
		verify(moveLogic).isMoveValid(playerId, nodeId);
	}

	@Test
	public void testGetMovesToSwap() {
		Handler moveLogic = Mockito.mock(Handler.class);
		OthelloImpl othello = new OthelloImpl(null, null, null, moveLogic, null);
		String playerId = "player";
		String nodeId = "x0y0";
		othello.getNodesToSwap(playerId, nodeId);
		verify(moveLogic).getNodesToSwap(playerId, nodeId);
	}

	@Test
	public void testInvalidMove() {
		final PlayerImpl black = Mockito.mock(PlayerImpl.class);
		Handler moveLogic = Mockito.mock(Handler.class);

		Othello othello = new OthelloImpl(null, black, null, moveLogic, null) {
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
		final PlayerImpl black = Mockito.mock(PlayerImpl.class);

		Othello othello = new OthelloImpl(null, black, null, null, null) {
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
		final PlayerImpl black = Mockito.mock(PlayerImpl.class);

		Othello othello = new OthelloImpl(null, black, null, null, null) {
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
		final PlayerImpl black = Mockito.mock(PlayerImpl.class);
		PlayerImpl white = Mockito.mock(PlayerImpl.class);
		Handler moveLogic = Mockito.mock(Handler.class);
		Random random = new Random();

		Othello othello = new OthelloImpl(null, black, white, moveLogic, random) {
			@Override
			public Player getPlayerInTurn() {
				return black;
			}
		};

		String playerId = "black";
		when(black.getType()).thenReturn(Player.Type.COMPUTER);
		when(black.getId()).thenReturn(playerId);
		when(white.getId()).thenReturn("white");

		othello.move();

		// Verify that moveLogic get's called with expected arguments
		verify(moveLogic).randomMove(playerId, random);
	}

	@Test
	public void testStart() {
		BoardImpl board = Mockito.mock(BoardImpl.class);
		PlayerImpl black = Mockito.mock(PlayerImpl.class);
		PlayerImpl white = Mockito.mock(PlayerImpl.class);
		Handler moveLogic = Mockito.mock(Handler.class);

		Othello othello = new OthelloImpl(board, black, white, moveLogic, new Random());

		when(black.getId()).thenReturn("black");

		// The game is active after start
		othello.start();
		assertTrue(othello.isActive());

		// The game starts with correct player
		othello.start("black");
		assertEquals(othello.getPlayerInTurn(), black);
	}

}
