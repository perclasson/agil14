package kth.game.othello.player.movestrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A move strategy where the move is randomly made.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class MoveRandomStrategy implements MoveStrategy {

	private Random random;

	public MoveRandomStrategy(Random random) {
		this.random = random;
	}

	@Override
	public String getName() {
		return "move random strategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		List<Node> nodes = getPossibleMoves(playerId, rules, board);
		if (nodes.size() > 0) {
			return nodes.get(random.nextInt(nodes.size()));
		} else {
			return null;
		}
	}

	private List<Node> getPossibleMoves(String playerId, Rules rules, Board board) {
		List<Node> nodes = new ArrayList<Node>();
		for (Node node : board.getNodes()) {
			List<Node> swapped = rules.getNodesToSwap(playerId, node.getId());
			if (swapped.size() > 0) {
				nodes.add(node);
			}
		}
		return nodes;
	}

}
