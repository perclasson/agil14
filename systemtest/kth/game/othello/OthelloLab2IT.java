package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.board.factory.Diamond;
import kth.game.othello.factory.OthelloGameFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.movestrategy.MaxSwappedStrategy;
import kth.game.othello.player.movestrategy.MoveRandomStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;

import org.junit.Assert;
import org.junit.Test;

/**
 * Integration tests.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 *
 */
public class OthelloLab2IT extends AbstractTest {

	private PlayerFactory playerFactory;
	private Random random;
	private OthelloGameFactory othelloFactory;

	public OthelloLab2IT() {
		this.random = new Random();
		this.playerFactory = new PlayerFactory();
		this.othelloFactory = new OthelloGameFactory();
	}

	private MoveStrategy getNewMoveStrategy() {
		return new MaxSwappedStrategy();
	}

	private void makeNumberOfComputerMoves(int numberOfMoves, Othello othello) {
		for (int i = 0; i < numberOfMoves; i++) {
			if (othello.isActive()) {
				Assert.assertEquals(Type.COMPUTER, othello.getPlayerInTurn().getType());
				othello.move();
			} else {
				Assert.assertEquals(null, othello.getPlayerInTurn());
			}
		}
	}

	@Test
	public void studyTheInitialScoreTest() {
		Othello othello = othelloFactory.createComputerGame();
		String playerId = othello.getPlayers().get(0).getId();
		othello.start();
		Assert.assertEquals(2, othello.getScore().getPoints(playerId));
	}

	@Test
	public void studyTheScoreAfterAMoveTest() {
		Othello othello = othelloFactory.createComputerGame();
		String playerId = othello.getPlayers().get(1).getId();
		othello.start(playerId);
		othello.move(playerId, othello.getBoard().getNode(5, 3).getId());
		Assert.assertEquals(4, othello.getScore().getPoints(playerId));
	}

	/*
	 * Demo 4 Start a computer game with two computers. Make ten moves with each
	 * player. Change the strategy for one of the players. Go to step 2 until
	 * one of the computers won.
	 */
	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = othelloFactory.createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());

		// Make some moves
		makeNumberOfComputerMoves(10, othello);

		// Change one of the computers strategy
		othello.getPlayers().get(0).setMoveStrategy(getNewMoveStrategy());

		// Make some moves
		makeNumberOfComputerMoves(50, othello);

		Assert.assertFalse(othello.isActive());
	}

	/*
	 * Demo 5 Start a human against human game. Make four moves for each player.
	 * Show the score.
	 */
	@Test
	public void humanVersusHumanGame() {
		Othello othello = othelloFactory.createHumanGame();

		Player one = othello.getPlayers().get(0);
		Player two = othello.getPlayers().get(1);

		othello.start(one.getId());

		// Make four moves for each player
		for (int i = 0; i < 4; i++) {
			makeAHumanMove(othello, one);
			makeAHumanMove(othello, two);
		}

		// Show the score.
		Assert.assertTrue(othello.getScore().getPoints(one.getId()) > 0);
		Assert.assertTrue(othello.getScore().getPoints(two.getId()) > 0);
	}

	/*
	 * Demo 6 Create three computers. Use the diamond board from the board
	 * factory. Play the game and show the result.
	 */
	@Test
	public void threeComputersOnADiamondBoardTest() {
		List<Player> players = new ArrayList<Player>();
		players.add(playerFactory.createComputerPlayer("black", new MoveRandomStrategy(random)));
		players.add(playerFactory.createComputerPlayer("white", new MoveRandomStrategy(random)));
		players.add(playerFactory.createComputerPlayer("orange", new MoveRandomStrategy(random)));

		Diamond diamond = new Diamond();
		Othello othello = othelloFactory.createGame(diamond.getNodes(11, players), players);
		othello.start();
		while (othello.isActive()) {
			othello.move();
		}
		Assert.assertFalse(othello.isActive());

		// Show the score.
		Assert.assertTrue(othello.getScore().getPoints(players.get(0).getId()) >= 0);
		Assert.assertTrue(othello.getScore().getPoints(players.get(1).getId()) >= 0);
		Assert.assertTrue(othello.getScore().getPoints(players.get(2).getId()) >= 0);
	}
}
