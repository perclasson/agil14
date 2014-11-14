package kth.game.othello;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;

public class MoveLogic {

	private OthelloBoard board;

	int[][] directions = { { 0, 1 }, // ↑
			{ 1, 1 }, // ↗
			{ 1, 0 }, // →
			{ 1, -1 }, // ↘
			{ 0, -1 }, // ↓
			{ -1, -1 }, // ↙
			{ -1, 0 }, // ←
			{ -1, 1 } // ↖
	};

	MoveLogic(OthelloBoard board) {
		this.board = board;
	}

	List<Move> getValidMoves(String playerId) {
		List<Move> moves = new ArrayList<Move>();
		for (Node node : board.getNodes()) {
			moves.addAll(getValidMoves(playerId, node.getId()));
		}
		return moves;
	}

	/**
	 * 
	 * @param playerId
	 * @param nodeId
	 * @return Return list with all valid moves.
	 */
	List<Move> getValidMoves(String playerId, String nodeId) {
		// The valid moves
		List<Move> moves = new ArrayList<Move>();

		// Try every direction from the target node
		Node targetNode = getNode(nodeId);
		for (int[] direction : directions) {
			int x = targetNode.getXCoordinate();
			int y = targetNode.getYCoordinate();

			ArrayList<Node> visitedNodes = new ArrayList<Node>();

			// Follow the direction
			Move currentMove = null;
			while (true) {
				x += direction[0];
				y += direction[1];

				if ((x < 0 || x > board.getOrder()) || (y < 0 || y > board.getOrder())) {
					break;
				}

				Node node = getNodeByCoordinates(x, y);

				boolean nodeIsOpponent = node.isMarked() && !node.getOccupantPlayerId().equals(playerId);
				boolean nodeIsMine = !nodeIsOpponent;

				if (nodeIsOpponent) {
					visitedNodes.add(node);
				} else if (visitedNodes.size() > 0 && nodeIsMine) {
					// The move was valid
					currentMove = new Move(node, targetNode, visitedNodes);
					break;
				} else {
					break;
				}
			}

			if (currentMove != null) {
				moves.add(currentMove);
			}

		}

		return moves;
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
		return board.getNodes().get(y * board.getOrder() + x);
	}
}
