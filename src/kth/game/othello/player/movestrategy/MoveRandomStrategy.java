package kth.game.othello.player.movestrategy;

import java.util.List;
import java.util.Random;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.move.Move;
import kth.game.othello.move.MoveCalculator;

public class MoveRandomStrategy implements MoveStrategy {

	@Override
	public String getName() {
		return "move random strategy";
	}

	@Override
	public Node move(String playerId, Othello othello) {
		MoveCalculator mc = new MoveCalculator(othello.getBoard()); // TODO i don't know
		Random rand = new Random();
		List<Move> moves = mc.getMoves(playerId);
		return moves.get(rand.nextInt(moves.size())).getEndNode();
	}

}
