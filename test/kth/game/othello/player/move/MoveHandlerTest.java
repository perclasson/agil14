package kth.game.othello.player.move;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Random;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;
import kth.game.othello.history.HistoryHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.TurnHandler;
import kth.game.othello.player.move.strategy.MoveStrategy;
import kth.game.othello.rules.MoveValidator;
import kth.game.othello.rules.RulesImpl;
import kth.game.othello.rules.SwapHandler;
import kth.game.othello.score.ScoreImpl;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

public class MoveHandlerTest {

	/**
	 * @return 3 mocked nodes with id n1, n2, n3
	 */
	private List<Node> get3MockedNodes() {
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
		return nodes;
	}

	@Test
	public void moveChangesPlayerInTurn() {
		// mock board, needed to start a game
		BoardImpl board = Mockito.mock(BoardImpl.class);

		// Mock scores
		ScoreImpl scores = Mockito.mock(ScoreImpl.class);

		// mock move strategy, needed to move
		MoveStrategy moveStrategy = Mockito.mock(MoveStrategy.class);

		// add players
		List<Player> players = new ArrayList<Player>();
		players.add(PlayerImpl.getComputerPlayer("Player 1", "Player 1", moveStrategy));
		players.add(PlayerImpl.getComputerPlayer("Player 2", "Player 2", moveStrategy));

		RulesImpl rules = new RulesImpl(board, null, null);

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(Matchers.anyString())).thenReturn(true);
		Mockito.when(moveValidator.isActive(players)).thenReturn(true);

