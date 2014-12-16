package kth.game.othello.tournament;

import java.util.HashMap;

/**
 * The responsability of tournament is to provide an API for tournaments.
 */
public interface Tournament {
	/**
	 * Start a tournament
	 */
	public void start();

	/**
	 * @return the score of the tournament
	 */
	public HashMap<String, Integer> getScore();

}
