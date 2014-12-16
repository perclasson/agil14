package kth.game.othello.player.move.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * RandomMove is a {@link kth.game.othello.player.move.strategy.MoveStrategy} that moves at a random position.
 */
public class RandomMove implements MoveStrategy {

	Random random;

	public RandomMove(Random random) {
		this.random = random;
	}

	@Override
	public String getName() {
		return "Lucky Goldstar";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		return getRandomMove(playerId, rules, board);
	}

	/**
	 * Returns a random valid move.
	 *
	 * @param playerId the id of the player that will make a move.
	 * @param rules the rules for moving and swapping
	 * @param board the board on which the game is played
	 * @return a random valid node to move to.
	 */
	private Node getRandomMove(String playerId, Rules rules, Board board) {
		List<Node> nodes = board.getNodes();
		List<Node> possibleMoves = new ArrayList<Node>();
		for (Node node : nodes) {
			if (rules.isMoveValid(playerId, node.getId())) {
				possibleMoves.add(node);
			}
		}
		if (possibleMoves.size() == 0) {
			return null;
		}
		return possibleMoves.get(random.nextInt(possibleMoves.size()));
	}
}
