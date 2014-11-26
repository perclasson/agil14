package kth.game.othello.player.movestrategy;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.move.Move;
import kth.game.othello.move.MoveCalculator;

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
	public Node move(String playerId, Othello othello) {
		MoveCalculator moveCalculator = new MoveCalculator(othello.getBoard());
		List<Move> moves = moveCalculator.getAllPossibleMoves(playerId);
		return moves.get(0).getEndNode();
	}

}
