package kth.game.othello.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		List<OthelloMove> moves = getValidMoves(playerId, nodeId);
		List<Node> swapped = new ArrayList<Node>();
		for (OthelloMove m : moves) {
			swapped.addAll(m.getIntermediateNodes());
		}
		return swapped;

	}

	public List<OthelloMove> getValidMoves(String playerId) {
		List<OthelloMove> moves = new ArrayList<OthelloMove>();
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
	public List<OthelloMove> getValidMoves(String playerId, String nodeId) {
		// The valid moves
		List<OthelloMove> moves = new ArrayList<OthelloMove>();

		// Try every direction from the target node
		Node targetNode = board.getNode(nodeId);
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
				boolean nodeIsMine = !nodeIsOpponent;

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

	public List<Node> makeRandomValidMove(String playerId, Random random) {
		// Check if any valid move is possible
		List<OthelloMove> moves = getValidMoves(playerId);
		
		if (moves.isEmpty()) {
			return new ArrayList<Node>();
		} else {
			// Make a random move
			OthelloMove move = moves.get(random.nextInt(moves.size()));
			
			List<Node> swappedNodes = getNodesToSwap(playerId, move.getMovedToNode().getId());
			
			// The occupied nodes is the swapped nodes and the node move to
			List<Node> occupiedNodes = new ArrayList<Node>(swappedNodes);
			occupiedNodes.add(move.getMovedToNode());
			
			// Change the board
			board.changeOccupantOnNodes(occupiedNodes, playerId);
			
			// Return the nodes that were swapped
			return move.getIntermediateNodes();
		}
	}

}
