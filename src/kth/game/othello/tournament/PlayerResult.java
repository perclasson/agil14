package kth.game.othello.tournament;

import java.util.HashMap;
import java.util.List;

import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;

/*
 * This class represents the result of players in a tournament.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class PlayerResult {

	private HashMap<String, Integer> results;

	/**
	 * Creates a Result object that represents the result of players in a
	 * tournament.
	 */
	public PlayerResult() {
		this.results = new HashMap<String, Integer>();
	}

	/**
	 * Adds the score to the result. A player that has won the game will be
	 * given a score. A draw does not give any points.
	 * 
	 * @param score
	 *            the score that should be added to the result.
	 */
	public void add(Score score) {
		List<ScoreItem> scores = score.getPlayersScore();

		int bestScore = 0;
		String winningPlayerId = null;
		boolean draw = false;
		for (ScoreItem scoreItem : scores) {
			if (scoreItem.getScore() > bestScore) {
				bestScore = scoreItem.getScore();
				draw = false;
				winningPlayerId = scoreItem.getPlayerId();
			} else if (scoreItem.getScore() == bestScore) {
				draw = true;
			}
		}

		// Unless it was a draw, update the player score
		if (!draw) {
			incrementScore(winningPlayerId);
		}

	}

	/**
	 * Returns a string that contains the score for each player given.
	 * 
	 * @param players
	 *            the players that will be included in the result.
	 * @return a string containing the result of the players.
	 */
	public String getResult(List<Player> players) {
		StringBuilder sb = new StringBuilder();
		sb.append("Tournament results: " + System.lineSeparator());
		for (Player player : players) {
			sb.append(player.getName() + ", score: " + results.get(player.getId()) + System.lineSeparator());
		}
		return sb.toString();
	}

	private void incrementScore(String playerId) {
		Integer newScore = results.get(playerId) == null ? 0 : results.get(playerId) + 1;
		results.put(playerId, newScore);
	}
}
