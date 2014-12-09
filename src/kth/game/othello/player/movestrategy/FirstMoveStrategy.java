package kth.game.othello.player.movestrategy;

import java.util.ArrayList;
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
public class FirstMoveStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "first move strategy";
	}

	@Override
	public Node move(String playerId, Rules rules, Board board) {
		for (Node node : board.getNodes()) {
			if (rules.getNodesToSwap(playerId, node.getId()).size() > 0) {
				// A move was found, return it
				return node;
			}
		}
		return null;
	}

}
