package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.board.GameBoard;
import kth.game.othello.move.MoveHandler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;

/**
 * Unit tests of class MoveLogic.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveLogicTest {

	private GameBoard mockBoard(int order) {
		GameBoard board = mock(GameBoard.class);
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int y = 0; y < order; y++) {
			for (int x = 0; x < order; x++) {
				Node node = mock(Node.class);
				when(node.getId()).thenReturn("x" + x + "y" + y);
				when(node.getXCoordinate()).thenReturn(x);
				when(node.getYCoordinate()).thenReturn(y);
				when(node.isMarked()).thenReturn(false);
				when(board.getNodeByCoordinates(x, y)).thenReturn(node);
				when(board.getNode(node.getId())).thenReturn(node);
				nodes.add(node);
			}
		}
		when(board.getOrder()).thenReturn(order);
		when(board.getNodes()).thenReturn(nodes);
		return board;
	}

	private void setNode(int x, int y, GameBoard board) {
		when(board.getNodeByCoordinates(x, y).getOccupantPlayerId()).thenReturn(null);
		when(board.getNodeByCoordinates(x, y).isMarked()).thenReturn(false);
	}

	private void setNode(int x, int y, String playerId, GameBoard board) {
		when(board.getNodeByCoordinates(x, y).getOccupantPlayerId()).thenReturn(playerId);
		when(board.getNodeByCoordinates(x, y).isMarked()).thenReturn(true);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testGetNodesToSwapException() {
		GameBoard board = mock(GameBoard.class);
		MoveHandler moveLogic = new MoveHandler(board);
		when(board.getNode(Matchers.anyString())).thenThrow(new IllegalArgumentException());

		// Empty board should not have any nodes to swap
		exception.expect(IllegalArgumentException.class);
		moveLogic.getNodesToSwap("black", "x0y0");
	}

	@Test
	public void testGetNodesToSwap() {
		GameBoard board = mockBoard(3);
		MoveHandler moveLogic = new MoveHandler(board);
		List<Node> swap = null;

		// Scenario:
		// | white black target |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);

		// Should be:
		// | white white white |
		// | empty empty empty |
		// | empty empty empty |
		swap = moveLogic.getNodesToSwap("white", "x2y0");
		assertEquals(swap.size(), 2);
		assertEquals(swap.get(0), board.getNodeByCoordinates(1, 0));
		assertEquals(swap.get(1), board.getNodeByCoordinates(2, 0));

		// Scenario:
		// | white black empty |
		// | empty target empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		swap = moveLogic.getNodesToSwap("white", "x1y1");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white target black |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, null, board);
		setNode(2, 0, "black", board);
		swap = moveLogic.getNodesToSwap("white", "x1y0");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white target black |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, null, board);
		setNode(2, 0, "black", board);
		swap = moveLogic.getNodesToSwap("white", "x1y0");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white black white |
		// | black black black |
		// | target black white |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		setNode(2, 0, "white", board);
		setNode(0, 1, "black", board);
		setNode(1, 1, "black", board);
		setNode(2, 1, "black", board);
		setNode(0, 2, board);
		setNode(1, 2, "black", board);
		setNode(2, 2, "white", board);

		// Should be:
		// | white black white |
		// | white white black |
		// | white white white |
		swap = moveLogic.getNodesToSwap("white", "x0y2");
		assertEquals(swap.size(), 4);
	}

	@Test
	public void testMove() {
		GameBoard board = mockBoard(3);
		MoveHandler moveLogic = new MoveHandler(board);
		List<Node> nodes = null;

		// Scenario:
		// | white black target |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		nodes = moveLogic.move("white", "x2y0");

		// Should be:
		// | white white white |
		// | empty empty empty |
		// | empty empty empty |
		assertEquals(nodes.size(), 3);
		for (Node n : nodes) {
			assertEquals(n, board.getNodeByCoordinates(n.getXCoordinate(), n.getYCoordinate()));
		}

		// Scenario:
		// | white black white |
		// | black black black |
		// | target black white |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		setNode(2, 0, "white", board);
		setNode(0, 1, "black", board);
		setNode(1, 1, "black", board);
		setNode(2, 1, "black", board);
		setNode(0, 2, board);
		setNode(1, 2, "black", board);
		setNode(2, 2, "white", board);

		// Should be:
		// | white black white |
		// | white white black |
		// | white white white |
		nodes = moveLogic.move("white", "x0y2");

		assertEquals(nodes.size(), 5);
		for (Node n : nodes) {
			assertEquals(n, board.getNodeByCoordinates(n.getXCoordinate(), n.getYCoordinate()));
		}
	}

	@Test
	public void testHasAndIsValidMove() {
		GameBoard board = mockBoard(3);
		MoveHandler moveLogic = new MoveHandler(board);

		// Scenario:
		// | white white empty |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "white", board);
		assertFalse(moveLogic.hasValidMove("white"));
		for (Node n : board.getNodes()) {
			assertFalse(moveLogic.isMoveValid("white", n.getId()));
		}

		// Scenario:
		// | white black empty |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		assertTrue(moveLogic.hasValidMove("white"));
		assertTrue(moveLogic.isMoveValid("white", "x2y0"));

		// Scenario:
		// | white white black |
		// | empty white empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "white", board);
		setNode(2, 0, "black", board);
		setNode(1, 1, "white", board);
		assertTrue(moveLogic.hasValidMove("black"));
		assertFalse(moveLogic.hasValidMove("white"));

		assertTrue(moveLogic.isMoveValid("black", "x0y2"));
		assertFalse(moveLogic.isMoveValid("black", "x1y2"));
		assertFalse(moveLogic.isMoveValid("black", "x0y1"));
		assertFalse(moveLogic.isMoveValid("black", "x2y1"));
	}
}
