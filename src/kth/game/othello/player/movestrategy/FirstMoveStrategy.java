package kth.game.othello.player.movestrategy;

import kth.game.othello.board.Board;
import kth.game.othello.board.Node;
import kth.game.othello.rules.Rules;

/**
 * A move strategy where the first move from all possible moves will be chosen.
 *
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 * 
 */
public class FirstMoveStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "first move strategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		// TODO come up with a new strategy
		return null;
	}

}
