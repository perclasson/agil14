package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A move strategy where the first move from all possible moves will be chosen.
 * The first move will be selected from the
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 * 
 */
public class MinSwappedStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "min swapped strategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		List<Node> nodes = board.getNodes();
		int minSwapSize = Integer.MAX_VALUE;
		Node minNode = null;
		for (Node node : nodes) {
			List<Node> swapped = rules.getNodesToSwap(playerId, node.getId());
			if (swapped.size() > 0 && swapped.size() < minSwapSize) {
				minSwapSize = swapped.size();
				minNode = node;
			}
		}
		return minNode;
	}

}
