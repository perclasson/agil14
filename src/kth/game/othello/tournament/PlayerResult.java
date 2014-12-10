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
	private List<Player> players;

	/**
	 * Creates a Result object that represents the result of players in a
	 * tournament.
	 * 
	 * @param players
	 *            The players that should be included in the score.
	 */
	public PlayerResult(List<Player> players) {
		this.players = players;
		this.results = new HashMap<String, Integer>();
		for (Player player : players) {
			results.put(player.getId(), 0);
		}
	}

	/**
	 * Adds the score to the result. A player that has won the game will be
	 * given a score. Draw does not give any points.
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
	public String getResult() {
		StringBuilder sb = new StringBuilder();
		sb.append("Tournament results: " + System.lineSeparator());
		for (Player player : players) {
			sb.append(player.getName() + ", score: " + results.get(player.getId()) + System.lineSeparator());
		}
		return sb.toString();
	}

	/**
	 * Returns the score of the given player id.
	 * 
	 * @param playerId
	 *            The player id.
	 * @return The score of the player.
	 */
	public int getPlayerScore(String playerId) {
		return results.get(playerId);
	}

	/**
	 * Returns the players of the result.
	 * 
	 * @return The players.
	 */
	public List<Player> getPlayers() {
		return players;
	}

	private void incrementScore(String playerId) {
		results.put(playerId, results.get(playerId) + 1);
	}

}
