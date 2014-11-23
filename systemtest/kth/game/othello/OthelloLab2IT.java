package kth.game.othello;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kth.game.othello.Othello;
import kth.game.othello.board.factory.Diamond;
import kth.game.othello.factory.OthelloFactory;
import kth.game.othello.factory.OthelloFactoryImpl;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;
import kth.game.othello.player.PlayerFactory;
import kth.game.othello.player.movestrategy.MoveMaxSwappedStrategy;
import kth.game.othello.player.movestrategy.MoveRandomStrategy;
import kth.game.othello.player.movestrategy.MoveStrategy;

import org.junit.Assert;
import org.junit.Test;

public class OthelloLab2IT {

	private PlayerFactory playerFactory;
	private Random random;

	public OthelloLab2IT() {
		this.random = new Random();
		this.playerFactory = new PlayerFactory();
	}

	private MoveStrategy getNewMoveStrategy() {
		return new MoveMaxSwappedStrategy();
	}

	private OthelloFactory getOthelloFactory() {
		return new OthelloFactoryImpl();
	}

	private Player createComputer(String name) {
		return playerFactory.createComputerPlayer(name, new MoveRandomStrategy(random));
	}

	private Player createHuman(String name) {
		return playerFactory.createHumanPlayer(name);
	}

	private void makeNumberOfComputerMoves(int numberOfMoves, Othello othello) {
		for (int i = 0; i < numberOfMoves; i++) {
			Assert.assertEquals(Type.COMPUTER, othello.getPlayerInTurn().getType());
			othello.move();
		}
	}

	@Test
	public void studyTheInitialScoreTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(0).getId();
		othello.start();
		Assert.assertEquals(2, othello.getScore().getPoints(playerId));
	}

	@Test
	public void studyTheScoreAfterAMoveTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		String playerId = othello.getPlayers().get(1).getId();
		othello.start(playerId);
		othello.move(playerId, othello.getBoard().getNode(5, 3).getId());
		Assert.assertEquals(4, othello.getScore().getPoints(playerId));
	}

	@Test
	public void threeComputersOnADiamondBoardTest() {
		List<Player> players = new ArrayList<Player>();
		players.add(createComputer("black"));
		players.add(createComputer("white"));
		players.add(createComputer("orange"));
		Diamond diamond = new Diamond();
		Othello othello = getOthelloFactory().createGame(diamond.getNodes(11, players), players);
		othello.start();
		while (othello.isActive()) {
			othello.move();
		}
		Assert.assertFalse(othello.isActive());
	}

	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());

		// Make some moves
		makeNumberOfComputerMoves(10, othello);

		// Change one of the computers strategy
		othello.getPlayers().get(0).setMoveStrategy(getNewMoveStrategy());

		// Make some moves
		makeNumberOfComputerMoves(50, othello);

		Assert.assertFalse(othello.isActive());
	}
}
