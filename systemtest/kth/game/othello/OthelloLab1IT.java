package kth.game.othello;

import kth.game.othello.Othello;
import kth.game.othello.board.Node;
import kth.game.othello.factory.OthelloGameFactory;
import kth.game.othello.player.Player;
import kth.game.othello.player.Player.Type;

import org.junit.Assert;
import org.junit.Test;

/**
 * Integrations tests.
 * 
 * @author Ludvig Axelsson
 * @author Per Classon
 * @author Tommy Roshult
 */
public class OthelloLab1IT extends AbstractTest {

	private OthelloGameFactory othelloFactory;

	public OthelloLab1IT() {
		this.othelloFactory = new OthelloGameFactory();
	}

	/**
	 * DEMO 2 Integration test which show that the model allows for one computer
	 * and one human to play against each other for 10 moves.
	 */
	@Test
	public void someMovesBetweenAComputerAndHumanTest() {
		Othello othello = othelloFactory.createHumanVersusComputerGame();
		Player human = (othello.getPlayers().get(0).getType() == Type.COMPUTER) ? othello.getPlayers().get(1) : othello
				.getPlayers().get(0);
		othello.start(human.getId());

		int totalMoves = 10;
		for (int moves = 0; moves < totalMoves; moves++) {
			makeAHumanMove(othello, human);
			othello.move();
		}

		Assert.assertEquals(totalMoves * 2 + 4, getNumberOfOccupiedNodes(othello));
	}

	/**
	 * DEMO 1 Integration test which show that two computer players can start a
	 * game and and print out who has won.
	 */
	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = othelloFactory.createComputerGame();
		othello.start(othello.getPlayers().get(0).getId());
		while (othello.isActive()) {
			Assert.assertEquals(Type.COMPUTER, othello.getPlayerInTurn().getType());
			othello.move();
		}

		Player first = othello.getPlayers().get(0);
		int firstNodes = 0;

		Player second = othello.getPlayers().get(1);
		int secondNodes = 0;

		for (Node node : othello.getBoard().getNodes()) {
			if (!node.isMarked()) {
				continue;
			}
			if (node.getOccupantPlayerId().equals(first.getId())) {
				firstNodes++;
			}
			if (node.getOccupantPlayerId().equals(second.getId())) {
				secondNodes++;
			}
		}

		if (firstNodes > secondNodes) {
			System.out.println("Player " + first.getName() + " won.");
		} else if (secondNodes > firstNodes) {
			System.out.println("Player " + second.getName() + " won.");
		} else {
			System.out.println("It was a draw.");
		}
	}
}
