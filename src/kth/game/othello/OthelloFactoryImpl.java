package kth.game.othello;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.board.NodeImpl;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.board.factory.Square;
import kth.game.othello.history.HistoryHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.TurnHandler;
import kth.game.othello.player.move.MoveHandler;
import kth.game.othello.player.move.strategy.FirstMove;
import kth.game.othello.rules.MoveValidator;
import kth.game.othello.rules.RulesImpl;
import kth.game.othello.rules.SwapHandler;
import kth.game.othello.score.ScoreImpl;
import kth.game.othello.score.ScoreImplFactory;

/**
 * The responsibility of OthelloFactoryImpl is to create {@link kth.game.othello.Othello} objects.
 */
public class OthelloFactoryImpl implements OthelloFactory {

	private static final int STANDARD_BOARD_SIZE = 8;

	@Override
	public Othello createComputerGame() {
		PlayerImpl player1 = PlayerImpl.getComputerPlayer("Computer 1", "Computer 1", new FirstMove());
		PlayerImpl player2 = PlayerImpl.getComputerPlayer("Computer 2", "Computer 2", new FirstMove());

		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);

		return createGame(new Square().getNodes(STANDARD_BOARD_SIZE, players), players);
	}

	@Override
	public Othello createHumanGame() {
		PlayerImpl player1 = PlayerImpl.getHumanPlayer("Human 1", "Human 1");
		PlayerImpl player2 = PlayerImpl.getHumanPlayer("Human 2", "Human 2");

		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);

		return createGame(new Square().getNodes(STANDARD_BOARD_SIZE, players), players);
	}

	@Override
	public Othello createHumanVersusComputerGame() {
		PlayerImpl player1 = PlayerImpl.getHumanPlayer("Human", "Human");
		PlayerImpl player2 = PlayerImpl.getComputerPlayer("Computer", "Computer", new FirstMove());

		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);

		return createGame(new Square().getNodes(STANDARD_BOARD_SIZE, players), players);
	}

	@Override
	public Othello createGame(Set<NodeData> nodesData, List<Player> players) {
		List<Node> nodes = translateNodesData(nodesData);
		BoardImpl board = new BoardImpl(nodes);
		ScoreImplFactory scoreFactory = new ScoreImplFactory();
		ScoreImpl scoreImpl = scoreFactory.createScoreController(players, board.getNodes());
		SwapHandler swapHandler = new SwapHandler();
		MoveValidator validator = new MoveValidator(board, swapHandler);
		RulesImpl rules = new RulesImpl(board, validator, swapHandler);
		TurnHandler turnHandler = new TurnHandler(players, validator);
		HistoryHandler historyHandler = new HistoryHandler(turnHandler);
		MoveHandler moveHandler = new MoveHandler(board, rules, turnHandler, historyHandler, scoreImpl);

		return new OthelloImpl(UUID.randomUUID().toString(), board, players, rules, scoreImpl, turnHandler,
				moveHandler, historyHandler);
	}

	/**
	 * Check if coordinates is a boundary on board.
	 * 
	 * @param board
	 * @param x
	 * @param y
	 * @return boolean
	 */
	private boolean isAtBoundary(NodeData[][] board, int x, int y) {
		try {
			if (board[x + 1][y] == null || board[x - 1][y] == null || board[x][y + 1] == null
					|| board[x][y - 1] == null) {
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the boundary nodes.
	 * 
	 * @param nodesData
	 *            The data.
	 * @return Set of boundary nodes.
	 */
	private Set<NodeData> getBoundaryNodes(Set<NodeData> nodesData) {
		Set<NodeData> boundaryNodes = new HashSet<>();
		int maxX = 0;
		int maxY = 0;
		for (NodeData node : nodesData) {
			maxX = Math.max(node.getXCoordinate(), maxX);
			maxY = Math.max(node.getYCoordinate(), maxY);
		}
		NodeData[][] board = new NodeData[maxX + 1][maxY + 1];
		for (NodeData node : nodesData) {
			board[node.getXCoordinate()][node.getYCoordinate()] = node;
		}

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (board[x][y] != null && isAtBoundary(board, x, y)) {
					boundaryNodes.add(board[x][y]);
				}
			}
		}
		return boundaryNodes;
	}

	/**
	 * Translates a set of NodeData to a ordered list of Node
	 *
	 * @param nodesData
	 *            the set of NodeData to be translated
	 * @return the ordered list of Node
	 */
	public List<Node> translateNodesData(Set<NodeData> nodesData) {
		List<Node> nodes = new ArrayList<Node>();
		Set<NodeData> boundaryNodes = getBoundaryNodes(nodesData);

		for (NodeData nodeData : nodesData) {
			int value = 1;
			if (boundaryNodes.contains(nodeData)) {
				// if we have a boundary node, the value should be 2
				value = 2;
			}
			nodes.add(new NodeImpl(nodeData.getXCoordinate(), nodeData.getYCoordinate(),
					nodeData.getOccupantPlayerId(), value));
		}

		sortNodes(nodes);
		return nodes;
	}

	/**
	 * Sorts a list of Node in descending order
	 *
	 * @param nodes
	 *            the list to be sorted
	 */
	private void sortNodes(List<Node> nodes) {
		Collections.sort(nodes, new Comparator<Node>() {

			@Override
			public int compare(Node o1, Node o2) {
				if (o1.getYCoordinate() == o2.getYCoordinate()) {
					return o1.getXCoordinate() - o2.getXCoordinate();
				} else {
					return o1.getYCoordinate() - o2.getYCoordinate();
				}
			}
		});
	}
}
