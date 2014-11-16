package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This is a helper class that contains the logic when players make moves.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveLogic {

	private OthelloBoard board;

	// ↑ ↗ → ↘ ↓ ↙ ← ↖
	private static final int[][] directions = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 },
			{ -1, 0 }, { -1, 1 } };

	public MoveLogic(OthelloBoard board) {
		this.board = board;
	}

	public List<Node> getNodesToSwap(String playerId, String nodeId) {
		List<OthelloMove> moves = getMoves(playerId, nodeId);
		List<Node> swappedNodes = new ArrayList<Node>();
		for (OthelloMove move : moves) {
			swappedNodes.addAll(move.getIntermediateNodes());
		}
		// Add the last node
		if (swappedNodes.size() > 0) {
			swappedNodes.add(board.getNode(nodeId));
		}
		return swappedNodes;
	}

	public List<Node> randomMove(String playerId, Random random) {
		List<OthelloMove> moves = getMoves(playerId);
		if (moves.isEmpty()) {
			return new ArrayList<Node>();
		}

		// Pick a random move
		OthelloMove move = moves.get(random.nextInt(moves.size()));

		return move(playerId, move.getMovedToNode().getId());
	}

	public List<Node> move(String playerId, String nodeId) {
		List<Node> nodes = getNodesToSwap(playerId, nodeId);
		if (nodes.isEmpty()) {
			return new ArrayList<Node>();
		}
		board.changeOccupantOnNodes(nodes, playerId);

		// Return the nodes that were swapped and the start node
		nodes.add(0, board.getNode(nodeId));
		return nodes;
	}

	public boolean hasValidMove(String playerId) {
		List<OthelloMove> moves = new ArrayList<OthelloMove>();
		for (Node node : board.getNodes()) {
			moves.addAll(getMoves(playerId, node.getId()));
		}
		return moves.size() > 0;
	}

	public boolean isMoveValid(String playerId, String nodeId) {
		return getMoves(playerId, nodeId).size() > 0;
	}

	private List<OthelloMove> getMoves(String playerId) {
		List<OthelloMove> moves = new ArrayList<OthelloMove>();
		for (Node node : board.getNodes()) {
			moves.addAll(getMoves(playerId, node.getId()));
		}
		return moves;
	}

	private List<OthelloMove> getMoves(String playerId, String nodeId) {
		// The valid moves
		List<OthelloMove> moves = new ArrayList<OthelloMove>();

		// Try every direction from the target node
		Node targetNode = board.getNode(nodeId);

		// Check that the target node is not already occupant
		if (targetNode.isMarked()) {
			return moves;
		}

		for (int[] direction : directions) {
			int x = targetNode.getXCoordinate();
			int y = targetNode.getYCoordinate();

			ArrayList<Node> visitedNodes = new ArrayList<Node>();

			// Follow the direction
			OthelloMove currentMove = null;
			while (true) {
				x += direction[0];
				y += direction[1];

				if ((x < 0 || x >= board.getOrder()) || (y < 0 || y >= board.getOrder())) {
					break;
				}

				Node node = board.getNodeByCoordinates(x, y);

				boolean nodeIsOpponent = node.isMarked() && !node.getOccupantPlayerId().equals(playerId);
				boolean nodeIsMine = node.isMarked() && node.getOccupantPlayerId().equals(playerId);

				if (nodeIsOpponent) {
					visitedNodes.add(node);
				} else if (visitedNodes.size() > 0 && nodeIsMine) {
					// The move was valid
					currentMove = new OthelloMove(node, targetNode, visitedNodes);
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
}
