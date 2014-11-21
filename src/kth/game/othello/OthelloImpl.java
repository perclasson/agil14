package kth.game.othello;

import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;

/**
 * This class represents a game of Othello.
 * 
 * @author Ludvig Axelsson, Per Classon & Tommy Roshult
 */
public class OthelloImpl implements Othello {
	private BoardImpl board;
	private MoveHandler moveHandler;
	private PlayerWrapper playerWrapper;

	public OthelloImpl(BoardImpl board, PlayerWrapper playerWrapper, MoveHandler moveLogic, Random random) {
		this.board = board;
		this.moveHandler = moveLogic;
		this.playerWrapper = playerWrapper;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Player getPlayerInTurn() {
		return playerWrapper.getPlayerInTurn();
	}

	@Override
	public List<Player> getPlayers() {
		return playerWrapper.getPlayers();
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return moveHandler.hasValidMove(playerId);
	}

	@Override
	public boolean isActive() {
		return moveHandler.hasValidMove(playerWrapper.getPlayerInTurn().getId());
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
		return moveHandler.move();
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		return moveHandler.move(playerId, nodeId);
	}

	@Override
	public void start() {
		playerWrapper.setRandomPlayerInTurn();
	}

	@Override
	public void start(String playerId) {
		playerWrapper.setPlayerInTurn(playerId);
	}

	/**
	 * Gets the number of nodes player has occupant.
	 * 
	 * @param playerId
	 * @return number of nodes
	 */
	public int getNodesOfPlayer(String playerId) {
		int n = 0;
		for (Node node : getBoard().getNodes()) {
			if (node.getOccupantPlayerId().equals(playerId)) {
				n++;
			}
		}
		return n;
	}

	@Override
	public Score getScore() {
		// TODO Auto-generated method stub
		return null;
	}

}
