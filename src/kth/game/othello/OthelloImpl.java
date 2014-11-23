package kth.game.othello;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardHandler;
import kth.game.othello.board.Node;
import kth.game.othello.move.MoveHandler;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerHandler;
import kth.game.othello.score.Score;

/**
 * This class represents a game of Othello.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class OthelloImpl implements Othello {
	private BoardHandler boardHandler;
	private MoveHandler moveHandler;
	private PlayerHandler playerHandler;
	private Score score;

	public OthelloImpl(BoardHandler boardHandler, PlayerHandler playerHandler, MoveHandler moveHandler, Score score) {
		this.boardHandler = boardHandler;
		this.moveHandler = moveHandler;
		this.playerHandler = playerHandler;
		this.score = score;
	}

	@Override
	public Board getBoard() {
		return boardHandler.getBoard();
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
		return moveHandler.move(this);
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		return moveHandler.move(playerId, nodeId);
	}

	@Override
	public void start() {
		playerHandler.setRandomPlayerInTurn();
	}

	@Override
	public void start(String playerId) {
		playerHandler.setPlayerInTurn(playerId);
	}

	@Override
	public Score getScore() {
		return score;
	}

}
