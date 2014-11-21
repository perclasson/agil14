package kth.game.othello;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.player.MoveHandler;
import kth.game.othello.player.OthelloPlayer;
import kth.game.othello.player.Player;

/**
 * This class represents a game of Othello.
 * 
 * @author Ludvig Axelsson, Per Classon & Tommy Roshult
 */
public class DemoOthello implements Othello {
	private OthelloBoard board;
	private Player black;
	private Player white;
	private Random random;
	private MoveHandler moveHandler;

	public DemoOthello(OthelloBoard board, OthelloPlayer black, OthelloPlayer white, MoveHandler moveLogic, Random random) {
		this.board = board;
		this.black = black;
		this.white = white;
		this.moveHandler = moveLogic;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Player getPlayerInTurn() {
		return moveHandler.getPlayerInTurn();
	}

	@Override
	public List<Player> getPlayers() {
		return Arrays.asList(white, black);
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return moveHandler.hasValidMove(playerId);
	}

	@Override
	public boolean isActive() {
		return moveHandler.hasValidMove(moveHandler.getPlayerInTurn().getId());
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
		moveHandler.setPlayerInTurn(random.nextBoolean() ? black.getId() : white.getId());
	}

	@Override
	public void start(String playerId) {
		moveHandler.setPlayerInTurn(playerId);
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

}