		HistoryHandler historyHandler = Mockito.mock(HistoryHandler.class);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		MoveHandler moveHandler = new MoveHandler(board, rules, turnHandler, historyHandler, scores);
		String randomPlayer = turnHandler.getRandomPlayer(new Random());
		turnHandler.setStartingPlayer(randomPlayer);
		Player player1 = turnHandler.getPlayerInTurn();
		moveHandler.move();
		Player player2 = turnHandler.getPlayerInTurn();
		Assert.assertNotEquals(player1, player2);
	}

	@Test
	public void twoMovesChangesToFirstPlayer() {
		// mock board, needed to start
		BoardImpl board = Mockito.mock(BoardImpl.class);

		// mock move strategy, needed to move
		MoveStrategy moveStrategy = Mockito.mock(MoveStrategy.class);

		// Mock scores
		ScoreImpl scores = Mockito.mock(ScoreImpl.class);

		// add players
		List<Player> players = new ArrayList<Player>();
		players.add(PlayerImpl.getComputerPlayer("Player 1", "Player 1", moveStrategy));
		players.add(PlayerImpl.getComputerPlayer("Player 2", "Player 2", moveStrategy));

		RulesImpl rules = new RulesImpl(board, null, null);

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(Matchers.anyString())).thenReturn(true);

		HistoryHandler historyHandler = Mockito.mock(HistoryHandler.class);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		MoveHandler moveHandler = new MoveHandler(board, rules, turnHandler, historyHandler, scores);
		turnHandler.setStartingPlayer(players.get(0).getId());
		Player player1 = turnHandler.getPlayerInTurn();
		moveHandler.move();
		moveHandler.move();
		Assert.assertEquals(player1, turnHandler.getPlayerInTurn());
	}

	@Test(expected = IllegalArgumentException.class)
	public void moveForPlayerNotInTurnThrowsException() {
		// add players
		List<Player> players = new ArrayList<Player>();
		players.add(PlayerImpl.getHumanPlayer("Player 1", "Player 1"));
		players.add(PlayerImpl.getHumanPlayer("Player 2", "Player 2"));

		// mock board, needed to start
		BoardImpl board = Mockito.mock(BoardImpl.class);

		RulesImpl rules = new RulesImpl(board, null, null);

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(Matchers.anyString())).thenReturn(true);

		HistoryHandler historyHandler = Mockito.mock(HistoryHandler.class);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		MoveHandler moveHandler = new MoveHandler(board, rules, turnHandler, historyHandler, null);
		turnHandler.setStartingPlayer(players.get(0).getId());

		moveHandler.move(players.get(1).getId(), null);
	}

	@Test
	public void moveReturnsNodesToSwap() {
		// add players
		List<Player> players = new ArrayList<Player>();
		players.add(PlayerImpl.getHumanPlayer("Player 1", "Player 1"));
		players.add(PlayerImpl.getHumanPlayer("Player 2", "Player 2"));

		String moveNodeId = "n4";
		String movingPlayer = players.get(0).getId();

		// mock swap handler, needed to swap
		SwapHandler swapHandler = Mockito.mock(SwapHandler.class);

		// mock nodes to swap
		List<Node> nodesToSwap = get3MockedNodes();
		Node node4 = Mockito.mock(Node.class);
		Mockito.when(node4.getId()).thenReturn("n4");

		// mock board, needed to start
		BoardImpl board = Mockito.mock(BoardImpl.class);
		Mockito.when(board.getNode(moveNodeId)).thenReturn(node4);

		Mockito.when(swapHandler.getNodesToSwap(movingPlayer, moveNodeId, board)).thenReturn(nodesToSwap);

		// mock move validator, needed to move
		MoveValidator validator = Mockito.mock(MoveValidator.class);
		Mockito.when(validator.isMoveValid(movingPlayer, moveNodeId)).thenReturn(true);

		RulesImpl rules = new RulesImpl(board, validator, swapHandler);

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(Matchers.anyString())).thenReturn(true);

		HistoryHandler historyHandler = Mockito.mock(HistoryHandler.class);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		turnHandler.setStartingPlayer(movingPlayer);
		MoveHandler moveHandler = new MoveHandler(board, rules, turnHandler, historyHandler, null);

		List<Node> swappedNodes = moveHandler.move(movingPlayer, moveNodeId);

		boolean nodeFound = false;
		// check that all nodes are
		for (Node node : nodesToSwap) {
			for (Node swappedNode : swappedNodes) {
				if (node.getId().equals(swappedNode.getId())) {
					nodeFound = true;
					break;
				}
			}
			Assert.assertTrue(nodeFound);
			nodeFound = false;
		}

		boolean placedNodeFound = false;
		for (Node swappedNode : swappedNodes) {
			if (swappedNode.getId().equals(node4.getId())) {
				placedNodeFound = true;
				break;
			}
		}
		Assert.assertTrue(placedNodeFound);
	}

	@Test
	public void moveSwapsNodesToSwap() {

		String movingPlayerId = "Player 1";
		String moveNodeId = "3,2";

		// mock nodes to swap
		List<Node> nodesToSwap = get3MockedNodes();
		// add players
		List<Player> players = new ArrayList<Player>();
		players.add(PlayerImpl.getHumanPlayer(movingPlayerId, "Player 1"));
		players.add(PlayerImpl.getHumanPlayer("Player 2", "Player 2"));

		// mock board, needed to start
		BoardImpl board = Mockito.mock(BoardImpl.class);

		// mock swap handler, needed to swap
		SwapHandler swapHandler = Mockito.mock(SwapHandler.class);
		Mockito.when(swapHandler.getNodesToSwap(movingPlayerId, moveNodeId, board)).thenReturn(nodesToSwap);

		// mock move validator, needed to move
		MoveValidator validator = Mockito.mock(MoveValidator.class);
		Mockito.when(validator.isMoveValid(movingPlayerId, moveNodeId)).thenReturn(true);

		RulesImpl rules = new RulesImpl(board, validator, swapHandler);

		MoveValidator moveValidator = Mockito.mock(MoveValidator.class);
		Mockito.when(moveValidator.hasValidMove(Matchers.anyString())).thenReturn(true);

		HistoryHandler historyHandler = Mockito.mock(HistoryHandler.class);

		TurnHandler turnHandler = new TurnHandler(players, moveValidator);
		turnHandler.setStartingPlayer(movingPlayerId);
		MoveHandler moveHandler = new MoveHandler(board, rules, turnHandler, historyHandler, null);

		moveHandler.move(movingPlayerId, moveNodeId);

		// verify that the nodes to swap have had their occupant player ids changed.
		Mockito.verify(board, Mockito.times(1)).setOccupantPlayerId(nodesToSwap.get(0).getId(), movingPlayerId);
		Mockito.verify(board, Mockito.times(1)).setOccupantPlayerId(nodesToSwap.get(1).getId(), movingPlayerId);
		Mockito.verify(board, Mockito.times(1)).setOccupantPlayerId(nodesToSwap.get(2).getId(), movingPlayerId);
	}

	@Test
	public void observersNotifiedAfterMove() {
		String playerId = "playerId";
		String nodeId = "nodeId";
		Player player = PlayerImpl.getHumanPlayer(playerId, "Human");

		List<Node> nodesToSwap = new ArrayList<Node>();
		nodesToSwap.add(new NodeImpl(1, 1, playerId));
		nodesToSwap.add(new NodeImpl(2, 2, playerId));

		TurnHandler turnHandler = Mockito.mock(TurnHandler.class);
		Mockito.when(turnHandler.getPlayerInTurn()).thenReturn(player);

		RulesImpl rules = Mockito.mock(RulesImpl.class);
		Mockito.when(rules.isMoveValid(playerId, nodeId)).thenReturn(true);
		Mockito.when(rules.getNodesToSwap(playerId, nodeId)).thenReturn(nodesToSwap);

		BoardImpl board = Mockito.mock(BoardImpl.class);
		Mockito.when(board.getNode(nodeId)).thenReturn(new NodeImpl(3, 3, playerId));

		HistoryHandler historyHandler = Mockito.mock(HistoryHandler.class);

		Observer observer = Mockito.mock(Observer.class);

		MoveHandler moveHandler = new MoveHandler(board, rules, turnHandler, historyHandler, null);
		moveHandler.addObserver(observer);
		moveHandler.move(playerId, nodeId);

		Mockito.verify(observer, Mockito.times(1)).update(moveHandler, nodesToSwap);
	}
}