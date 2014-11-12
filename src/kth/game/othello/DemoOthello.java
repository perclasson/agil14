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
	private boolean isActive;

	public DemoOthello(Board board, Player black, Player white, int boardOrder) {
		this.board = board;
		this.black = black;
		this.white = white;
		this.boardOrder = boardOrder;
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

		Node targetNode = getNode(nodeId);

		// Try every direction from the target node
		int[][] directions = { { 0, 1 }, // ↑
				{ 1, 1 }, // ↗
				{ 1, 0 }, // →
				{ 1, -1 }, // ↘
				{ 0, -1 }, // ↓
				{ -1,  -1 }, // ↙
				{ -1, 0 }, // ←
				{ -1, 1 } // ↖
		};

		// Nodes that will be swapped
		List<Node> swaps = new ArrayList<Node>();

		for (int[] direction : directions) {
			int x = targetNode.getXCoordinate();
			int y = targetNode.getYCoordinate();

			List<Node> currentSwaps = new ArrayList<Node>();
			boolean isValidDirection = false;

			// Follow the direction
			while (true) {
				x += direction[0];
				y += direction[1];

				if ((x < 0 || x > boardOrder) || (y < 0 || y > boardOrder)) {
					break;
				}

				Node node = getNodeByCoordinates(x, y);

				boolean nodeIsOpponent = node.isMarked() && !node.getOccupantPlayerId().equals(playerId);
				boolean nodeIsMine = !nodeIsOpponent;

				if (nodeIsOpponent) {
					currentSwaps.add(node);
				} else if (currentSwaps.size() > 0 && nodeIsMine) {
					isValidDirection = true;
					break;
				} else {
					break;
				}
			}

			if (isValidDirection) {
				swaps.addAll(currentSwaps);
			}
		}
		return swaps;
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
		return isActive;
	}

	@Override
	public boolean isMoveValid(String playerId, String nodeId) {
		// TODO
		return true;
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
		isActive = true;
	}

	@Override
	public void start(String playerId) {
		isBlackTurn = black.getId().equals(playerId);
		isActive = true;
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
