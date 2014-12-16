package kth.game.othello.tournament;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.move.strategy.FirstMove;
import kth.game.othello.player.move.strategy.GreedyMove;
import kth.game.othello.player.move.strategy.RandomMove;
import kth.game.othello.player.move.strategy.TurtleMove;

/**
 * A tournament between the different {@link kth.game.othello.player.move.strategy.MoveStrategy}s. Can run graphically
 * or in the console.
 */
public class MoveStrategyBattle {

	public static void main(String[] args) {

		// Initiate players
		Player gunnarGreedy = PlayerImpl.getComputerPlayer("0", "GunnarGreedy", new GreedyMove());
		Player torkelTurtle = PlayerImpl.getComputerPlayer("1", "TorkelTurtle", new TurtleMove());
		Player frasseFirst = PlayerImpl.getComputerPlayer("2", "FrasseFirst", new FirstMove());
		Player ragnarRandom = PlayerImpl.getComputerPlayer("3", "RagnarRandom", new RandomMove(new Random()));
		List<Player> players = new LinkedList<Player>();
		players.add(gunnarGreedy);
		players.add(torkelTurtle);
		players.add(frasseFirst);
		players.add(ragnarRandom);

		// Start presenting the Battle
		Scanner scanner = new Scanner(System.in);
		TwoPlayerTournamentAnnouncer twoPlayerTournamentAnnouncer = new TwoPlayerTournamentAnnouncer();
		twoPlayerTournamentAnnouncer.printIntro(players);

		String[] alternatives = new String[] { "Watch the Epic Tournament unfold live!",
				"Get the results from the competition instantly..." };
		boolean validSelection = false;
		int index = -1;
		while (!validSelection) {
			twoPlayerTournamentAnnouncer.printViewAlternatives(alternatives);
			String choice = scanner.nextLine();
			choice = choice.replaceAll("[\\D*]", "");
			index = Integer.parseInt(choice) - 1;
			if (index >= 0 && index < alternatives.length) {
				validSelection = true;
			} else {
				System.out.println("Goddammit correct number pls!");
			}
		}
		scanner.close();
		// Determine the type of tournament
		HashMap<String, Integer> score;
		Tournament tournament;
		TwoPlayerTournamentScore twoPlayerTournamentScore = new TwoPlayerTournamentScore(players);
		switch (index) {
		case 0:
			tournament = new TwoPlayerComputerViewTournament(players, twoPlayerTournamentAnnouncer,
					twoPlayerTournamentScore);
			break;
		default:
			tournament = new TwoPlayerComputerConsoleTournament(players, twoPlayerTournamentAnnouncer,
					twoPlayerTournamentScore);
		}
		// Start and get score when the game is played
		tournament.start();
		score = tournament.getScore();
		twoPlayerTournamentAnnouncer.printScore(score, players);
	}
}
