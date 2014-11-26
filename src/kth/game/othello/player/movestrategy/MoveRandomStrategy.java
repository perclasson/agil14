package kth.game.othello.player.movestrategy;

import java.util.List;
import java.util.Random;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.move.Move;
import kth.game.othello.move.MoveCalculator;

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
	public Node move(String playerId, Othello othello) {
		MoveCalculator moveCalculator = new MoveCalculator(othello.getBoard());
		List<Move> moves = moveCalculator.getAllPossibleMoves(playerId);
		Node target = moves.get(random.nextInt(moves.size())).getEndNode();
		return target;
	}

}
