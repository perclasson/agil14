package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.factory.Square;
import kth.game.othello.factory.OthelloGameFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.movestrategy.MoveStrategy;

public class StrategyTournamentFactory {

	private static final int CLASSIC_BOARD_ORDER = 8;
	private OthelloGameFactory othelloFactory;
	private PlayerFactory playerFactory;

	public StrategyTournamentFactory(OthelloGameFactory othelloFactory, PlayerFactory playerFactory) {
		this.othelloFactory = othelloFactory;
		this.playerFactory = playerFactory;
	}

	public StrategyTournament createTournament(List<MoveStrategy> strategies, boolean othelloView) {
		List<Player> players = createPlayers(strategies);
		List<GameInstance> gameInstances = createGameInstances(players);
		return new StrategyTournament(players, gameInstances, new Result(), othelloView);
	}

	private List<Player> createPlayers(List<MoveStrategy> strategies) {
		List<Player> players = new ArrayList<Player>();

		// Create a player for each move strategy
		for (MoveStrategy moveStrategy : strategies) {
			Player player = playerFactory.createComputerPlayer("Player: " + moveStrategy.getName(), moveStrategy);
			players.add(player);
		}
		return players;
	}

	private List<GameInstance> createGameInstances(List<Player> players) {
		List<GameInstance> gameInstances = new ArrayList<GameInstance>();

		// Each move strategy should meet the other strategies twice and
		// starting the game exactly once.
		for (Player playerOne : players) {
			for (Player playerTwo : players) {
				if (playerOne.equals(playerTwo)) {
					continue;
				}
				List<Player> gamePlayers = Arrays.asList(playerOne, playerTwo);
				Othello othello = othelloFactory.createGame(new Square().getNodes(CLASSIC_BOARD_ORDER, gamePlayers),
						gamePlayers);
				gameInstances.add(new GameInstance(othello, playerOne));
			}
		}
		return gameInstances;
	}
}
