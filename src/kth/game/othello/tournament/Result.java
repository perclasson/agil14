package kth.game.othello.tournament;

import java.util.HashMap;
import java.util.List;

import kth.game.othello.player.Player;
import kth.game.othello.score.Score;
import kth.game.othello.score.ScoreItem;

public class Result {

	private HashMap<String, Integer> results;

	public Result() {
		this.results = new HashMap<String, Integer>();
	}

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

	public String getResultString(List<Player> players) {
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
