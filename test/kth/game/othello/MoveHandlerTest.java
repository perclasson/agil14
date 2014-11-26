package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.move.MoveCalculator;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;
import kth.game.othello.player.PlayerHandler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * Unit tests of class MoveLogic.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveHandlerTest {

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

	private void setNode(int x, int y, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(null);
		when(board.getNode(x, y).isMarked()).thenReturn(false);
	}

	private void setNode(int x, int y, String playerId, GameBoard board) {
		when(board.getNode(x, y).getOccupantPlayerId()).thenReturn(playerId);
		when(board.getNode(x, y).isMarked()).thenReturn(true);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testGetNodesToSwapException() {
		GameBoard board = mock(GameBoard.class);
		PlayerHandler playerhandler = Mockito.mock(PlayerHandler.class);
		MoveCalculator movecalculator = Mockito.mock(MoveCalculator.class);
		MoveHandler movehandler = new MoveHandler(board, playerhandler, movecalculator);
		when(movecalculator.getNodesToSwap(Matchers.anyString(), Matchers.anyString())).thenReturn(
				new ArrayList<Node>());
		// Empty board should not have any nodes to swap
		exception.expect(IllegalArgumentException.class);
		movehandler.move("black", "x0y0");
	}

	// If the type of a player is not a computer, then an exception should be thrown.
	@Test
	public void testMoveException() {
		GameBoard board = mock(GameBoard.class);
		PlayerHandler playerhandler = Mockito.mock(PlayerHandler.class);
		Player player = Mockito.mock(Player.class);
		when(playerhandler.getPlayerInTurn()).thenReturn(player);
		when(player.getType()).thenReturn(Type.HUMAN);
		MoveCalculator movecalculator = Mockito.mock(MoveCalculator.class);
		MoveHandler movehandler = new MoveHandler(board, playerhandler, movecalculator);
		Game game = Mockito.mock(Game.class);
		exception.expect(IllegalStateException.class);
		movehandler.move(game);

	}

	@Test
	public void testGetNodesToSwap() {
		GameBoard board = mockBoard(3);
		PlayerHandler playerhandler = Mockito.mock(PlayerHandler.class);
		MoveCalculator movecalculator = new MoveCalculator(board);
		MoveHandler movehandler = new MoveHandler(board, playerhandler, movecalculator);
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
		swap = movehandler.getNodesToSwap("white", "x2y0");
		assertEquals(swap.size(), 3);
		assertEquals(swap.get(1), board.getNode(1, 0));
		assertEquals(swap.get(2), board.getNode(2, 0));

		// Scenario:
		// | white black empty |
		// | empty target empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		swap = movehandler.getNodesToSwap("white", "x1y1");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white target black |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, null, board);
		setNode(2, 0, "black", board);
		swap = movehandler.getNodesToSwap("white", "x1y0");
		assertEquals(swap.size(), 0);

		// Scenario:
		// | white target black |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, null, board);
		setNode(2, 0, "black", board);
		swap = movehandler.getNodesToSwap("white", "x1y0");
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
		swap = movehandler.getNodesToSwap("white", "x0y2");
		assertEquals(swap.size(), 5);
	}

	@Test
	public void testMove() {
		GameBoard board = mockBoard(3);
		PlayerHandler playerhandler = Mockito.mock(PlayerHandler.class);
		Player player = Mockito.mock(Player.class);
		when(playerhandler.getPlayerInTurn()).thenReturn(player);
		when(player.getId()).thenReturn("white");
		MoveCalculator movecalculator = new MoveCalculator(board);
		MoveHandler movehandler = new MoveHandler(board, playerhandler, movecalculator);
		List<Node> nodes = null;

		// Scenario:
		// | white black target |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		nodes = movehandler.move("white", "x2y0");

		// Should be:
		// | white white white |
		// | empty empty empty |
		// | empty empty empty |
		assertEquals(nodes.size(), 3);
		for (Node n : nodes) {
			assertEquals(n, board.getNode(n.getXCoordinate(), n.getYCoordinate()));
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
		nodes = movehandler.move("white", "x0y2");

		assertEquals(nodes.size(), 5);
		for (Node n : nodes) {
			assertEquals(n, board.getNode(n.getXCoordinate(), n.getYCoordinate()));
		}
	}

	@Test
	public void testHasAndIsValidMove() {
		GameBoard board = mockBoard(3);
		PlayerHandler playerhandler = Mockito.mock(PlayerHandler.class);
		MoveCalculator movecalculator = Mockito.mock(MoveCalculator.class);
		MoveHandler movehandler = new MoveHandler(board, playerhandler, movecalculator);

		// Scenario:
		// | white white empty |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "white", board);
		assertFalse(movehandler.hasValidMove("white"));
		for (Node n : board.getNodes()) {
			assertFalse(movehandler.isMoveValid("white", n.getId()));
		}

		// Scenario:
		// | white black empty |
		// | empty empty empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "black", board);
		assertTrue(movehandler.hasValidMove("white"));
		assertTrue(movehandler.isMoveValid("white", "x2y0"));

		// Scenario:
		// | white white black |
		// | empty white empty |
		// | empty empty empty |
		setNode(0, 0, "white", board);
		setNode(1, 0, "white", board);
		setNode(2, 0, "black", board);
		setNode(1, 1, "white", board);
		assertTrue(movehandler.hasValidMove("black"));
		assertFalse(movehandler.hasValidMove("white"));

		assertTrue(movehandler.isMoveValid("black", "x0y2"));
		assertFalse(movehandler.isMoveValid("black", "x1y2"));
		assertFalse(movehandler.isMoveValid("black", "x0y1"));
		assertFalse(movehandler.isMoveValid("black", "x2y1"));
	}
}
