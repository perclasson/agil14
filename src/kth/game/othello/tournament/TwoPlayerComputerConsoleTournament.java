package kth.game.othello.tournament;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;

/**
 * The responsibility of a TwoPlayerComputerConsoleTournament is tow run a tournament and display the progress and
 * results in the console.
 */
public class TwoPlayerComputerConsoleTournament implements Tournament {
	List<Player> players;
	TwoPlayerTournamentAnnouncer twoPlayerTournamentAnnouncer;
	TwoPlayerTournamentScore score;

	public TwoPlayerComputerConsoleTournament(List<Player> players,
			TwoPlayerTournamentAnnouncer twoPlayerTournamentAnnouncer, TwoPlayerTournamentScore score) {
		this.players = players;
		this.twoPlayerTournamentAnnouncer = twoPlayerTournamentAnnouncer;
		this.score = score;
	}

	/**
	 * Start and run Console tournament with the players given in initialization
	 */
	public void start() {
		score.resetScore(players);
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0; j < players.size(); j++) {
				if (i != j) {
					runGame(players.get(i), players.get(j));
				}
			}
		}
	}

	/**
	 * @return the score of the tournament
	 */
	public HashMap<String, Integer> getScore() {
		return score.getScore();
	}

	/**
	 * Run a game between two players
	 *
	 * @param player1 player 1
	 * @param player2 player 2
	 */
	private void runGame(Player player1, Player player2) {
		List<Player> challengers = new LinkedList<Player>();
		challengers.add(player1);
		challengers.add(player2);
		Othello game = new OthelloFactoryImpl().createGame(new Square().getNodes(8, challengers), challengers);
		game.start(player1.getId());
		while (game.isActive()) {
			game.move();
		}
		score.updateScoreFromGame(player1, player2, game.getScore(), twoPlayerTournamentAnnouncer);
	}
}
