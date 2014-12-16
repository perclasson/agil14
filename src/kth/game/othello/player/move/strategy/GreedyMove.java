package kth.game.othello.player.move.strategy;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * GreedyMove is a {@link kth.game.othello.player.move.strategy.MoveStrategy} that always moves at the position which
 * results in most nodes swapped.
 */
public class GreedyMove implements MoveStrategy {

	@Override
	public String getName() {
		return "Greedy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		return greedyMove(playerId, rules, board);
	}

	/**
	 * Returns the move which results in the most swapped opponent nodes.
	 *
	 * @param playerId the id of the player that will make a move.
	 * @param rules the rules for moving and swapping
	 * @param board the board on which the game is played
	 * @return the node where the player should move to swap as many nodes as possible of the opponents nodes.
	 */
	private Node greedyMove(String playerId, Rules rules, Board board) {
		List<Node> nodes = board.getNodes();
		Node greedyNode = null;
		int maxNodesToSwap = 0;
		int tempNodesToSwap = 0;
		for (Node node : nodes) {
			if (rules.isMoveValid(playerId, node.getId())) {
				tempNodesToSwap = rules.getNodesToSwap(playerId, node.getId()).size();
				if (tempNodesToSwap > maxNodesToSwap) {
					maxNodesToSwap = tempNodesToSwap;
					greedyNode = node;
				}
			}
		}
		return greedyNode;
	}
}
