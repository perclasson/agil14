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
import kth.game.othello.tournament.Result;
import kth.game.othello.tournament.Tournament;
import kth.game.othello.tournament.TournamentFactory;

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
	private TournamentFactory tournamentFactory;

	public OthelloLab3IT() {
		this.strategies = Arrays.asList(new MaxSwappedStrategy(), new MinSwappedStrategy(), new MoveRandomStrategy(
				new Random()), new CornerStrategy());
		this.tournamentFactory = new TournamentFactory(new OthelloGameFactory(), new PlayerFactory());
	}

	@Test
	public void tournamentUsingViewTest() {
		Tournament tournament = tournamentFactory.createComputerTournament(strategies, true);
		Result result = tournament.play();
		System.out.println(result);
	}

	@Test
	public void tournamentTest() {
		Tournament tournament = tournamentFactory.createComputerTournament(strategies, false);
		Result result = tournament.play();
		System.out.println(result.getResult());
	}

}
