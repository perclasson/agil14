package kth.game.othello.tournament.gameinstance;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

/**
 * A strategy to play the othello game using the OthelloView.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class ViewPlayStrategy implements GamePlayStrategy {
	private static int TIME_BETWEEN_SWAPS = 0;
	private static int TIME_BETWEEN_MOVES = 1000;

	@Override
	public Score play(Othello othello, Player startingPlayer) {
		OthelloView view = OthelloViewFactory.create(othello, TIME_BETWEEN_SWAPS, TIME_BETWEEN_MOVES);
		othello.start(startingPlayer.getId());
		view.start();
		return othello.getScore();
	}
}