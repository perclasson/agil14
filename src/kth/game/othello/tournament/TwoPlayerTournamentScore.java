package kth.game.othello.tournament;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import kth.game.othello.player.Player;
import kth.game.othello.score.Score;

/**
 * The responsibility of TwoPlayerScore is to keep and calculate the score of a tournament.
 */
public class TwoPlayerTournamentScore {
	HashMap<String, Integer> score;

	public TwoPlayerTournamentScore(List<Player> players) {
		resetScore(players);
	}

	/**
	 * Reset the score
	 *
	 * @param players the players in the game
	 */
	public void resetScore(List<Player> players) {
		score = new HashMap<String, Integer>();
		for (Player player : players) {
			score.put(player.getId(), 0);
		}
	}

	/**
	 * @return Hash map of score
	 */
	public HashMap<String, Integer> getScore() {
		return score;
	}

	/**
	 * Update the tournament score with the score from the given game
	 *
	 * @param player1 player 1
	 * @param player2 player 2
	 * @param gameScore score from the game
	 * @param twoPlayerTournamentAnnouncer announcer to print the result
	 */
	public void updateScoreFromGame(Player player1, Player player2, Score gameScore,
			TwoPlayerTournamentAnnouncer twoPlayerTournamentAnnouncer) {
		int playerScore = gameScore.getPoints(player1.getId());
		int opponentScore = gameScore.getPoints(player2.getId());
		String winnerId;
		if (playerScore < opponentScore) {
			winnerId = player2.getId();
			score.put(winnerId, score.get(winnerId) + opponentScore - playerScore);
			twoPlayerTournamentAnnouncer.printVictory(player2, player1, new Random());
		} else if (playerScore > opponentScore) {
			winnerId = player1.getId();
			score.put(winnerId, score.get(winnerId) + playerScore - opponentScore);
			twoPlayerTournamentAnnouncer.printVictory(player1, player2, new Random());
		} else {
			twoPlayerTournamentAnnouncer.printTie(player1, player2);
		}
	}
}
