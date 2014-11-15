package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;

public class MoveLogic {

	private OthelloBoard board;

	// ↑ ↗ → ↘ ↓ ↙ ← ↖
	private static final int[][] directions = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 },
			{ -1, 0 }, { -1, 1 } };

	public MoveLogic(OthelloBoard board) {
		this.board = board;
	}

	/**
	 * 
	 * @param playerId
	 * @param nodeId
	 * @return
	 */
	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		List<Move> moves = getValidMoves(playerId, nodeId);
		List<Node> swapped = new ArrayList<Node>();
		for (Move m : moves) {
			swapped.addAll(m.getIntermediateNodes());
		}
		return swapped;

	}

	public List<Move> getValidMoves(String playerId) {
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
	public List<Move> getValidMoves(String playerId, String nodeId) {
		// The valid moves
		List<Move> moves = new ArrayList<Move>();

		// Try every direction from the target node
		Node targetNode = board.getNode(nodeId);
		for (int[] direction : directions) {
			int x = targetNode.getXCoordinate();
			int y = targetNode.getYCoordinate();

			ArrayList<Node> visitedNodes = new ArrayList<Node>();

			// Follow the direction
			Move currentMove = null;
			while (true) {
				x += direction[0];
				y += direction[1];

				if ((x < 0 || x >= board.getOrder()) || (y < 0 || y >= board.getOrder())) {
					break;
				}

				Node node = board.getNodeByCoordinates(x, y);

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


	public List<Node> getRandomValidMove(String playerId, Random random) {
		List<Move> moves = getValidMoves(playerId);

		if (moves.isEmpty()) {
			return new ArrayList<Node>();
		} else {
			// Let the computer make a random move
			Move move = moves.get(random.nextInt(moves.size()));

			// Return the number of nodes that were swapped
			return getNodesToSwap(playerId, move.getMovedToNode().getId());
		}
	}

}
