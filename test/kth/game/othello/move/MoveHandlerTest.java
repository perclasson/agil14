package kth.game.othello.move;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.player.Player.Type;
import kth.game.othello.rules.Rules;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

/**
 * Unit tests of class MoveLogic.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveHandlerTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testComputerMoveException() {
		GameBoard board = mock(GameBoard.class);

		// Player handler with a human player
		PlayerHandler playerHandler = Mockito.mock(PlayerHandler.class);
		Player player = Mockito.mock(Player.class);
		when(playerHandler.getPlayerInTurn()).thenReturn(player);
		when(player.getType()).thenReturn(Type.HUMAN);
		Rules rules = Mockito.mock(Rules.class);

		// Create the move handler
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);

		exception.expect(IllegalStateException.class);

		// Make a computer move, should cast exception
		moveHandler.move();
	}

	@Test
	public void testMovePlayerNotInTurnException() {
		GameBoard board = mock(GameBoard.class);
		String correctPlayerId = "0";
		String wrongPlayerId = "1";

		// Mock a player
		Player player = Mockito.mock(Player.class);
		when(player.getId()).thenReturn(correctPlayerId);

		// Player handler with the player in turn
		PlayerHandler playerHandler = Mockito.mock(PlayerHandler.class);
		when(playerHandler.getPlayerInTurn()).thenReturn(player);

		// Create the move handler
		Rules rules = Mockito.mock(Rules.class);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);

		exception.expect(IllegalArgumentException.class);

		// Make a move with the wrong player, should cast exception
		moveHandler.move(wrongPlayerId, "nodeid");
	}

	@Test
	public void testInvalidMove() {
		// Mock an empty board
		GameBoard board = mock(GameBoard.class);
		when(board.getNodes()).thenReturn(new ArrayList<Node>());

		// Mock a player
		Player player = Mockito.mock(Player.class);
		when(player.getId()).thenReturn("id");

		// Player handler with the player in turn
		PlayerHandler playerHandler = Mockito.mock(PlayerHandler.class);
		when(playerHandler.getPlayerInTurn()).thenReturn(player);

		// Create the move handler
		Rules rules = Mockito.mock(Rules.class);
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);

		exception.expect(IllegalArgumentException.class);

		// Make a invalid move
		moveHandler.move(player.getId(), "nodeid");
	}

	@Test
	public void testMoveShouldChangeOccupant() {
		// Mock a board
		GameBoard board = mock(GameBoard.class);

		// Mock a player
		Player player = Mockito.mock(Player.class);
		when(player.getId()).thenReturn("id");

		// Player handler with the player in turn
		PlayerHandler playerHandler = Mockito.mock(PlayerHandler.class);
		when(playerHandler.getPlayerInTurn()).thenReturn(player);

		// Mock one node to be swapped for each move
		List<Node> swappedNode = new ArrayList<Node>();
		Node node = mock(Node.class);
		swappedNode.add(node);

		// Mock rules to return node
		Rules rules = Mockito.mock(Rules.class);
		when(rules.getNodesToSwap(player.getId(), "nodeid")).thenReturn(swappedNode);

		// Create the move handler
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);

		// Make a move
		moveHandler.move(player.getId(), "nodeid");

		// Verify that change occupant was called
		verify(board).changeOccupantOnNodes(swappedNode, player.getId());
	}

	@Test
	public void testMoveShouldChangePlayerInTurn() {
		// Mock a board
		GameBoard board = mock(GameBoard.class);

		// Mock two players
		Player player = Mockito.mock(Player.class);
		when(player.getId()).thenReturn("id1");
		Player player2 = Mockito.mock(Player.class);
		when(player2.getId()).thenReturn("id2");

		// Player handler with first player in turn
		PlayerHandler playerHandler = Mockito.mock(PlayerHandler.class);
		when(playerHandler.getPlayerInTurn()).thenReturn(player);

		// Mock one node to be swapped for each move
		List<Node> swappedNode = new ArrayList<Node>();
		Node node = mock(Node.class);
		swappedNode.add(node);

		// Mock rules to return swapped nodes
		Rules rules = Mockito.mock(Rules.class);
		when(rules.getNodesToSwap(player.getId(), "nodeid")).thenReturn(swappedNode);

		// Create the move handler
		MoveHandler moveHandler = new MoveHandler(board, playerHandler, rules);

		// Make a move
		moveHandler.move(player.getId(), "nodeid");

		// Verify that players turn was changed
		verify(playerHandler).changePlayersTurn();
	}
}
