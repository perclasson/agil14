package kth.game.othello;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

import org.junit.Test;
import org.mockito.Mockito;

public class OthelloTest {

	@Test
	public void testGetNodesToSwap() {
	}

	@Test
	public void testHasValidMove() {
	}

	@Test
	public void testIsMoveValid() {
	}

	@Test
	public void testMove() {
	}

	@Test
	public void testStart() {
		Board board = Mockito.mock(Board.class);
		Player black = Mockito.mock(Player.class);
		Player white = Mockito.mock(Player.class);

		when(black.getId()).thenReturn("black");

		Othello othello = new DemoOthello(board, black, white, 8);

		// The game is active after start
		othello.start();
		assertTrue(othello.isActive());

		// The game starts with correct player
		othello.start("black");
		assertEquals(othello.getPlayerInTurn(), black);
	}

}
