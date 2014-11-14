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
	public void testGetNodesToSwap() {
		OthelloBoard board = mock(OthelloBoard.class);
		OthelloPlayer black = mock(OthelloPlayer.class);
		OthelloPlayer white = mock(OthelloPlayer.class);
		MoveLogic moveLogic = mock(MoveLogic.class);

		// Create the Othello to test
		Othello othello = new DemoOthello(board, black, white, moveLogic, new Random());

		// Empty board should not return any swapped nodes
		assertEquals(othello.getNodesToSwap("black", "x0y0").size(), 0);
		assertEquals(othello.getNodesToSwap("black", "x3y2").size(), 0);
		assertEquals(othello.getNodesToSwap("black", "x5y1").size(), 0);

		// Create a list of mocked board nodes
		List<Node> nodes = mockBoardNodes(8);

		// Create some moves
		Move moveOne = Mockito.mock(Move.class);
		Move moveTwo = Mockito.mock(Move.class);
		Move moveThree = Mockito.mock(Move.class);
		List<Move> moves = new ArrayList<Move>();

		List<Node> intermediate = new ArrayList<Node>();
		intermediate.add(nodes.get(0));
		intermediate.add(nodes.get(1));
		when(moveOne.getIntermediateNodes()).thenReturn(intermediate);
		moves.add(moveOne);

		//
		when(moveLogic.getValidMoves("black", "x0y0")).thenReturn(moves);

		List<Node> toSwap = othello.getNodesToSwap("black", "x0y0");
		assertEquals(toSwap, intermediate);
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
