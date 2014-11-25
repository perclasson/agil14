package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.move.Move;
import kth.game.othello.move.MoveCalculator;

/**
 * The responsibility of this class is to choose moves. This one chooses moves that maximises swaps.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class MaxSwappedStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "move max swapped strategy";
	}

	@Override
	public Node move(String playerId, Othello othello) {
		MoveCalculator moveCalculator = new MoveCalculator(othello.getBoard());
		List<Move> moves = moveCalculator.getAllPossibleMoves(playerId);
		Move maxMove = null;
		int maxSwapped = 0;
		for (Move move : moves) {
			int swaps = moveCalculator.getNodesToSwap(playerId, move.getEndNode().getId()).size();
			if (swaps > maxSwapped) {
				maxSwapped = swaps;
				maxMove = move;
			}
		}
		return maxMove.getEndNode();
	}

}
