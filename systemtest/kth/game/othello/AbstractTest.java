package kth.game.othello;

import kth.game.othello.board.Node;
import kth.game.othello.factory.OthelloFactory;
import kth.game.othello.factory.OthelloFactoryImpl;
import kth.game.othello.player.Player;

public abstract class AbstractTest {

	protected Object getNumberOfOccupiedNodes(Othello othello) {
		int occupiedNodesCounter = 0;
		for (Node node : othello.getBoard().getNodes()) {
			if (node.isMarked()) {
				occupiedNodesCounter++;
			}
		}
		return occupiedNodesCounter;
	}

	protected OthelloFactory getOthelloFactory() {
		return new OthelloFactoryImpl();
	}

	protected void makeAHumanMove(Othello othello, Player human) {
		for (Node node : othello.getBoard().getNodes()) {
			if (othello.isMoveValid(human.getId(), node.getId())) {
				othello.move(human.getId(), node.getId());
				return;
			}
		}
		throw new IllegalStateException();
	}
}
