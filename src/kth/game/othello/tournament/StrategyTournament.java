package kth.game.othello.tournament;

import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.player.Player;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

public class StrategyTournament {
	private static int TIME_BETWEEN_SWAPS = 0;
	private static int TIME_BETWEEN_MOVES = 1000;

	private List<Player> players;
	private List<GameInstance> gameInstances;
	private PlayerResult playerResult;
	private boolean othelloView;

	public StrategyTournament(List<Player> players, List<GameInstance> gameInstances, PlayerResult playerResult) {
		this.players = players;
		this.gameInstances = gameInstances;
		this.playerResult = playerResult;
	}

	public StrategyTournament(List<Player> players, List<GameInstance> gameInstances, PlayerResult playerResult, boolean othelloView) {
		this.players = players;
		this.gameInstances = gameInstances;
		this.othelloView = othelloView;
		this.playerResult = playerResult;
	}

	public void play() {
		for (GameInstance game : gameInstances) {
			Othello othello = game.getOthello();
			othello.start(game.getStartingPlayer().getId());

			if (othelloView) {
				OthelloView view = OthelloViewFactory.create(othello, TIME_BETWEEN_SWAPS, TIME_BETWEEN_MOVES);
				view.start();
			} else {
				while (othello.isActive()) {
					othello.move();
				}
			}

			playerResult.add(othello.getScore());
		}

		// Print the results
		System.out.println(playerResult.getResult(players));
	}
}
