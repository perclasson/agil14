package kth.game.othello;

import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Set;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.history.HistoryHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.TurnHandler;
import kth.game.othello.player.move.MoveHandler;
import kth.game.othello.rules.RulesImpl;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreImpl;

/**
 * An implementation of {@link kth.game.othello.Othello}.
 */
public class OthelloImpl extends Observable implements Othello, Observer {

	private String id;
	private BoardImpl board;
	private List<Player> players;
	private RulesImpl rules;
	private MoveHandler moveHandler;
	private ScoreImpl scoreImpl;
	private TurnHandler turnHandler;
	private HistoryHandler historyHandler;
	private Set<Observer> gameFinishedObservers;
	private Set<Observer> moveObservers;

	public OthelloImpl(String id, BoardImpl board, List<Player> players, RulesImpl rules, ScoreImpl scoreImpl,
			TurnHandler turnHandler, MoveHandler moveHandler, HistoryHandler historyHandler) {
		this.id = id;
		this.board = board;
		this.players = players;
		this.rules = rules;
		this.scoreImpl = scoreImpl;
		this.turnHandler = turnHandler;
		this.moveHandler = moveHandler;
		this.historyHandler = historyHandler;
		gameFinishedObservers = new HashSet<Observer>();
		moveObservers = new HashSet<Observer>();
		moveHandler.addObserver(this);
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return rules.getNodesToSwap(playerId, nodeId);
	}

	@Override
	public Player getPlayerInTurn() {
		return turnHandler.getPlayerInTurn();
	}

	@Override
	public List<Player> getPlayers() {
		return players;
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return rules.hasValidMove(playerId);
	}

	@Override
	public boolean isActive() {
		return rules.isActive(players);
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return rules.isMoveValid(playerId, nodeId);
	}

	@Override
	public List<Node> move() {
		return moveHandler.move();
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		return moveHandler.move(playerId, nodeId);
	}

	@Override
	public void start() {
		String randomPlayer = turnHandler.getRandomPlayer(new Random());
		start(randomPlayer);
	}

	@Override
	public void start(String playerId) {
		turnHandler.setStartingPlayer(playerId);
		scoreImpl.setInitialScore(board.getNodes());
	}

	@Override
	public Score getScore() {
		return scoreImpl;
	}

	@Override
	public void addGameFinishedObserver(Observer observer) {
		gameFinishedObservers.add(observer);
	}

	@Override
	public void addMoveObserver(Observer observer) {
		moveObservers.add(observer);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void undo() {
		historyHandler.undoPreviousMove(board);
	}

	@Override
	public void update(Observable o, Object arg) {

		for (Observer observer : moveObservers) {
			observer.update(this, arg);
		}

		if (!rules.isActive(players)) {
			for (Observer observer : gameFinishedObservers) {
				observer.update(this, null);
			}
		}
	}
}
