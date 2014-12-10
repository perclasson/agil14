package kth.game.othello.tournament.gameinstance;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;

/**
 * Represents a game instance of Othello.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class GameInstance {
	private Othello othello;
	private Player startingPlayer;
	private GamePlayStrategy gamePlayStrategy;

	/**
	 * Creates an GameInstance object that represents an instance of Othello
	 * with a game and a starting player.
	 * 
	 * @param othello
	 *            The game of Othello.
	 * @param startingPlayer
	 *            The starting player of the game.
	 * @param gamePlayStrategy
	 *            The game play strategy.
	 */
	public GameInstance(Othello othello, Player startingPlayer, GamePlayStrategy gamePlayStrategy) {
		this.othello = othello;
		this.startingPlayer = startingPlayer;
		this.gamePlayStrategy = gamePlayStrategy;
	}

	/**
	 * The game of Othello in this instance.
	 * 
	 * @return The game of Othello.
	 */
	public Othello getOthello() {
		return othello;
	}

	/**
	 * The starting player of this instance.
	 * 
	 * @return The starting player.
	 */
	public Player getStartingPlayer() {
		return startingPlayer;
	}

	/**
	 * The game play strategy that should be used for this instance.
	 * 
	 * @return The game play strategy.
	 */
	public GamePlayStrategy getPlayStrategy() {
		return gamePlayStrategy;
	}

}
