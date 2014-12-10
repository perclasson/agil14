package kth.game.othello.tournament.gameinstance;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;

/**
 * A strategy to play the othello game between computer players.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class ComputerPlayStrategy implements GamePlayStrategy {

	@Override
	public Score play(Othello othello, Player startingPlayer) {
		othello.start(startingPlayer.getId());
		while (othello.isActive()) {
			othello.move();
		}
		return othello.getScore();
	}

}
