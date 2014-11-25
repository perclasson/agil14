package kth.game.othello.player.movestrategy;

import java.util.List;
import java.util.Random;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.move.Move;
import kth.game.othello.move.MoveCalculator;

public class MoveRandomStrategy implements MoveStrategy {

	private Random random;

	public MoveRandomStrategy(Random random) {
		this.random = random;
	}

	@Override
	public String getName() {
		return "move random strategy";
	}

	/**
	 * Strategy where the move is random
	 */

	@Override
	public Node move(String playerId, Othello othello) {
		// FIXME: I do not know any better way than to create a move calculator
		// here.
		// Issue:
		// 1. Player wants a move strategy
		// 2. The move strategy needs a calculator
		// 3. Calculator needs a board
		// 4. Board need players to be created
		// Solution, let moveCalculator be injected here
		MoveCalculator moveCalculator = new MoveCalculator(othello.getBoard());
		List<Move> moves = moveCalculator.getAllPossibleMoves(playerId);
		Node target = moves.get(random.nextInt(moves.size())).getEndNode();
		return target;
	}

}
