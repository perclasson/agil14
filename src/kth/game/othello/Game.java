package kth.game.othello;

import java.util.List;
import java.util.Observer;
import java.util.Stack;

import kth.game.othello.board.Board;
import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.score.GameScore;
import kth.game.othello.score.Score;

/**
 * This class represents a game of Othello.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class Game implements Othello {
	private Board board;
	private MoveHandler moveHandler;
	private PlayerHandler playerHandler;
	private Score score;
	private String gameId;
	private List<Observer> gameFinishedObservers;
	private List<Observer> moveObservers;
	private Stack<GameData> previous;
	private GameDataFactory gameDataFactory;

	public Game(Board board, PlayerHandler playerHandler, MoveHandler moveHandler, Score score, String gameId,
			List<Observer> gameFinishedObservers, List<Observer> moveObservers, Stack<GameData> previous,
			GameDataFactory gameDataFactory) {
		this.board = board;
		this.moveHandler = moveHandler;
		this.playerHandler = playerHandler;
		this.score = score;
		this.gameId = gameId;
		this.gameFinishedObservers = gameFinishedObservers;
		this.moveObservers = moveObservers;
		this.previous = previous;
		this.gameDataFactory = gameDataFactory;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Player getPlayerInTurn() {
		return playerHandler.getPlayerInTurn();
	}

	@Override
	public List<Player> getPlayers() {
		return playerHandler.getPlayers();
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return moveHandler.hasValidMove(playerId);
	}

	@Override
	public boolean isActive() {
		return playerHandler.getPlayerInTurn() != null;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return moveHandler.isMoveValid(playerId, nodeId);
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return moveHandler.getNodesToSwap(playerId, nodeId);
	}

	@Override
	public List<Node> move() {
		List<Node> nodes = moveHandler.move();
		saveCurrentState();
		return nodes;
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		List<Node> nodes = moveHandler.move(playerId, nodeId);
		saveCurrentState();
		return nodes;
	}

	@Override
	public void start() {
		playerHandler.setRandomPlayerInTurn();
		saveCurrentState();
	}

	@Override
	public void start(String playerId) {
		playerHandler.setPlayerInTurn(playerId);
		saveCurrentState();
	}

	@Override
	public Score getScore() {
		return score;
	}

	@Override
	public void addGameFinishedObserver(Observer observer) {
		// TODO this game finished observers are to be used
		if (!gameFinishedObservers.contains(observer)) {
			gameFinishedObservers.add(observer);
		}

	}

	@Override
	public void addMoveObserver(Observer observer) {
		// TODO these move observers are to be used
		if (!moveObservers.contains(observer)) {
			moveObservers.add(observer);
		}
	}

	@Override
	public String getId() {
		return gameId;
	}

	@Override
	public void undo() {
		GameData gameData = previous.pop();
		board = new GameBoard(gameData.getNodeData());
		playerHandler.setPlayerInTurn(gameData.getPlayerInTurnId());
		score = new GameScore(playerHandler.getPlayers(), board.getNodes());
	}

	private void saveCurrentState() {
		previous.add(gameDataFactory.createGameData(this));
	}
}
