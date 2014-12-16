package kth.game.othello.tournament;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import kth.game.othello.player.Player;

/**
 * The responsibility of a TwoPlayerTournament is to print messages in a TwoPlayerTournament.
 */
public class TwoPlayerTournamentAnnouncer {

	/**
	 * Print an intro message
	 * 
	 * @param players players to participate
	 */
	public void printIntro(List<Player> players) {
		System.out.println("Good evening Ladies and Gentlemen!");
		String competitors = "";
		for (Player player : players) {
			competitors = competitors + "\n" + player.getName();
		}
		System.out.println("Today's tournament is between:" + competitors);
	}

	/**
	 * Print choice alternatives with a preceding number
	 * 
	 * @param alternatives the alternatives to print
	 */
	public void printViewAlternatives(String[] alternatives) {
		System.out.println();
		System.out.println("You can choose between:");
		for (int i = 0; i < alternatives.length; i++) {
			System.out.print("\n" + (i + 1) + ". " + alternatives[i]);
		}
		System.out.println();
	}

	/**
	 * Print the result of a game
	 * 
	 * @param victoriousPlayer the victorious player
	 * @param loosingPlayer the loosing player
	 * @param random a random object
	 */
	public void printVictory(Player victoriousPlayer, Player loosingPlayer, Random random) {
		String[] phrases = new String[] { " was stomped into the carpet by ", " was annihilated by ",
				" lost an epic battle against ", " was defeated by " };
		System.out.println(loosingPlayer.getName() + phrases[random.nextInt(4)] + victoriousPlayer.getName());
	}

	/**
	 * Print a tie message
	 * 
	 * @param player1 tied player 1
	 * @param player2 tied player 2
	 */
	public void printTie(Player player1, Player player2) {
		System.out.println(player1.getName() + " and " + player2.getName() + " are tied");
	}

	/**
	 * Print the given score with corresponding player
	 * 
	 * @param scores the score of the tournament
	 * @param players the players of the tournament
	 */
	public void printScore(HashMap<String, Integer> scores, List<Player> players) {
		System.out.println("\nThe score is:");
		for (String playerId : scores.keySet()) {
			for (Player player : players) {
				if (player.getId().equals(playerId)) {
					System.out.println("\t" + player.getName() + ": " + scores.get(playerId));
				}
			}
		}
	}
}
