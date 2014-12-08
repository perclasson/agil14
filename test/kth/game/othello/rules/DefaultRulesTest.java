package kth.game.othello.rules;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.move.Direction;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit tests of class MoveCalculator.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 * 
 */
public class DefaultRulesTest {

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
				when(board.getNode(x, y)).thenReturn(node);
				nodes.add(node);
			}
		}
		when(board.getNodes()).thenReturn(nodes);
		return board;
	}

	private List<Direction> getMockDirections() {
		ArrayList<Direction> directions = new ArrayList<Direction>();
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				Direction d = Mockito.mock(Direction.class);
				when(d.getX()).thenReturn(x);
				when(d.getY()).thenReturn(y);
				directions.add(d);
			}
		}
		return directions;
	}

	private void setNode(int x, int y, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(null);
		when(board.getNode(x, y).isMarked()).thenReturn(false);
	}

	private void setNode(int x, int y, String playerId, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(playerId);
		when(board.getNode(x, y).isMarked()).thenReturn(true);
	}

	@Test
	public void testGetNodesToSwap() {
		GameBoard board = mockBoard(3);
		DefaultRules defaultRules = new DefaultRules(getMockDirections(), board);
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
		System.out.println("test1");
		swap = defaultRules.getNodesToSwap("white", "x2y0");
		System.out.println("test2");
		assertEquals(swap.size(), 3);
		assertEquals(swap.get(1), board.getNode(1, 0));
		assertEquals(swap.get(2), board.getNode(2, 0));

		// Scenario:
		// | white black empty |
		// | empty target empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		swap = defaultRules.getNodesToSwap("white", "x1y1");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white target black |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, null, board);
		setNode(2, 0, "black", board);
		swap = defaultRules.getNodesToSwap("white", "x1y0");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white target black |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, null, board);
		setNode(2, 0, "black", board);
		swap = defaultRules.getNodesToSwap("white", "x1y0");
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
		swap = defaultRules.getNodesToSwap("white", "x0y2");
		assertEquals(swap.size(), 5);
	}

	@Test
	public void hasValidMoveTest() {
		GameBoard board = mockBoard(5);
		DefaultRules defaultRules = new DefaultRules(getMockDirections(), board);

		// Scenario:
		// | white black empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		assertEquals(defaultRules.hasValidMove("white"), true);
		assertEquals(defaultRules.hasValidMove("black"), false);

		// Scenario:
		// | white black black black empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		setNode(2, 0, "black", board);
		setNode(3, 0, "black", board);
		assertEquals(defaultRules.hasValidMove("white"), true);
		assertEquals(defaultRules.hasValidMove("black"), false);

		// Scenario:
		// | white black black black black |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		// | empty empty empty empty empty |
		setNode(4, 0, "black", board);
		assertEquals(defaultRules.hasValidMove("white"), false);
		assertEquals(defaultRules.hasValidMove("black"), false);

		// Reset board
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				setNode(i, j, board);
			}
		}

		// Scenario:
		// | white empty empty empty empty |
		// | empty black empty empty empty |
		// | empty empty black empty empty |
		// | empty empty empty black empty |
		// | empty empty empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 1, "black", board);
		setNode(2, 2, "black", board);
		setNode(3, 3, "black", board);
		assertEquals(defaultRules.hasValidMove("white"), true);
		assertEquals(defaultRules.hasValidMove("black"), false);

		// Scenario:
		// | white empty empty empty empty |
		// | empty black empty empty empty |
		// | empty empty black empty empty |
		// | empty empty empty black empty |
		// | empty empty empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 1, "black", board);
		setNode(2, 2, "black", board);
		setNode(3, 3, "black", board);
		assertEquals(defaultRules.hasValidMove("white"), true);
		assertEquals(defaultRules.hasValidMove("black"), false);

		// Scenario:
		// | white empty empty empty empty |
		// | empty black empty empty empty |
		// | empty empty black empty empty |
		// | empty empty empty black empty |
		// | empty empty empty empty black |
		setNode(4, 4, "black", board);
		assertEquals(defaultRules.hasValidMove("white"), false);
		assertEquals(defaultRules.hasValidMove("black"), false);

		// Reset board
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				setNode(i, j, board);
			}
		}

		// Scenario:
		// | white empty empty empty empty |
		// | black empty empty empty empty |
		// | black empty empty empty empty |
		// | black empty empty empty empty |
		// | empty empty empty empty empty |
		setNode(0, 0, "white", board);
		setNode(0, 1, "black", board);
		setNode(0, 2, "black", board);
		setNode(0, 3, "black", board);
		assertEquals(defaultRules.hasValidMove("white"), true);
		assertEquals(defaultRules.hasValidMove("black"), false);

		// Scenario:
		// | white empty empty empty empty |
		// | empty black empty empty empty |
		// | empty empty black empty empty |
		// | empty empty empty black empty |
		// | empty empty empty empty black |
		setNode(0, 4, "black", board);
		assertEquals(defaultRules.hasValidMove("white"), false);
		assertEquals(defaultRules.hasValidMove("black"), false);

	}
}
