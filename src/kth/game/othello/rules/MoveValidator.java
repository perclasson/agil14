package kth.game.othello.rules;

import java.util.List;

import kth.game.othello.board.BoardImpl;
import kth.game.othello.board.Node;
import kth.game.othello.player.Player;

/**
 * The responsibility of MoveValidator is to validate a player has any valid moves. It can also determine if the game is
 * still active or not.
 */
public class MoveValidator {

	private BoardImpl board;
	private SwapHandler swapHandler;

	public MoveValidator(BoardImpl board, SwapHandler swapHandler) {
		this.board = board;
		this.swapHandler = swapHandler;
	}

	/**
	 * {@link kth.game.othello.rules.RulesImpl#isMoveValid(String, String)}
	 */
	public boolean isMoveValid(String playerId, String nodeId) {
		Node currentNode = board.getNode(nodeId);

		if (currentNode.isMarked()) {
			return false;
		}

		List<Node> nodesToSwap = swapHandler.getNodesToSwap(playerId, nodeId, board);
		if (nodesToSwap.size() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * {@link kth.game.othello.rules.RulesImpl#hasValidMove(String)}
	 */
	public boolean hasValidMove(String playerId) {
		List<Node> nodes = board.getNodes();
		for (Node node : nodes) {
			if (isMoveValid(playerId, node.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * {@link kth.game.othello.rules.RulesImpl#isActive(java.util.List)}
	 */
	public boolean isActive(List<Player> players) {
		for (Player player : players) {
			if (hasValidMove(player.getId())) {
				return true;
			}
		}
		return false;
	}
}
