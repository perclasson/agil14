package kth.game.othello.player.move.strategy;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * FirstMove is a {@link kth.game.othello.player.move.strategy.MoveStrategy} which makes the first possible move
 * regardless if it is good or not.
 */
public class FirstMove implements MoveStrategy {

	@Override
	public String getName() {
		return "Better Quick than Wise";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		return getFirstValidMoveNode(playerId, rules, board);
	}

	/**
	 * Returns the node id of the first valid move for the player, null if not found.
	 *
	 * @param playerId the player.
	 * @param rules the rules for moving and swapping
	 * @param board the board on which the game is played
	 * @return the node id where to move, null if not found.
	 */
	private Node getFirstValidMoveNode(String playerId, Rules rules, Board board) {
		List<Node> nodes = board.getNodes();
		for (Node node : nodes) {
			if (rules.isMoveValid(playerId, node.getId())) {
				return node;
			}
		}
		return null;
	}
}
