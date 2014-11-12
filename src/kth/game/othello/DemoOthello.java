package kth.game.othello;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

public class DemoOthello implements Othello {

	private Board board;
	private Player black;
	private Player white;
	private boolean isBlackTurn;
	private int boardOrder;

	public DemoOthello(Board board, Player black, Player white) {
		this.board = board;
		this.black = black;
		this.white = white;
		boardOrder = (int) Math.sqrt(board.getNodes().size());
	}

	@Override
	public Board getBoard() {
		return board;
	}

	@Override
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		if (!isMoveValid(playerId, nodeId)) {
			return new ArrayList<Node>();
		}

		Node node = getNode(nodeId);
		int x = node.getXCoordinate();
		int y = node.getYCoordinate();
		int[][] deltas = { { -1, -1 }, { 0, -1 }, { 1, -1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1 } };
		List<Node> allSwaps = new ArrayList<Node>();

		for (int[] delta : deltas) {
			int deltaInX = delta[0];
			int deltaInY = delta[1];
			int currentX = x;
			int currentY = y;
			List<Node> swaps = new ArrayList<Node>();
			boolean isValidDirection = false;

			while (currentX >= 0 && currentX <= boardOrder && currentY >= 0 && currentY <= boardOrder) {
				currentX += deltaInX;
				currentY += deltaInY;
				Node current = getNodeByCoordinates(currentX, currentY);
				boolean isOpponent = current.isMarked() && !current.getOccupantPlayerId().equals(playerId);
				boolean lastIsMine = swaps.size() > 0 && current.isMarked()
						&& current.getOccupantPlayerId().equals(playerId);
				if (isOpponent) {
					swaps.add(current);
				} else if (lastIsMine) {
					isValidDirection = true;
					break;
				} else {
					break;
				}
			}

			if (isValidDirection) {
				allSwaps.addAll(swaps);
			}
		}

		return allSwaps;
	}

	@Override
	public Player getPlayerInTurn() {
		if (isBlackTurn) {
			return black;
		} else {
			return white;
		}
	}

	@Override
	public List<Player> getPlayers() {
		return Arrays.asList(white, black);
	}

	@Override
	public boolean hasValidMove(String playerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		Node node = getNode(nodeId);
		return false;
	}

	@Override
	public List<Node> move() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Node> move(String playerId, String nodeId) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() {
		Random random = new Random();
		isBlackTurn = random.nextBoolean();
	}

	@Override
	public void start(String playerId) {
		isBlackTurn = black.getId().equals(playerId);
	}

	/**
	 * Get node by id from board, returns null if non-existent node.
	 * 
	 * @param String
	 *            nodeId
	 * @return Node node
	 */
	private Node getNode(String nodeId) {
		for (Node node : board.getNodes()) {
			if (node.getId().equals(nodeId)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Get node by coordinates.
	 * 
	 * @param int x
	 * @param int y
	 * @return Node node
	 */
	private Node getNodeByCoordinates(int x, int y) {
		return board.getNodes().get(y * boardOrder + x);
	}
}
