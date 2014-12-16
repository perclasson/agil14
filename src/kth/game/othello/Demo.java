package kth.game.othello;

import kth.game.othello.factory.OthelloFactory;
import kth.game.othello.factory.OthelloGameFactory;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

public class Demo {
	public static void main(String[] args) {
		OthelloFactory othelloFactory = new OthelloGameFactory();
		Othello othello = othelloFactory.createHumanVersusComputerGame();
		OthelloView othelloView = OthelloViewFactory.create(othello, 100, 1000);
		othelloView.start();
	}
}
