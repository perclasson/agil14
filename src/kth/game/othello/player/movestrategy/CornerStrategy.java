package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A move strategy where the moves near the corners are chosen.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class CornerStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "corner strategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		List<Node> nodes = board.getNodes();
		double bestSwap = 0;

		Node bestNode = null;
		for (Node node : nodes) {

			List<Node> swapped = rules.getNodesToSwap(playerId, node.getId());
			double swapEval = evaluateNode(node, board);
			if (swapped.size() > 0 && swapEval > bestSwap) {
				bestSwap = swapEval;
				bestNode = node;
			}
		}
		return bestNode;
	}

	private double evaluateNode(Node node, Board board) {
		double x = (double) node.getXCoordinate();
		double y = (double) node.getYCoordinate();
		double meanX = ((double) board.getMaxX()) / 2.0d;
		double meanY = ((double) board.getMaxY()) / 2.0d;

		return Math.pow((x - meanX), 2.0d) + Math.pow((y - meanY), 2.0d);
	}
}
