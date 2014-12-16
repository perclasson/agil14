package kth.game.othello.player.move;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;

import kth.game.othello.rules.MoveValidator;
import kth.game.othello.rules.SwapHandler;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MoveValidatorTest {

	private BoardImpl getMockedBoardImplWith3Nodes() {
		// mock nodes
		Node node1 = Mockito.mock(Node.class);
		Mockito.when(node1.getId()).thenReturn("n1");
		Node node2 = Mockito.mock(Node.class);
		Mockito.when(node2.getId()).thenReturn("n2");
		Node node3 = Mockito.mock(Node.class);
		Mockito.when(node3.getId()).thenReturn("n3");

		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);

		BoardImpl board = Mockito.mock(BoardImpl.class);
		Mockito.when(board.getNode("n1")).thenReturn(node1);
		Mockito.when(board.getNode("n2")).thenReturn(node2);
		Mockito.when(board.getNode("n3")).thenReturn(node3);
		Mockito.when(board.getNodes()).thenReturn(nodes);
		return board;
	}

	@Test
	public void moveIsNotValidOnUsedNode() {
		Othello game = new OthelloFactoryImpl().createHumanGame();
		SwapHandler swapHandler = new SwapHandler();
		MoveValidator validator = new MoveValidator((BoardImpl) game.getBoard(), swapHandler);
		game.start();
		String playerInTurnId = game.getPlayerInTurn().getId();
		List<Node> nodes = game.getBoard().getNodes();
		for (Node node : nodes) {
			if (node.getOccupantPlayerId() != null) {
				Assert.assertFalse(validator.isMoveValid(playerInTurnId, node.getId()));
				break;
			}
		}
	}

	@Test
	public void testGameIsActiveWhenValidNodeExists() {

		String movingPlayerId = "Player 1";
		String otherPlayerId = "Player 2";

		BoardImpl board = getMockedBoardImplWith3Nodes();

		SwapHandler swapHandler = Mockito.mock(SwapHandler.class);

		Mockito.when(swapHandler.getNodesToSwap(movingPlayerId, "n1", board)).thenReturn(new ArrayList<Node>());
		Mockito.when(swapHandler.getNodesToSwap(movingPlayerId, "n2", board)).thenReturn(new ArrayList<Node>());
		Mockito.when(swapHandler.getNodesToSwap(movingPlayerId, "n3", board)).thenReturn(new ArrayList<Node>());

		List<Node> nodes = board.getNodes();
		Mockito.when(swapHandler.getNodesToSwap(otherPlayerId, "n1", board)).thenReturn(nodes);
		Mockito.when(swapHandler.getNodesToSwap(otherPlayerId, "n2", board)).thenReturn(nodes);
		Mockito.when(swapHandler.getNodesToSwap(otherPlayerId, "n3", board)).thenReturn(nodes);

		// add players
		List<Player> players = new ArrayList<Player>();
		players.add(PlayerImpl.getHumanPlayer("Player 1", movingPlayerId));
		players.add(PlayerImpl.getHumanPlayer("Player 2", otherPlayerId));
		MoveValidator validator = new MoveValidator(board, swapHandler);

		Assert.assertEquals(true, validator.isActive(players));
	}

	@Test
	public void testGameIsNotActiveWhenNoValidNodeExists() {
		String movingPlayerId = "Player 1";
		String otherPlayerId = "Player 2";

		BoardImpl board = getMockedBoardImplWith3Nodes();

		SwapHandler swapHandler = Mockito.mock(SwapHandler.class);

		Mockito.when(swapHandler.getNodesToSwap(movingPlayerId, "n1", board)).thenReturn(new ArrayList<Node>());
		Mockito.when(swapHandler.getNodesToSwap(movingPlayerId, "n2", board)).thenReturn(new ArrayList<Node>());
		Mockito.when(swapHandler.getNodesToSwap(movingPlayerId, "n3", board)).thenReturn(new ArrayList<Node>());

		Mockito.when(swapHandler.getNodesToSwap(otherPlayerId, "n1", board)).thenReturn(new ArrayList<Node>());
		Mockito.when(swapHandler.getNodesToSwap(otherPlayerId, "n2", board)).thenReturn(new ArrayList<Node>());
		Mockito.when(swapHandler.getNodesToSwap(otherPlayerId, "n3", board)).thenReturn(new ArrayList<Node>());

		// add players
		List<Player> players = new ArrayList<Player>();
		players.add(PlayerImpl.getHumanPlayer("Player 1", movingPlayerId));
		players.add(PlayerImpl.getHumanPlayer("Player 2", otherPlayerId));
		MoveValidator validator = new MoveValidator(board, swapHandler);

		Assert.assertEquals(false, validator.isActive(players));
	}
}
