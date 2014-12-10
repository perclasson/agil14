package kth.game.othello.tournament;

import java.util.List;
import kth.game.othello.score.Score;
import kth.game.othello.tournament.gameinstance.GameInstance;
import kth.game.othello.tournament.gameinstance.GamePlayStrategy;

/**
 * This class represents a tournament of Othello.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class Tournament {
	private List<GameInstance> gameInstances;
	private PlayerResult result;

	/**
	 * Creates a Tournament object that can be played using the given data.
	 * 
	 * @param gameInstances
	 *            The game instances that will be played during the tournament.
	 * @param result
	 *            The result object that will contain the result of the
	 *            tournament.
	 */
	public Tournament(List<GameInstance> gameInstances, PlayerResult result) {
		this.gameInstances = gameInstances;
		this.result = result;
	}

	/**
	 * Plays the tournament.
	 * 
	 * @return The result of the tournament.
	 */
	public PlayerResult play() {
		for (GameInstance game : gameInstances) {
			GamePlayStrategy playStrategy = game.getPlayStrategy();
			Score score = playStrategy.play(game.getOthello(), game.getStartingPlayer());
			result.add(score);
		}
		return result;
	}
}
