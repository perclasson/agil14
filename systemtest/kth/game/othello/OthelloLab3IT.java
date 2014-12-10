package kth.game.othello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kth.game.othello.board.Node;
import kth.game.othello.board.factory.NodeData;
import kth.game.othello.factory.OthelloGameFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.movestrategy.CornerStrategy;
import kth.game.othello.player.movestrategy.MaxSwappedStrategy;
import kth.game.othello.player.movestrategy.MinSwappedStrategy;
import kth.game.othello.player.movestrategy.MoveRandomStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;
import kth.game.othello.tournament.PlayerResult;
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
	public void computerStrategyTournamentUsingViewTest() {
		Tournament tournament = tournamentFactory.createComputerStrategyTournament(strategies, true);
		PlayerResult result = tournament.play();
		System.out.println(result.getResult());

		int scores = 0;
		for (Player player : result.getPlayers()) {
			scores += result.getPlayerScore(player.getId());
		}
		assertEquals(12, scores);
	}

	@Test
	public void computerStrategyTournamentTest() {
		Tournament tournament = tournamentFactory.createComputerStrategyTournament(strategies, false);
		PlayerResult result = tournament.play();
		System.out.println(result.getResult());

		int scores = 0;
		for (Player player : result.getPlayers()) {
			scores += result.getPlayerScore(player.getId());
		}
		assertEquals(12, scores);
	}

	@Test
	public void undoTest() {
		OthelloGameFactory othelloFactory = new OthelloGameFactory();
		Othello game = othelloFactory.createComputerGame();
		game.start();

		Map<Integer, NodeData> oldNodeData = new HashMap<Integer, NodeData>();
		Player oldPlayerInTurn = game.getPlayerInTurn();

		for (Node node : game.getBoard().getNodes()) {
			NodeData data = new NodeData(node.getXCoordinate(), node.getYCoordinate(), node.getOccupantPlayerId());
			oldNodeData.put(data.hashCode(), data);
		}

		// Let's make 2 moves
		game.move();

		assertTrue(oldPlayerInTurn != game.getPlayerInTurn());

		game.move();

		// Lets make 2 undos to get back to start
		game.undo();

		// We should not have the same board after just one undo
		boolean notAllEquals = false;
		for (Node node : game.getBoard().getNodes()) {
			Integer hashCode = new NodeData(node.getXCoordinate(), node.getYCoordinate(), node.getOccupantPlayerId())
					.hashCode();

			if (oldNodeData.get(hashCode).getOccupantPlayerId() != node.getOccupantPlayerId()) {
				notAllEquals = true;
			}
		}

		assertTrue(notAllEquals);

		// However after 2 undos we should be back at the beginning
		game.undo();

		Player newPlayerInTurn = game.getPlayerInTurn();
		assertEquals(oldPlayerInTurn, newPlayerInTurn);

		boolean allEquals = true;
		for (Node node : game.getBoard().getNodes()) {
			Integer hashCode = new NodeData(node.getXCoordinate(), node.getYCoordinate(), node.getOccupantPlayerId())
					.hashCode();

			if (oldNodeData.get(hashCode).getOccupantPlayerId() != node.getOccupantPlayerId()) {
				allEquals = false;
			}
		}

		assertTrue(allEquals);
	}
}
