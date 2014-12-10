package kth.game.othello.tournament.gameinstance;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.score.Score;

/**
 * A strategy that can be implemented to play the Othello game.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public interface GamePlayStrategy {

	/**
	 * Plays the given game of Othello with the starting player and returns the
	 * score of the game.
	 * 
	 * @param othello
	 *            The game to be played.
	 * @param startingPlayer
	 *            Starting player in the game.
	 * @return The score.
	 */
	Score play(Othello othello, Player startingPlayer);

}
