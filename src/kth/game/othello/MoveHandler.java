package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Node;
import kth.game.othello.board.OthelloBoard;
import kth.game.othello.board.OthelloMove;
import kth.game.othello.player.OthelloPlayer;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;

/**
 * This is a helper class that contains the logic when players make moves.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveHandler {

	private OthelloBoard board;
	private Player black;
	private Player white;
	private boolean isBlackTurn;
	private Random random;

	/**
	 * List with all directions to move, in following order{up, upright, right, rightdown, down, downleft, left, upleft}
	 */
	private static final int[][] directions = { { 0, 1 }, { 1, 1 }, { 1, 0 }, { 1, -1 }, { 0, -1 }, { -1, -1 },
			{ -1, 0 }, { -1, 1 } };

	public MoveHandler(OthelloBoard board, OthelloPlayer black, OthelloPlayer white, Random random) {
		this.board = board;
		this.black = black;
		this.white = white;
		this.random = random;
	}

	public List<Node> move() {
		// If the current player is not a computer
		if (getPlayerInTurn().getType() != Player.Type.COMPUTER) {
			throw new IllegalStateException("Player in turn is not a computer.");
		}
		String playerId = getPlayerInTurn().getId();

		// Make a random move
		List<OthelloMove> moves = getMoves(playerId);
		if (moves.isEmpty()) {
			return new ArrayList<Node>();
		}
		// Pick a random move
		OthelloMove move = moves.get(random.nextInt(moves.size()));

		return move(playerId, move.getEndNode().getId());
	}
	
	/**
	 * Makes a move given a player, node id and updates the board.
	 * 
	 * @param playerId
	 * 		the player's id
	 * @param nodeId
	 * 		the node's id
	 * @return Empty list if the move is invalid
	 * @return the nodes that where swapped for this move, including the node where the player made the move
	 */
	public List<Node> move(String playerId, String nodeId) {
		if (!getPlayerInTurn().getId().equals(playerId)) {
			throw new IllegalArgumentException("Given player not in turn.");
		}
	
		List<Node> nodes = getNodesToSwap(playerId, nodeId);
		if (nodes.isEmpty()) {
			throw new IllegalArgumentException("Move is not valid.");
		}
		
		changePlayersTurn();
		board.changeOccupantOnNodes(nodes, playerId);

		// Return the nodes that were swapped and the start node
		nodes.add(0, board.getNode(nodeId));
		return nodes;
	}

	/**
	 * Returns the nodes that will be swapped for a move at the given nodeId.
	 * 
	 * @param playerId
	 * 		the player's id
	 * @param nodeId
	 * 		the node's id
	 * @return the nodes that where swapped for this move, including the node where the player made the move, for a move
	 *         to a specific nodeID
	 */
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
	
	/**
	 * Checks if a player has a valid move.
	 * 
	 * @param playerId
	 * @return true if the player has a valid move
	 */
	public boolean hasValidMove(String playerId) {
		List<OthelloMove> moves = new ArrayList<OthelloMove>();
		for (Node node : board.getNodes()) {
			moves.addAll(getMoves(playerId, node.getId()));
		}
		return moves.size() > 0;
	}

	/**
	 * Checks if a move is valid for a player.
	 * 
	 * @param playerId
	 * @param nodeId
	 * @return if it's valid for playerId to move to NodeId or not.
	 */
	public boolean isMoveValid(String playerId, String nodeId) {
		return getMoves(playerId, nodeId).size() > 0;
	}

	/**
	 * Retrieves all the moves that a player can do.
	 * 
	 * @param playerId
	 * @return list of moves for the playerId
	 */
	private List<OthelloMove> getMoves(String playerId) {
		List<OthelloMove> moves = new ArrayList<OthelloMove>();
		for (Node node : board.getNodes()) {
			moves.addAll(getMoves(playerId, node.getId()));
		}
		return moves;
	}

	/**
	 * For a specific node, this method return all the moves that could lead to that node. By from the specific node go
	 * in all directions and looking for a node that have the same playerId. All the nodes between the specific node the
	 * eventually findings must have the opponents playerId
	 * 
	 * @param playerId
	 * @param nodeId
	 * @return list of moves, where every move leads to a valid "placement" on the nodeId.
	 */
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

	public Player getPlayerInTurn() {
		return isBlackTurn ? black : white;
	}

	public void setPlayerInTurn(String playerId) {
		isBlackTurn = black.getId().equals(playerId);
	}

	private void changePlayersTurn() {
		isBlackTurn = !isBlackTurn;
	}

}
