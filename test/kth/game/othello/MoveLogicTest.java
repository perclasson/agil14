package kth.game.othello;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Matchers;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.MoveLogic;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MoveLogicTest {

	private OthelloBoard mockBoard(int order) {
		OthelloBoard board = mock(OthelloBoard.class);
		for (int y = 0; y < order; y++) {
			for (int x = 0; x < order; x++) {
				Node node = mock(Node.class);
				when(node.getId()).thenReturn("x" + x + "y" + y);
				when(node.getXCoordinate()).thenReturn(x);
				when(node.getYCoordinate()).thenReturn(y);
				when(node.isMarked()).thenReturn(false);
				when(board.getNodeByCoordinates(x, y)).thenReturn(node);
				when(board.getNode(node.getId())).thenReturn(node);	
			}
		}
		when(board.getOrder()).thenReturn(order);
		return board;
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testGetNodesToSwapException() {
		OthelloBoard board = mock(OthelloBoard.class);
		MoveLogic moveLogic = new MoveLogic(board);
		when(board.getNode(Matchers.anyString())).thenThrow(new IllegalArgumentException());

		// Empty board should not have any nodes to swap
		exception.expect(IllegalArgumentException.class);
		moveLogic.getNodesToSwap("black", "x0y0");
	}

	@Test
	public void testGetNodesToSwap() {
		OthelloBoard board = mockBoard(8);
		MoveLogic moveLogic = new MoveLogic(board);
		
		List<Node> swap = null;
			
		// 1
		when(board.getNodeByCoordinates(0, 0).getOccupantPlayerId()).thenReturn("white");
		when(board.getNodeByCoordinates(0, 0).isMarked()).thenReturn(true);
		
		when(board.getNodeByCoordinates(1, 0).getOccupantPlayerId()).thenReturn("black");
		when(board.getNodeByCoordinates(1, 0).isMarked()).thenReturn(true);

		swap = moveLogic.getNodesToSwap("white", "x2y0");
		assertEquals(swap.size(), 2);
		assertEquals(swap.get(0), board.getNodeByCoordinates(1, 0));
		
	}

	public void testRandomMove() {
	 
	}

	public void testMove() {
	}

	public void testHasValidMove() {
	}

	public void testIsMoveValid() {
	}
}
