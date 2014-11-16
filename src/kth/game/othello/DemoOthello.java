package kth.game.othello;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.MoveLogic;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
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
	private boolean isBlackTurn;
	private boolean isActive;
	private Random random;
	private MoveLogic moveLogic;

	public DemoOthello(OthelloBoard board, OthelloPlayer black, OthelloPlayer white, MoveLogic moveLogic, Random random) {
		this.board = board;
		this.black = black;
		this.white = white;
		this.moveLogic = moveLogic;
		this.random = random;
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public Player getPlayerInTurn() {
		return isBlackTurn ? black : white;
	}

	@Override
	public List<Player> getPlayers() {
		return Arrays.asList(white, black);
	}

	@Override
	public boolean hasValidMove(String playerId) {
		return moveLogic.hasValidMove(playerId);
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return moveLogic.isMoveValid(playerId, nodeId);
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		return moveLogic.getNodesToSwap(playerId, nodeId);
	}

	@Override
	public List<Node> move() {
		// If the current player is not a computer
		if (getPlayerInTurn().getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Player in turn is not a computer.");
		}
		// Make a random move
		List<Node> nodes = moveLogic.randomMove(getPlayerInTurn().getId(), random);
		updateGameState();
		changePlayersTurn();
		return nodes;
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		if (!getPlayerInTurn().getId().equals(playerId)) {
			throw new IllegalArgumentException("Given player not in turn.");
		}

		List<Node> nodes = moveLogic.move(playerId, nodeId);

		if (nodes.isEmpty()) {
			throw new IllegalArgumentException("Move is not valid.");
		} else {
			updateGameState();
			changePlayersTurn();
			return nodes;
		}
	}

	@Override
	public void start() {
		isBlackTurn = random.nextBoolean();
		isActive = true;
	}

	@Override
	public void start(String playerId) {
		isBlackTurn = black.getId().equals(playerId);
		isActive = true;
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

	/**
	 * Checks if the game is active, and updates the internal state;
	 */
	private void updateGameState() {
		// Check if the opponent has any moves left
		String playerInTurnId = getPlayerInTurn().getId();
		String playerNextInTurnId = white.getId().equals(playerInTurnId) ? black.getId() : white.getId();
		boolean isMovePossible = moveLogic.hasValidMove(playerNextInTurnId);
		isActive = isMovePossible;
	}

	/**
	 * Changes the state of which player is in turn.
	 */
	private void changePlayersTurn() {
		isBlackTurn = !isBlackTurn;
	}
}
