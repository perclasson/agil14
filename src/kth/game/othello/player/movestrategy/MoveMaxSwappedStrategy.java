package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.move.Move;
import kth.game.othello.move.MoveCalculator;

public class MoveMaxSwappedStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "move max swapped strategy";
	}

	/**
	 * Strategy where the move that leads to most changes of occupantplayer will be swapped.
	 */
	@Override
	public Node move(String playerId, Othello othello) {
		// FIXME
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
