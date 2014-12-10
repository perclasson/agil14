package kth.game.othello.tournament;

import kth.game.othello.score.Score;

public interface Result {

	/**
	 * Adds a score to the result.
	 * 
	 * @param score
	 *            the score that should be added to the result.
	 */
	public void add(Score score);

	/**
	 * Returns a string that contains information about the result.
	 * 
	 * @return a string containing the result.
	 */
	public String getResult();
}
