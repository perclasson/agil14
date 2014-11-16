package kth.game.othello;

import kth.game.othello.board.Node;
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
public class OthelloLab1IT {

	private Object getNumberOfOccupiedNodes(Othello othello) {
		int occupiedNodesCounter = 0;
		for (Node node : othello.getBoard().getNodes()) {
			if (node.isMarked()) {
				occupiedNodesCounter++;
			}
		}
		return occupiedNodesCounter;
	}

	private OthelloFactory getOthelloFactory() {
		return new DemoOthelloFactory();
	}

	private void makeAHumanMove(Othello othello, Player human) {
		for (Node node : othello.getBoard().getNodes()) {
			if (othello.isMoveValid(human.getId(), node.getId())) {
				othello.move(human.getId(), node.getId());
				return;
			}
		}
		throw new IllegalStateException();
	}

	@Test
	public void someMovesBetweenAComputerAndHumanTest() {
		Othello othello = getOthelloFactory().createHumanVersusComputerGame();
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

	@Test
	public void twoComputerOnAClassicalBoardTest() {
		Othello othello = getOthelloFactory().createComputerGame();
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
