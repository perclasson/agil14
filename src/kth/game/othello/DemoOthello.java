package kth.game.othello;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.player.OthelloPlayer;
import kth.game.othello.player.Player;

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
		return moveLogic.getValidMoves(playerId).size() > 0;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		return moveLogic.getValidMoves(playerId, nodeId).size() > 0;
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
		return moveLogic.getRandomValidMove(getPlayerInTurn().getId(), random);
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		if (!getPlayerInTurn().getId().equals(playerId)) {
			throw new IllegalArgumentException("Given player not in turn.");
		}
		List<Node> nodes = getNodesToSwap(playerId, nodeId);
		if (nodes.isEmpty()) {
			throw new IllegalArgumentException();
		} else {
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
}
