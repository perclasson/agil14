package kth.game.othello;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import kth.game.othello.board.Board;
import kth.game.othello.board.GameBoard;
import kth.game.othello.board.Node;
import kth.game.othello.gamestate.GameState;
import kth.game.othello.gamestate.GameStateHandler;
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
public class Game extends Observable implements Othello {
	private Board board;
	private MoveHandler moveHandler;
	private PlayerHandler playerHandler;
	private Score score;
	private String gameId;
	private GameStateHandler gameStateHandler;

	public Game(Board board, PlayerHandler playerHandler, MoveHandler moveHandler, Score score,
			GameStateHandler gameStateHandler) {
		this.board = board;
		this.moveHandler = moveHandler;
		this.playerHandler = playerHandler;
		this.score = score;
		this.gameStateHandler = gameStateHandler;
		this.gameId = UUID.randomUUID().toString();
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
		notifyObserversIfInactive();
		return nodes;
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		List<Node> nodes = moveHandler.move(playerId, nodeId);
		saveCurrentState();
		notifyObserversIfInactive();
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
		addObserver(observer);
	}

	@Override
	public void addMoveObserver(Observer observer) {
		moveHandler.addObserver(observer);
	}

	@Override
	public String getId() {
		return gameId;
	}

	@Override
	public void undo() {
		GameState gameState = gameStateHandler.pop();
		if (gameState != null) {
			board = new GameBoard(gameState.getNodeData());
			playerHandler.setPlayerInTurn(gameState.getPlayerInTurnId());
			score = new GameScore(playerHandler.getPlayers(), board.getNodes());
		}
	}

	private void saveCurrentState() {
		gameStateHandler.add(this);
	}

	private void notifyObserversIfInactive() {
		if (!isActive()) {
			setChanged();
			notifyObservers();
		}
	}
}
