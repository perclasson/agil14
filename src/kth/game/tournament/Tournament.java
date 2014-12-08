package kth.game.tournament;

import kth.game.othello.Othello;
import kth.game.othello.factory.OthelloGameFactor;
import kth.game.othello.view.swing.OthelloView;
import kth.game.othello.view.swing.OthelloViewFactory;

public class Tournament {

	public static void Main(String[] args) {
		Othello othelloGame = new OthelloGameFactor().createComputerGame();
		OthelloView othelloView = OthelloViewFactory.create(othelloGame, 100, 100);
		othelloView.start();
	}
}
