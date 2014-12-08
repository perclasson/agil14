package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A move strategy where the move that leads to most changes of occupant player
 * will be swapped.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MaxSwappedStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "move max swapped strategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		List<Node> nodes = board.getNodes();
		int bestSwapSize = 0;
		Node bestNode = null;
		for (Node node : nodes) {
			List<Node> swapped = rules.getNodesToSwap(playerId, node.getId());
			if (swapped.size() > bestSwapSize) {
				bestSwapSize = swapped.size();
				bestNode = node;
			}
		}
		return bestNode;
	}

}
