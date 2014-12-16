package kth.game.othello.tournament;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.OthelloFactoryImpl;
import kth.game.othello.board.factory.Square;
import kth.game.othello.player.Player;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

/**
 * The responsibility of a TwoPlayerComputerView tournament is to run a tournament which displays the progress in a view
 * and the result in a console.
 */
public class TwoPlayerComputerViewTournament implements Tournament {

	List<Player> players;
	TwoPlayerTournamentAnnouncer twoPlayerTournamentAnnouncer;
	TwoPlayerTournamentScore score;

	public TwoPlayerComputerViewTournament(List<Player> players,
			TwoPlayerTournamentAnnouncer twoPlayerTournamentAnnouncer, TwoPlayerTournamentScore score) {
		this.players = players;
		this.twoPlayerTournamentAnnouncer = twoPlayerTournamentAnnouncer;
		this.score = score;
	}

	/**
	 * Start and run view tournament with the players given in initialization
	 */
	@Override
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
	@Override
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
		OthelloView othelloView = OthelloViewFactory.create(game, 10, 100);
		othelloView.start(player1.getId());
		while (game.isActive()) {
		}
		score.updateScoreFromGame(player1, player2, game.getScore(), twoPlayerTournamentAnnouncer);
	}
}
