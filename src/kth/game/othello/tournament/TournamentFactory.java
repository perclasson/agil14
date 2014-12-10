package kth.game.othello.tournament;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.Othello;
import kth.game.othello.board.factory.Square;
import kth.game.othello.factory.OthelloGameFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.tournament.gameinstance.ComputerPlayStrategy;
import kth.game.othello.tournament.gameinstance.GameInstance;
import kth.game.othello.tournament.gameinstance.GamePlayStrategy;
import kth.game.othello.tournament.gameinstance.ViewPlayStrategy;

/**
 * This factory creates tournaments with players that battle with different
 * strategies.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class TournamentFactory {

	private static final int CLASSIC_BOARD_ORDER = 8;
	private OthelloGameFactory othelloFactory;
	private PlayerFactory playerFactory;

	public TournamentFactory(OthelloGameFactory othelloFactory, PlayerFactory playerFactory) {
		this.othelloFactory = othelloFactory;
		this.playerFactory = playerFactory;
	}

	public Tournament createComputerTournament(List<MoveStrategy> strategies, boolean othelloView) {
		List<Player> players = createComputerPlayers(strategies);
		List<GameInstance> gameInstances = createComputerGameInstances(players, othelloView);
		return new Tournament(gameInstances, new PlayerResult(players));
	}

	private List<Player> createComputerPlayers(List<MoveStrategy> strategies) {
		List<Player> players = new ArrayList<Player>();

		// Create a player for each move strategy
		for (MoveStrategy moveStrategy : strategies) {
			Player player = playerFactory.createComputerPlayer("Player: " + moveStrategy.getName(), moveStrategy);
			players.add(player);
		}
		return players;
	}

	private List<GameInstance> createComputerGameInstances(List<Player> players, boolean othelloView) {
		List<GameInstance> gameInstances = new ArrayList<GameInstance>();

		// Each move strategy should meet the other strategies twice and
		// starting the game exactly once.
		for (Player playerOne : players) {
			for (Player playerTwo : players) {
				if (playerOne.equals(playerTwo)) {
					continue;
				}
				List<Player> gamePlayers = new ArrayList<Player>();
				gamePlayers.add(playerOne);
				gamePlayers.add(playerTwo);
				Othello othello = othelloFactory.createGame(new Square().getNodes(CLASSIC_BOARD_ORDER, gamePlayers),
						gamePlayers);

				GamePlayStrategy gamePlayStrategy = null;
				if (othelloView) {
					gamePlayStrategy = new ViewPlayStrategy();
				} else {
					gamePlayStrategy = new ComputerPlayStrategy();
				}

				gameInstances.add(new GameInstance(othello, playerOne, gamePlayStrategy));
			}
		}
		return gameInstances;
	}
}
