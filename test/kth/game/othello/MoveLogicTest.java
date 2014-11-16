package kth.game.othello;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.MoveLogic;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.player.OthelloPlayer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class MoveLogicTest {

	private List<Node> mockBoardNodes(int order) {
		List<Node> nodes = new ArrayList<Node>();
		for (int y = 0; y < order; y++) {
			for (int x = 0; x < order; x++) {
				Node node = Mockito.mock(Node.class);
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
	public void testGetNodesToSwapException() {
		OthelloBoard board = mock(OthelloBoard.class);
		MoveLogic moveLogic = new MoveLogic(board);

		// Create empty node list
		List<Node> nodes = mockBoardNodes(0);
		when(board.getNodes()).thenReturn(nodes);

		// Empty board should not have any nodes to swap
		exception.expect(IllegalArgumentException.class);
		moveLogic.getNodesToSwap("black", "x0y0");
	}

	@Test
	public void testGetNodesToSwap() {
		OthelloBoard board = mock(OthelloBoard.class);
		OthelloPlayer black = mock(OthelloPlayer.class);
		OthelloPlayer white = mock(OthelloPlayer.class);
		MoveLogic moveLogic = new MoveLogic(board);

		// Create a list of mocked board nodes
		List<Node> nodes = mockBoardNodes(8);
		when(board.getNodes()).thenReturn(nodes);

		// TODO
		List<Node> toSwap = moveLogic.getNodesToSwap("black", "x0y0");
		// assertEquals(intermediate, toSwap);
	}
}
