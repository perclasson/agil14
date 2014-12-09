package kth.game.othello;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kth.game.othello.factory.OthelloGameFactory;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.movestrategy.CornerStrategy;
import kth.game.othello.player.movestrategy.MaxSwappedStrategy;
import kth.game.othello.player.movestrategy.MinSwappedStrategy;
import kth.game.othello.player.movestrategy.MoveRandomStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.tournament.StrategyTournament;
import kth.game.othello.tournament.StrategyTournamentFactory;

import org.junit.Test;

/**
 * Integration tests.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class OthelloLab3IT extends AbstractTest {

	private List<MoveStrategy> strategies;
	private StrategyTournamentFactory strategyTournamentFactory;

	public OthelloLab3IT() {
		this.strategies = Arrays.asList(new MaxSwappedStrategy(), new MinSwappedStrategy(), new MoveRandomStrategy(
				new Random()), new CornerStrategy());
		this.strategyTournamentFactory = new StrategyTournamentFactory(new OthelloGameFactory(), new PlayerFactory());
	}

	@Test
	public void tournamentUsingViewTest() {
		StrategyTournament strategyTournament = strategyTournamentFactory.createTournament(strategies, true);
		strategyTournament.play();
	}

	@Test
	public void tournamentTest() {
		StrategyTournament strategyTournament = strategyTournamentFactory.createTournament(strategies, false);
		strategyTournament.play();
	}

}
