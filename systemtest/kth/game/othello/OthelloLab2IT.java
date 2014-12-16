package kth.game.othello;

import java.util.ArrayList;
import java.util.List;

import kth.game.othello.board.factory.Diamond;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;
import kth.game.othello.player.PlayerImpl;
import kth.game.othello.player.move.strategy.FirstMove;
import kth.game.othello.player.move.strategy.GreedyMove;
import kth.game.othello.player.move.strategy.MoveStrategy;

import org.junit.Assert;
import org.junit.Test;

public class OthelloLab2IT {

	private MoveStrategy getNewMoveStrategy() {
		return new GreedyMove();
	}

	private OthelloFactory getOthelloFactory() {
		return new OthelloFactoryImpl();
	}

	private Player createComputer(String name) {
		return PlayerImpl.getComputerPlayer(name, name, new FirstMove());
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
		String player1Id = othello.getPlayers().get(0).getId();
		String player2Id = othello.getPlayers().get(1).getId();
		othello.start(player2Id);
		othello.move(player2Id, othello.getBoard().getNode(5, 3).getId());
		othello.move(player1Id, othello.getBoard().getNode(5, 2).getId());
		othello.move(player2Id, othello.getBoard().getNode(4, 2).getId());
		othello.move(player1Id, othello.getBoard().getNode(5, 4).getId());
		Assert.assertEquals(5, othello.getScore().getPoints(player1Id));
		Assert.assertEquals(3, othello.getScore().getPoints(player2Id));
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
			System.out.println(othello.getBoard());
		}
		Assert.assertFalse(othello.isActive());
	}

	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = getOthelloFactory().createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());
		while (othello.isActive()) {
			// Make some moves
			makeNumberOfComputerMoves(10, othello);
			// Change one of the computers strategy
			othello.getPlayers().get(1).setMoveStrategy(getNewMoveStrategy());
		}

		Assert.assertFalse(othello.isActive());
	}
}