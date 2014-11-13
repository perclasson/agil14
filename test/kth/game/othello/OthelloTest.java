package kth.game.othello;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

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
				Node node = Mockito.mock(Node.class);
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
		OthelloBoard board = Mockito.mock(OthelloBoard.class);
		OthelloPlayer black = Mockito.mock(OthelloPlayer.class);
		OthelloPlayer white = Mockito.mock(OthelloPlayer.class);

		int boardOrder = 8;
		Othello othello = new DemoOthello(board, black, white);

		List<Node> nodes = mockBoardNodes(boardOrder);

		when(board.getNodes()).thenReturn(nodes);
		when(board.getOrder()).thenReturn(boardOrder);

		List<Node> swapped = null;

		// Random moves on empty board
		assertEquals(othello.getNodesToSwap("black", "x0y0").size(), 0);
		assertEquals(othello.getNodesToSwap("black", "x3y2").size(), 0);
		assertEquals(othello.getNodesToSwap("black", "x5y1").size(), 0);
		
		// black - white - white - target - empty
		when(nodes.get(0).getOccupantPlayerId()).thenReturn("black");
		when(nodes.get(0).isMarked()).thenReturn(true);
		when(nodes.get(1).getOccupantPlayerId()).thenReturn("white");
		when(nodes.get(1).isMarked()).thenReturn(true);
		when(nodes.get(2).getOccupantPlayerId()).thenReturn("white");
		when(nodes.get(2).isMarked()).thenReturn(true);
		swapped = othello.getNodesToSwap("black", "x3y0");
		assertTrue(swapped.contains(nodes.get(1)));
		assertTrue(swapped.contains(nodes.get(2)));


		// black - white - black - target - empty
		when(nodes.get(2).getOccupantPlayerId()).thenReturn("black");
		when(nodes.get(2).isMarked()).thenReturn(true);
		swapped = othello.getNodesToSwap("black", "x3y0");
		assertEquals(swapped.size(), 0);
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

		Othello othello = new DemoOthello(board, black, white);
		
		when(black.getId()).thenReturn("black");

		// The game is active after start
		othello.start();
		assertTrue(othello.isActive());

		// The game starts with correct player
		othello.start("black");
		assertEquals(othello.getPlayerInTurn(), black);
	}

}
